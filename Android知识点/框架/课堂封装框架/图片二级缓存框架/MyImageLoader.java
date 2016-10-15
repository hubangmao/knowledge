package cn.ucai.a4imageloader.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by hbm on 2016/8/17.
 * 异步加载图片基于O,kHttp
 */
public class MyImageLoader {
    private final static String UTF_8 = "utf-8";
    private final static int RESULT_SUCCESS = 0;
    private final static int RESULT_ERROR = 1;

    private static OkHttpClient mClient;
    private Handler mHandler;

    private ImageBean mBean;

    //一级缓存储存图片集合
    private static LruCache<String, Bitmap> mCaches;

    //处理图片加载完成接口
    public interface OnImageLoadOkListener {
        void onSuccess(String url, Bitmap bitmap);

        void onError(String error);

    }

    public static MyImageLoader build() {
        return new MyImageLoader();
    }

    //存放图片相关属性
    private class ImageBean {
        StringBuilder url;
        int width;
        int height;
        String saveFileName;

        Bitmap bitmap;
        OnImageLoadOkListener listener;
        String error;
    }

    public MyImageLoader() {
        mBean = new ImageBean();
        if (mClient == null) {
            mClient = new OkHttpClient();
        }
        if (mCaches == null) {
            long maxRAM = Runtime.getRuntime().maxMemory();
            Log.i("main", "分配最大内存空间=" + (maxRAM / 1024 / 1024) + "M");
            mCaches = new LruCache<String, Bitmap>((int) (maxRAM / 4)) {
                //计算每个图片的大小，单位：字节
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    //计算当前bitmap的字节数
                    return bitmap.getHeight() * bitmap.getRowBytes();
                }

            };
        }
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ImageBean bean = (ImageBean) msg.obj;
                switch (msg.what) {
                    case RESULT_ERROR:
                        bean.listener.onError(bean.error);
                        break;
                    case RESULT_SUCCESS:
                        bean.listener.onSuccess(bean.url.toString(), bean.bitmap);
                        break;
                }
            }
        };
    }

    public MyImageLoader url(String url) {
        mBean.url = new StringBuilder(url);
        return this;
    }

    public MyImageLoader addParam(String key, String value) {
        if (mBean.url == null) {
            return this;
        }
        if (mBean.url.indexOf("?") == -1) {
            mBean.url.append("?");
        } else {
            mBean.url.append("&");
        }
        try {
            mBean.url.append(key).append("=").append(URLEncoder.encode(value, UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public MyImageLoader width(int width) {
        mBean.width = width;
        return this;
    }

    public MyImageLoader height(int height) {
        mBean.height = height;
        return this;
    }

    public MyImageLoader setFileName(String fileName) {
        mBean.saveFileName = fileName;
        return this;
    }

    //预存代码
    public MyImageLoader listener(OnImageLoadOkListener listener) {
        mBean.listener = listener;
        return this;
    }

    public Bitmap loadImage(final Context context) {
        final Message msg = Message.obtain();
        if (mBean.url == null) {
            msg.what = RESULT_ERROR;
            msg.obj = "url设置了吗";
            mHandler.sendMessage(msg);
        }
        //从内存中读取图片
        if (mCaches.get(mBean.url.toString()) != null) {
            return mCaches.get(mBean.url.toString());
        }
        //从SD卡读取
        Bitmap bitmap = BitmapUtils.getBitmap(FileUtils.getDir(context, mBean.saveFileName));
        if (bitmap != null) {
            return bitmap;
        }

        final Request request = new Request.Builder().url(mBean.url.toString()).build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            //请求失败
            public void onFailure(Request request, IOException e) {
                msg.what = RESULT_ERROR;
                msg.obj = e.getMessage();
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                byte[] data = response.body().bytes();
                if (data == null) {
                    msg.what = RESULT_ERROR;
                    msg.obj = "图片加载失败";
                    mHandler.sendMessage(msg);
                    return;
                }
                //将图片字节数组转换为Bitmap格式
                Bitmap bitmap = BitmapUtils.getBitmap(data, mBean.width, mBean.height);
                mBean.bitmap = bitmap;

                Message msg = Message.obtain();
                msg.what = RESULT_SUCCESS;
                msg.obj = mBean;
                mHandler.sendMessage(msg);

                //一级缓存将下载的图片保存在内存(mCaches)中
                mCaches.put(mBean.url.toString(), bitmap);
                //二级缓存将图片库存在sd卡
                BitmapUtils.saveBitmap(bitmap, FileUtils.getDir(context, mBean.saveFileName));
            }
        });
        return null;
    }
}
