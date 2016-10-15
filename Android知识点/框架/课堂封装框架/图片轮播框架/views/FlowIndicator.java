package cn.ucai.day05_04.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import cn.ucai.day05_04.R;

/** 指示器控件，与图片轮播的控件配合使用，指示图片轮播的焦点位置
 * Created by yao on 2016/5/20.
 */
public class FlowIndicator extends View{
    /** 实心圆的数量*/
    int mCount;
    /** 实心圆的半径*/
    int mRadius;
    /** 焦点索引*/
    int mFocus;
    /** 两个实心圆的间距*/
    int mSpace;
    /** 非焦点的实心圆的颜色值*/
    int mNormalColor;
    /** 焦点实心圆的罨*/
    int mFocusColor;

    Paint mPaint;
    public FlowIndicator(Context context) {
        super(context);
    }

    public FlowIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        //从布局文件中拿到自定义属性的数组
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowIndicator);
        mCount = array.getInt(R.styleable.FlowIndicator_count, 0);
        mRadius = array.getDimensionPixelOffset(R.styleable.FlowIndicator_radius, 15);
        mFocus = array.getInt(R.styleable.FlowIndicator_focus, 0);
        mSpace = array.getDimensionPixelSize(R.styleable.FlowIndicator_space, 10);
        mNormalColor = array.getColor(R.styleable.FlowIndicator_normalColor, 0xccc);
        mFocusColor = array.getColor(R.styleable.FlowIndicator_focusColor, 0xff5300);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void setFocus(int focus) {
        mFocus=focus;
        invalidate();
    }
    //测量FlowIndicator的宽和高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
    }

    /** 计算控件的高度*/
    private int measureHeight(int heightMeasureSpec) {
        //获取测量规格
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        //获取系统为控件测量的高度
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int result=size;//result保存最终的结果
        if (mode == MeasureSpec.AT_MOST) {
            size=getPaddingBottom()+getPaddingTop()+2*mRadius;
            result = Math.min(result, size);
        }
        return result;
    }

    /** 计算控件的宽度*/
    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int result=size;
        if (mode == MeasureSpec.AT_MOST) {
            size=getPaddingLeft()+getPaddingRight()+mCount*2*mRadius+(mCount-1)*mSpace;
            result = Math.min(result, size);
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int leftSpce=(getWidth()-(mCount*2*mRadius+(mCount-1)*mSpace))/2;
        for(int i=0;i<mCount;i++) {
            /*
               第1个圆的x=mRadius
               第2个圆的x=2*mRadius+mSpace+mRadius
             */
            int x=leftSpce+mRadius+i*(2*mRadius+mSpace);
            int color=i==mFocus?mFocusColor:mNormalColor;
            mPaint.setColor(color);
            canvas.drawCircle(x,getHeight()/2,mRadius,mPaint);
        }
    }
}
