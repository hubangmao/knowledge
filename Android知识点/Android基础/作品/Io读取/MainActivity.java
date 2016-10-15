package cn.ucai.day06_03sd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {
    ImageView mim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mim = (ImageView) findViewById(R.id.Im);
    }

    public void imgSd(View view) {
        final InputStream in = getResources().openRawResource(R.raw.layout8);
        //每次读取数据的数组
        byte[] buffer = new byte[1024 * 5];
        int len;//保存每次读取数据的实际字节数
        //创建输出流,指向SD卡的Pictures文件夹
        final File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);//PICTURES文件夹
        //创建保存的文件
        File file = new File(directory, "layout8.jpg");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            Toast.makeText(MainActivity.this, "图片保存成功", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void imgAn(View view) {
        final InputStream in = getResources().openRawResource(R.raw.layout8);
        final File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        FileOutputStream out = null;
        File file = new File(dir, "layout.jpg");
        byte[] buffer = new byte[1024 * 5];
        int len;
        try {
            out = new FileOutputStream(file);
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            Toast.makeText(MainActivity.this, "图片保存至SD卡的Android文件夹", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void TextSd(View view) {
        final File dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        FileOutputStream out = null;
        File file = new File(dir, "hello.dat");
        try {
            out = new FileOutputStream(file);
            out.write("hello adnroid".getBytes());
            Toast.makeText(MainActivity.this, "文本保存至sd卡的Android文件夹", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void outSd(View view) {
        final File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(dir, "layout8.jpg");
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            final Bitmap bitmap = BitmapFactory.decodeStream(in);
            mim.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

