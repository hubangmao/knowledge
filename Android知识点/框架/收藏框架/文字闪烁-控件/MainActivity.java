package cn.ucai.test4;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShimmerFrameLayout shimmer = (ShimmerFrameLayout) findViewById(R.id.shimmer);
        //闪光从左到右
        shimmer.setRepeatMode(ObjectAnimator.RESTART);
        //从左到右在从右到左
        //shimmer.setRepeatMode(ObjectAnimator.REVERSE);
        //间隔时间
        shimmer.setDuration(1000);
        shimmer.startShimmerAnimation();
    }
}
