package cn.ucai.test6;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.ucai.test6.shimmer.ShimmerFrameLayout;
import cn.ucai.test6.togglebutton.ToggleButton;

public class MainActivity extends AppCompatActivity {
    ToggleButton tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToggle();
    }


    private void initToggle() {
        tb = (ToggleButton) findViewById(R.id.togleButton);
        tb.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    toast(MainActivity.this, "开");
                } else {
                    toast(MainActivity.this, "关");
                }
            }
        });
    }

    private static Toast toast;

    public static void toast(final Context context, String string) {
        if (toast == null) {
            toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(string);
        }
        toast.show();
    }
}
