package cn.ucai.test5;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private BounceLoadingView mLoadingView1;
    private BounceLoadingView mLoadingView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        mLoadingView1 = (BounceLoadingView) findViewById(R.id.loadingView1);
        mLoadingView2 = (BounceLoadingView) findViewById(R.id.loadingView2);

        mLoadingView1.addBitmap(R.drawable.image1);
        mLoadingView1.addBitmap(R.drawable.image2);
        mLoadingView1.addBitmap(R.drawable.image4);

        mLoadingView2.addBitmap(R.drawable.v4);
        mLoadingView2.addBitmap(R.drawable.v5);
        mLoadingView2.addBitmap(R.drawable.v6);
        mLoadingView2.addBitmap(R.drawable.v7);
        mLoadingView2.addBitmap(R.drawable.v8);
        mLoadingView2.addBitmap(R.drawable.v9);


    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but1:
                mLoadingView1.setVisibility(View.VISIBLE);

                mLoadingView2.stop();
                mLoadingView2.setVisibility(View.GONE);

                mLoadingView1.setShadowColor(Color.LTGRAY);
                mLoadingView1.setDuration(700);
                mLoadingView1.start();


                break;
           case R.id.but2:
                mLoadingView2.setVisibility(View.VISIBLE);

                mLoadingView1.stop();
                mLoadingView1.setVisibility(View.GONE);

                mLoadingView2.setShadowColor(Color.LTGRAY);
                mLoadingView2.setDuration(700);
                mLoadingView2.start();
                break;

        }


    }
}
