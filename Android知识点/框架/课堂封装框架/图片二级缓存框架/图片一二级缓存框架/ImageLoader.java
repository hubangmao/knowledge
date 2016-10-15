package cn.ucai.test_f5.utlis;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.squareup.okhttp.Callback;

import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by yao on 2016/5/18.
 */
public class ImageLoader {
    private static final String UTF_8 = "utf-8";
    private static final int DOWNLOAD_SUCCESS = 0;
    private static final int DOWNLOAD_ERROR = 1;

    private static OkHttpClient mOkHttpClient;
    /**
     * mHandler不能单例，否则一个mHandler不能准确地处理多个mBean
     */
    private Handler mHandler;
    private static LruCache<String, Bitmap> mCaches;
    ImageBean mBean;

    /**
     * RecyclerView、listView、GridView等容器
     */
    ViewGroup mParentLayout;
    private static String mTag;
    /**
     * 缺省图片
     */
    private int mDefaultPicId;

    public interface OnImageLoadListener {
        void onSuccess(String url, Bitmap bitmap);

        void onError(String error);
    }

    private class ImageBean {
        String url;
        int width;
        int height;
        Bitmap bitmap;
        OnImageLoadListener listener;
        String saveFileName;
        String error;
        ImageView imageView;
    }

    public static ImageLoader build() {
        return new ImageLoader();
    }

    private ImageLoader() {
        mBean = new ImageBean();
        initHandler();
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        }
        if (mCaches == null) {
            initCaches();
        }
    }

    private void initCaches() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        mCaches = new LruCache<String, Bitmap>((int) maxMemory / 4) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ImageBean bean = (ImageBean) msg.obj;
                switch (msg.what) {
                    case DOWNLOAD_ERROR:
                        bean.listener.onError(bean.error);
                        break;
                    case DOWNLOAD_SUCCESS:
                        bean.listener.onSuccess(mBean.url, mBean.bitmap);
                        break;
                }
            }
        };

    }

    public ImageLoader url(String url) {
        mBean.url = url;
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

    public ImageLoader saveFileName(String saveFileName) {
        mBean.saveFileName = saveFileName;
        return this;
    }

    public ImageLoader listener(OnImageLoadListener listener) {
        mBean.listener = listener;
        return this;
    }

    public ImageLoader addParam(String key, String value) {
        try {
            if (mBean.url.indexOf("?") == -1) {
                mBean.url += "?" + key + "=" + URLEncoder.encode(value, UTF_8);
            } else {
                mBean.url += "&" + key + "=" + URLEncoder.encode(value, UTF_8);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Bitmap loadImage(final Context context) {
        if (mBean.url == null) {
            Message msg = Message.obtain();
            msg.what = DOWNLOAD_ERROR;
            mBean.error = "url没有设置";
            msg.obj = mBean;
            mHandler.sendMessage(msg);
            return null;
        }
        if (mCaches.get(mBean.url) != null) {
            return mCaches.get(mBean.url);
        }
        String dir = FileUtils.getDir(context, mBean.saveFileName);
        final Bitmap bitmap = BitmapUtils.getBitmap(dir);
        if (bitmap != null) {
            return bitmap;
        }
        //用图片的下载地址（不包含每个图片的文件名)设置用于取消请求的tag
        int i = mBean.url.lastIndexOf('/');
        mTag = mBean.url;
        Request request = new Request.Builder().url(mBean.url).tag(mTag).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Message msg = Message.obtain();
                msg.what = DOWNLOAD_ERROR;
                mBean.error = e.getMessage();
                msg.obj = mBean;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Bitmap bitmap = BitmapUtils.getBitmap(response.body().bytes(), mBean.width, mBean.height);
                if (bitmap != null) {
                    mBean.bitmap = bitmap;
                    mCaches.put(mBean.url, mBean.bitmap);
                    if (mBean.saveFileName != null) {
                        BitmapUtils.saveBitmap(mBean.bitmap, FileUtils.getDir(context, mBean.saveFileName));
                    }

                    Message msg = Message.obtain();
                    msg.what = DOWNLOAD_SUCCESS;
                    msg.obj = mBean;
                    mHandler.sendMessage(msg);
                } else {// 发送下载失败的消息
                    Message message = Message.obtain();
                    message.what = DOWNLOAD_ERROR;
                    mBean.error = "图片下载失败";
                    message.obj = mBean;
                    mHandler.sendMessage(message);
                }
            }
        });
        return null;
    }

    /**
     * 则设置图片下载后主线程默认的图片显示代码->mOnImageLoadListener
     *
     * @param parent:View，例如:RecyclerView、ListView等
     * @return
     */
    public ImageLoader listener(final ViewGroup parent) {
        if (parent != null) {
            mBean.listener = new OnImageLoadListener() {//设置下载完成后处理的代码
                @Override
                public void onSuccess(String url, Bitmap bitmap) {
                    //从RecyclerView中搜索tag值是url的ImageView
                    ImageView iv = (ImageView) parent.findViewWithTag(url);
                    if (iv != null) {
                        iv.setImageBitmap(bitmap);
                    }
                }

                @Override
                public void onError(String error) {
                    Log.i("main", error);
                }

            };
            mParentLayout = parent;
        }
        return this;
    }

    /**
     * 设置显示图片的ImageView
     *
     * @param imageView
     * @return
     */
    public ImageLoader imageView(ImageView imageView) {
        imageView.setTag(mBean.url);
        mBean.imageView = imageView;
        return this;
    }

    /**
     * 设置缺省显示的图片
     *
     * @param defaultPicId
     * @return
     */
    public ImageLoader defaultPicture(int defaultPicId) {
        mDefaultPicId = defaultPicId;
        return this;
    }

    /**
     * 封装了图片下载和显示的缺省代码。
     * 若要编写更为灵活的显示图片的代码，可调用loadImage方法
     *
     * @param context
     */
    public void showImage(Context context) {
        Bitmap bitmap = loadImage(context);//从内存或sd卡加载图片
        if (bitmap == null) {
            Log.i("main", "null");
            mBean.imageView.setImageResource(mDefaultPicId);
        } else {
            mBean.imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * 释放ImageLoader类的静态对象
     */
    public static void release() {
        if (mOkHttpClient != null) {
            mOkHttpClient.cancel(mTag);
            mOkHttpClient = null;
            mCaches = null;
        }
    }
}
