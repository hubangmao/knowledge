package cn.ucai.day05_02.views;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import cn.ucai.day05_02.R;
import cn.ucai.day05_02.utils.ImageLoader;

/**
 * Created by yao on 2016/8/17.
 */
public class AutoPlayViewPager extends ViewPager {
    String[] mGoodsUrl = {"goods01.png","goods02.png","goods03.png",
            "goods04.png","goods05.png","goods06.png",};
    GoodsAdapter mAdapter;

    FlowIndicator mFlowIndicator;

    boolean misAutoPlay;

    Context context;


    Handler mHandler;

    public AutoPlayViewPager(Context context) {
        super(context);
    }

    public AutoPlayViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initHandler();
        setListener();
    }

    private void initHandler() {
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (!misAutoPlay) {
                    misAutoPlay = true;
                } else {
                    setCurrentItem(getCurrentItem()+1);
                }
            }
        };
    }

    private void setListener() {
        setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mFlowIndicator.setFocus(position%mGoodsUrl.length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class GoodsAdapter extends PagerAdapter {
        Context context;
        String[] goodsUrls;

        public GoodsAdapter(Context context, String[] goodsUrls) {
            this.context = context;
            this.goodsUrls = goodsUrls;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(context);
            ImageLoader.build().url("http://10.0.2.2/" + goodsUrls[position%goodsUrls.length])
                    .width(160)
                    .height(400)
                    .imageView(iv)
                    .listener(container)
                    .showImage(context, R.drawable.nopic);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(((ImageView) object));
        }
    }

    Timer mTimer;
    public void startPlay(FlowIndicator flowIndicator) {
        mAdapter = new GoodsAdapter(context, mGoodsUrl);
        setAdapter(mAdapter);

        mFlowIndicator=flowIndicator;
        misAutoPlay=true;
        MyScroller scroller = new MyScroller(context, new LinearInterpolator());
        scroller.setDuration(2000);
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(this,scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        }, 0, 2000);
    }

    class MyScroller extends Scroller {
        int duration;

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public MyScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, this.duration);
        }
    }

    public void stopPlay() {
        mTimer.cancel();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                misAutoPlay=false;
                break;

        }
        return false;
    }
}
