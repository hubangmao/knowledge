package cn.ucai.day05_04.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by yao on 2016/8/17.
 * 异步加载图片的框架,基于OkHttp
 */
public class ImageLoader {
    private static String UTF_8 = "utf-8";
    private static final int RESULT_SUCCESS = 0;
    private static final int RESULT_ERROR = 1;

    private static OkHttpClient mOkHttpClient;

    //保存下载图片的集合
    private static LruCache<String,Bitmap> mCaches;
    private Handler mHandler;

    /**
     * 处理图片加载完成的事件
     */
    public interface OnImageLoadListener {
        void onSuccess(String url, Bitmap bitmap);

        void onError(String error);
    }

    ImageBean mBean;

    /**
     * 封装了一个图片的相关信息的实体类
     */
    private class ImageBean {
        //图片下载的地址
        StringBuilder url;
        //图片预期的宽度
        int width;
        //图片的预期高度
        int height;
        //下载的图片
        Bitmap bitmap;
        //存放图片下载完成后，程序员写的代码
        OnImageLoadListener listener;
        //客户端向服务端发送请求失败的信息
        String error;
        //图片的文件名
        String saveFileName;
        ImageView imageView;
    }

    public ImageLoader() {
        mBean = new ImageBean();
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        }

        if (mCaches == null) {
            initCaches();
        }
        initHandler();

    }

    private void initCaches() {
        //获取app最大的容量，单位：byte
        long maxMemory = Runtime.getRuntime().maxMemory();
        mCaches = new LruCache<String, Bitmap>((int) (maxMemory / 4)){
            //计算每个图片的大小，单位：字节
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //计算当前bitmap的字节数
                return bitmap.getHeight()*bitmap.getRowBytes();
            }
        };
    }

    public static ImageLoader build() {
        return new ImageLoader();
    }

    private void initHandler() {
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

    public ImageLoader url(String url) {
        mBean.url = new StringBuilder(url);
        return this;
    }

    public ImageLoader addParam(String key, String value) {
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

    public ImageLoader saveFileName(String fileName) {
        mBean.saveFileName=fileName;
        return this;
    }
    public ImageLoader width(int width) {
        mBean.width = width;
        return this;
    }

    public ImageLoader height(int height) {
        mBean.height = height;
        return this;
    }

    /**
     * 预存程序员写的图片加载完成的事件处理的代码
     *
     * @param listener
     * @return
     */
    public ImageLoader listener(OnImageLoadListener listener) {
        mBean.listener = listener;
        return this;
    }

    public Bitmap loadImage(final Context context) {
        if (mBean.url == null) {
            Message msg = Message.obtain();
            msg.what = RESULT_ERROR;
            mBean.error="没有设置url";
            msg.obj = mBean;
            mHandler.sendMessage(msg);
        }
        //从内存中读取图片，若存在则直接返回Bitmap对象
        if (mCaches.get(mBean.url.toString()) != null) {
            return mCaches.get(mBean.url.toString());
        }
        //从sd卡读取图片，若存在则从sd卡读取图片并返回Bitmap对象
        Bitmap bitmap = BitmapUtils.getBitmap(FileUtils.getDir(context, mBean.saveFileName));
        if (bitmap != null) {
            return bitmap;
        }
        mTag = mBean.url.substring(0,mBean.url.lastIndexOf("/"));

        Request request = new Request.Builder().url(mBean.url.toString()).tag(mTag).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Message msg = Message.obtain();
                msg.what = RESULT_ERROR;
                mBean.error=e.getMessage();
                msg.obj = mBean;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                //解析图片->mBean.bitmap
                byte[] data = response.body().bytes();
                if (data == null) {
                    Message msg = Message.obtain();
                    msg.what = RESULT_ERROR;
                    mBean.error="图片加载失败";
                    msg.obj = mBean;
                    mHandler.sendMessage(msg);
                    return;
                }
                //将代表图片的字节数组转换为Bitmap格式
                Bitmap bitmap = BitmapUtils.getBitmap(data, mBean.width, mBean.height);
                //将下载的图片保存在内存(mCaches)中
                mCaches.put(mBean.url.toString(), bitmap);
                if (mBean.saveFileName != null) {
                    //将图片库存在sd卡
                    BitmapUtils.saveBitmap(bitmap,FileUtils.getDir(context,mBean.saveFileName));
                }
                mBean.bitmap = bitmap;
                Message msg = Message.obtain();
                msg.what = RESULT_SUCCESS;
                msg.obj = mBean;
                mHandler.sendMessage(msg);
            }
        });
        return null;
    }

    public ImageLoader listener(final ViewGroup parent) {
        mBean.listener=new ImageLoader.OnImageLoadListener() {
            @Override
            public void onSuccess(String url, Bitmap bitmap) {
                ImageView iv = (ImageView) parent.findViewWithTag(url);
                if (iv != null) {
                    iv.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onError(String error) {

            }
        };
        return  this;
    }

    public void showImage(Context context,int defaultPicId) {
        Bitmap bitmap = loadImage(context);
        if (bitmap == null) {
            mBean.imageView.setImageResource(defaultPicId);
        } else {
            mBean.imageView.setImageBitmap(bitmap);
        }
    }

    public ImageLoader imageView(ImageView imageView) {
        mBean.imageView=imageView;
        return this;
    }

    private static String mTag;
    public static void realease() {
        mCaches=null;
        if (mOkHttpClient != null) {
            mOkHttpClient.cancel(mTag);
            mOkHttpClient=null;
        }
    }
}
