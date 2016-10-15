package cn.ucai.day07_05muis;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<AudioBean> mAudioBean;
    ArrayList<VideoBean> mVideoView;
    ArrayList<ImageBean> mImageBeanList;
    VideoView mvv;
    EditText mXinXi;
    EditText mv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
    }

    private void setView() {
        mXinXi = (EditText) findViewById(R.id.xinXi);
        mv = (EditText) findViewById(R.id.V);

    }

    //读取音频播放音频
    public void onclick(View v) {

        mAudioBean = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        //音频用Audio
        Cursor c = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(MediaStore.Audio.Media._ID));
            String path = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));
            String displayName = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            AudioBean ab = new AudioBean(displayName, path, id);
            mAudioBean.add(ab);
        }
        String[] items = new String[mAudioBean.size()];
        for (int i = 0; i < mAudioBean.size(); i++) {
            items[i] = mAudioBean.get(i).getName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("音乐列表").setItems
                (items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String path = mAudioBean.get(which).getPath();
                        mXinXi.setText("邦哥提供:\n" + mAudioBean.get(which).getName());
                        MediaPlayer player = new MediaPlayer();
                        try {
                            //设置播放路径
                            player.setDataSource(path);
                            player.prepare();//z进入准备状态
                            player.start();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).create().show();

    }

    //读取播放视频用
    public void onclick1(View view) {
        mvv = (VideoView) findViewById(R.id.vv);
        mVideoView = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        //视频用Video
        Cursor c = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        while (c.moveToNext()) {
            int vid = c.getInt(c.getColumnIndex(MediaStore.Video.Media._ID));
            String vPath = c.getString(c.getColumnIndex(MediaStore.Video.Media.DATA));
            String vDisplayName = c.getString(c.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
            VideoBean vAbv = new VideoBean(vDisplayName, vPath, vid);
            mVideoView.add(vAbv);
        }
        final String[] vItems = new String[mVideoView.size()];
        for (int i = 0; i < mVideoView.size(); i++) {
            vItems[i] = mVideoView.get(i).getPathv();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("视频列表")
                .setItems
                        (vItems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mvv.setVideoPath(mVideoView.get(which).getPathv());
                                mv.setText("邦哥提供:\n" + mVideoView.get(which).getNamev());
                                mvv.start();
                                findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mvv.stopPlayback();
                                    }
                                });
                            }
                        }).create().show();


    }

    //读取本地图片
    public void onclick2(View view) {
        ContentResolver resolver = getContentResolver();
        Cursor c = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (c.moveToNext()) {
            int iid = c.getInt(c.getColumnIndex(MediaStore.Images.Media._ID));
            String iPath = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
            String iDisplayName = c.getString(c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            ImageBean ib = new ImageBean(iDisplayName,iPath,iid);
            mImageBeanList = new ArrayList<>();
            mImageBeanList.add(ib);
            String[] ImArray = new String[mImageBeanList.size()];
            for (int i=0;i<mImageBeanList.size();i++) {
                ImArray[i]=mImageBeanList.get(i).getIname();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("图片列表").setItems(ImArray,
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String path = mImageBeanList.get(which).getIpath();
                    ImageView miv;
                    miv = (ImageView) findViewById(R.id.iv);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    miv.setImageBitmap(bitmap);

                }
            }).create().show();
        }
    }

}
