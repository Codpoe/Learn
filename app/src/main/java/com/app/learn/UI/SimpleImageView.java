package com.app.learn.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.app.learn.R;
import com.app.learn.Util.ImageUtils;

/**
 * Created by Codpoe on 2016/7/29.
 */
public class SimpleImageView  extends View {
    // 画笔
    private Paint mPaint;
    // 图片
    private Drawable mDrawable;
    // View 的宽度
    private int mWidth;
    // View 的高度
    private int mHeight;

    // 构造方法
    public SimpleImageView(Context context) {
        this(context, null);
    }
    // 构造方法
    public SimpleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 根据属性初始化
        initAttrs(attrs);
        // 初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = null;
            try {
                array = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleImageView);
                // 根据图片 id 获取到 drawable 对象
                mDrawable = array.getDrawable(R.styleable.SimpleImageView_src);
                // 测量 Drawable 对象的宽、高
                measureDrawable();
            } finally {
                if (array != null) {
                    array.recycle();
                }
            }
        }
    }

    public void measureDrawable() {
        if (mDrawable == null) {
            throw new RuntimeException("drawable 不能为空!");
        }
        mWidth = mDrawable.getIntrinsicWidth();
        mHeight = mDrawable.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获取宽度的模式与大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        // 获取高度的模式与大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        // 设置 View 的宽高
        setMeasuredDimension(measureWidth(widthMode, width), measureHeight(heightMode, height));
    }

    private int measureWidth(int mode, int width) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mWidth = width;
                break;
        }
        return mWidth;
    }

    private int measureHeight(int mode, int height) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mHeight = height;
                break;
        }
        return mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制图片
        Bitmap bitmap = Bitmap.createScaledBitmap(ImageUtils.drawableToBitmap(mDrawable), mWidth, mHeight, true);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        // 绘制文字
        canvas.save();
        canvas.rotate(90);
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(50);
        mPaint.setTextSkewX(0.5f);
        canvas.drawText("miaopasi", 50, -50, mPaint);
        canvas.restore();
        // 绘制点
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10f);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoints(new float[]{
                100, 100,
                120, 120,
                140, 140
        }, mPaint);
        // 绘制线
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawLines(new float[]{
                300, 50, 400, 180,
                300, 310, 400, 180,
                500, 50, 400, 180
        }, mPaint);
        // 绘制矩形
        RectF rectF1 = new RectF(540, 50, 660, 110);
        canvas.drawRect(rectF1, mPaint);
        // 绘制圆角矩形
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        canvas.drawRoundRect(rectF1, 10, 10, mPaint);
        // 绘制椭圆
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawOval(rectF1, mPaint);
        // 绘制圆形
        canvas.drawCircle(200, 500, 50, mPaint);
        // 绘制圆弧
        RectF rectF2 = new RectF(100, 700, 300, 900);
        canvas.drawArc(rectF2, 0, 90, true, mPaint);
        // 画布缩放
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rectF3 = new RectF(0, -150, 150, 0);
        canvas.drawRect(rectF3, mPaint);
        canvas.scale(-1.5f, -1.5f, 75, 0);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        canvas.drawRect(rectF3, mPaint);
        canvas.drawPoints(new float[] {
                0, -150,
                0, 150,
                50, 50,
        }, mPaint);
        canvas.restore();
        // 画布旋转
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.save();
        canvas.translate(mWidth * 2 / 3, mHeight);
        canvas.drawCircle(0, 0, 300, mPaint);
        canvas.drawCircle(0, 0, 270, mPaint);
        for (int i = 0; i < 360; i += 10) {
            canvas.drawLine(270, 0, 300, 0, mPaint);
            canvas.rotate(10);
        }
        canvas.restore();
    }
}
