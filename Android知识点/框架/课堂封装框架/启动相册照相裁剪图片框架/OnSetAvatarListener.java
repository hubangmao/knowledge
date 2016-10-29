package cn.ucai.test2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 获取头像工具类
 * 1、通过拍照获取头像
 * 2、从相册获取头像
 * 3、对头像进行裁剪，拍照的头像保存至sd卡的当前项目文件夹下
 */
public class OnSetAvatarListener implements View.OnClickListener {
    //拍照
    private static final int REQUEST_TAKE_PICTURE = 1;
    //图库
    private static final int REQUEST_CHOOSE_PHOTO = 2;
    //裁剪
    public static final int REQUEST_CROP_PHOTO = 3;
    private Activity mActivity;
    private View mPopupLayout;
    private PopupWindow mPopupWindow;
    private String mFileName;


    /**
     * @param mActivity：PopupWindow宿主Activity
     * @param parentId：PopupWindows需要基于一个容器需要容器id
     * @param fileName：个人账号和群号作为图片文件名称
     */
    public OnSetAvatarListener(Activity mActivity, int parentId, String fileName) {
        this.mActivity = mActivity;
        mFileName = fileName;
        View parentLayout = mActivity.findViewById(parentId);
        mPopupLayout = View.inflate(mActivity, R.layout.popu_window, null);
        mPopupLayout.findViewById(R.id.btn_take_picture).setOnClickListener(this);
        mPopupLayout.findViewById(R.id.btn_choose_photo).setOnClickListener(this);
        showPopupWindow(parentLayout);
    }

    //popupWindows需要基于一个视图容器
    private void showPopupWindow(View parentLayout) {
        mPopupWindow = new PopupWindow(mPopupLayout, getScreenDisplay().widthPixels, (int) (90 * getScreenDisplay().density));
        //设置触摸PopupWindow之外的区域能关闭PopupWindow
        mPopupWindow.setOutsideTouchable(true);
        //设置PopupWindow可触摸
        mPopupWindow.setTouchable(true);
        //设置PopupWindow获取焦点
        mPopupWindow.setFocusable(true);
        //设置popupWindow的背景,必须设置，否则PopupWindow不能隐藏
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置popupWindow进入和隐藏的动画
        mPopupWindow.setAnimationStyle(R.style.styles_pop_window);
        //设置PopupWindow从屏幕底部进入
        mPopupWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 100, 100);
    }

    private DisplayMetrics getScreenDisplay() {
        //创建用于获取屏幕尺寸、像素密度的对象
        Display defaultDisplay = mActivity.getWindowManager().getDefaultDisplay();
        //创建用于获取屏幕尺寸、像素密度等信息的对象
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        return outMetrics;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_picture:
                takePicture();//拍照
                break;
            case R.id.btn_choose_photo:
                choosePhoto();//图库
                break;
        }
    }

    //启动：系统拍照
    private void takePicture() {
        //由于返回缩略图较慢 直接保存后在调用裁剪
        File file = getAvatarPath(mActivity, mFileName + ".jpg");
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        mActivity.startActivityForResult(intent, REQUEST_TAKE_PICTURE);
    }

    //启动：系统相册
    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }


    //得到拍照或从相册选择返回的结果
    public void setAvatar(int requestCode, Intent data, ImageView ivAvatar) {
        switch (requestCode) {
            case REQUEST_TAKE_PICTURE://返回拍照数据
                if (isNull(data)) {
                    //拿到拍照后保存的图片
                    File file = getAvatarPath(mActivity, mFileName + ".jpg");
                    startCropPhotoActivity(Uri.fromFile(file), 200, 200, REQUEST_CROP_PHOTO);
                }
                break;
            case REQUEST_CHOOSE_PHOTO://返回相册数据
                if (!isNull(data)) {
                    startCropPhotoActivity(data.getData(), 200, 200, REQUEST_CROP_PHOTO);
                }
                break;

            case REQUEST_CROP_PHOTO://启动裁剪Activity后 返回数据接收
                saveCropAndShowAvatar(ivAvatar, data);
                closePopupAvatar();//剪切完成关闭悬浮窗PopupWindow
                break;
        }
    }

    //保存头像至sd卡的Android文件夹+显示头像
    private void saveCropAndShowAvatar(ImageView ivIc, Intent data) {
        if (isNull(data.getExtras())) {
            return;
        }
        Bundle extras = data.getExtras();
        Bitmap avatar = extras.getParcelable("data");
        if (isNull(avatar)) {
            return;
        }
        ivIc.setImageBitmap(avatar);
        File file = getAvatarPath(mActivity, mFileName + ".jpg");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            avatar.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!isNull(out)) {
                    out.close();
                }
            } catch (IOException e) {
            }
        }
    }

    //启动系统的Activity裁剪
    private void startCropPhotoActivity(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //显示View为可裁剪的
        intent.putExtra("crop", true);
        //裁剪的宽高的比例为1:1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //输出图片宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        //裁剪之后的数据是通过Intent返回
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        mActivity.startActivityForResult(intent, requestCode);
    }

    //关闭PopupWindow
    public void closePopupAvatar() {
        if (!isNull(mPopupWindow)) {
            mPopupWindow.dismiss();
        }
    }

    //获取剪切后保存图片路径
    public File getAvatarPath(Activity activity, String fileName) {
        File dir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName);
        return file;
    }

    private boolean isNull(Object object) {
        if (object == null) {
            return true;
        } else {
            return false;
        }
    }
}
