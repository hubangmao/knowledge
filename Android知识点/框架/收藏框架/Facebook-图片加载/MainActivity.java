package cn.ucai.test5;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

public class MainActivity extends AppCompatActivity {

    SimpleDraweeView simple1;
    SimpleDraweeView simple2;
    SimpleDraweeView simple3;
    SimpleDraweeView simple4;

    String url = "http://img.zngirls.com/gallery/20440/17845/001.jpg";

    String url1 = "http://10.0.2.2/testgif.gif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        initSimple();

    }

    private void initSimple() {
        simple1 = (SimpleDraweeView) findViewById(R.id.faceBock_ImageLoad1);
        simple1.setImageURI(Uri.parse(url));

        simple2 = (SimpleDraweeView) findViewById(R.id.faceBock_ImageLoad2);
        simple2.setImageURI(Uri.parse(url));

        //显示gif
        simple3 = (SimpleDraweeView) findViewById(R.id.faceBock_ImageLoad3);
        simple3 = (SimpleDraweeView) findViewById(R.id.faceBock_ImageLoad3);
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(url1)
                .build();
        simple3.setController(draweeController);
    }


}
