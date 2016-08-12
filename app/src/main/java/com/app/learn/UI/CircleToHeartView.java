package com.app.learn.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import com.app.learn.R;

/**
 * Created by Codpoe on 2016/8/12.
 */
public class CircleToHeartView extends View {

    private static final float C = 0.551915024494f; // 用于辅助计算绘制圆形的控制点

    private Paint mPaint;

    private float mCenterX;
    private float mCenterY;
    private float mRadius; // 圆的半径
    private float mDifference; // 数据点和控制点之间的距离
    private PointF p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11;

    private float mDuration; // 变化时长
    private float mCurDuration; // 已变化时长
    private float mCount; // 变化的细分
    private float mPerDuration; // 每份变化的时长

    public CircleToHeartView(Context context) {
        this(context, null);
    }

    public CircleToHeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mCenterX, mCenterY);

        Path path = new Path();
        path.moveTo(p0.x, p0.y);
        path.cubicTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
        path.cubicTo(p4.x, p4.y, p5.x, p5.y, p6.x, p6.y);
        path.cubicTo(p7.x, p7.y, p8.x, p8.y, p9.x, p9.y);
        path.cubicTo(p10.x, p10.y, p11.x, p11.y, p0.x, p0.y);

        canvas.drawPath(path, mPaint);
        // 圆 ○ -> 心 ❤
        if (mCurDuration <= mDuration) {
            p9.y += 120 / mCount;
            p2.y -= 80 / mCount;
            p4.y -= 80 / mCount;
            p1.x -= 20 / mCount;
            p5.x += 20 / mCount;
            p7.x -= 14 / mCount;
            p11.x += 14 /mCount;
            mCurDuration += mPerDuration;
            postInvalidateDelayed((long) mPerDuration);
        }

    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);

        mRadius = 200;
        mDifference = mRadius * C;
        p0 = new PointF(mRadius, 0);
        p1 = new PointF(mRadius, mDifference);
        p2 = new PointF(mDifference, mRadius);
        p3 = new PointF(0, mRadius);
        p4 = new PointF(-mDifference, mRadius);
        p5 = new PointF(-mRadius, mDifference);
        p6 = new PointF(-mRadius, 0);
        p7 = new PointF(-mRadius, - mDifference);
        p8 = new PointF(-mDifference, -mRadius);
        p9 = new PointF(0, -mRadius);
        p10 = new PointF(mDifference, - mRadius);
        p11 = new PointF(mRadius, -mDifference);

        mDuration = 500;
        mCurDuration = 0;
        mCount = 100;
        mPerDuration = mDuration / mCount;
    }
}
