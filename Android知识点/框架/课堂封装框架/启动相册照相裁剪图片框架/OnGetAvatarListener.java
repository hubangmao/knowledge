package cn.ucai.day05_05.listener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.io.File;

import cn.ucai.day05_05.R;

/**
 * Created by yao on 2016/8/18.
 */
public class OnGetAvatarListener {
    static final int ACTION_CAPTURE=0;
    static final int ACTION_CHOOSE =1;
    static final int ACTION_CROP=2;
    PopupWindow mPopupWindow;
    File mFile;//存放拍照的照片的文件对象
    Activity mContext;

    public OnGetAvatarListener(Activity context,View parent) {
        mContext=context;
        showPopupWindow(parent);
    }

    /**
     *  从底部显示一个悬浮窗口，包含拍照和相册选取两个TextView
     */
    private void showPopupWindow(View parent ) {
        View layout = View.inflate(mContext, R.layout.popu_window, null);
        mPopupWindow=new PopupWindow(layout, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.showAtLocation(parent, Gravity.BOTTOM,0,0);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        setChooseOnClickListener(layout);
        setCaptureOnClickListener(layout);
    }

    private void setCaptureOnClickListener(View layout) {
        layout.findViewById(R.id.tvCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                mFile = new File(dir, System.currentTimeMillis() + "");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
                mContext.startActivityForResult(intent,ACTION_CAPTURE);
            }
        });
    }

    /**
     * 设置PopupWindow中的相册选取View的单击事件监听
     * @param layout
     */
    private void setChooseOnClickListener(View layout) {
        mPopupWindow.setAnimationStyle(R.style.popup_window);

        layout.findViewById(R.id.tvChoose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                mContext.startActivityForResult(intent, ACTION_CHOOSE);
            }
        });
    }

    public void setAvatar(int requestCode, Intent data, ImageView ivAvatar) {
        switch (requestCode) {
            case ACTION_CAPTURE:
                startCropActivity(Uri.fromFile(mFile), 200, 200);
                break;
            case ACTION_CHOOSE:
                startCropActivity(data.getData(),200,200);
                break;
            case ACTION_CROP:
                showImage(data,ivAvatar);
                break;
        }
    }

    private void showImage(Intent data,ImageView ivAvatar) {
        Bitmap bitmap = data.getParcelableExtra("data");
        if (bitmap == null) {
            return;
        }
        ivAvatar.setImageBitmap(bitmap);
    }

    /**
     * 启动裁剪的Activity并要求该Activity返回裁剪的结果
     * @param data：包含照片的Uri对象
     * @param outputX：裁剪的宽度
     * @param outputY：裁剪的高度
     */
    private void startCropActivity(Uri data, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, data);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG);
        mContext.startActivityForResult(intent, ACTION_CROP);
    }

}
