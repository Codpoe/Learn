package com.app.learn.UI;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.app.learn.R;

/**
 * Created by Codpoe on 2016/8/18.
 */
public class SmilingFaceView extends View {

    private Paint mPaint; // 画笔
    private Paint mTransparentPaint; // 透明画笔

    private float mCenterX; // 中心 X
    private float mCenterY; // 中心 Y
    private float mRadius; // 半径

    private Path mPath;
    private PathMeasure mMeasure;

    private static enum State {
        NONE, REFRESHING
    }
    private State mCurrentState = State.NONE; // 当前状态
    private Handler mHandler;

    private ValueAnimator valueAnim1;
    private ValueAnimator valueAnim2;
    private ValueAnimator valueAnim3;
    private ValueAnimator valueAnim4;
    private AnimatorSet mStartPAnimSet; // 起点动画
    private AnimatorSet mStopPAnimSet; // 终点动画
    private int mDuration = 1000; // 动画时长
    private float mStartPValue = 0; // 起点动画的数值
    private float mStopPValue = 0; // 终点动画的数值

    private ValueAnimator.AnimatorListener mListener; // 动画监听器
    private ValueAnimator.AnimatorUpdateListener mUpdateListener; // 动画数值监听器

    private boolean hasPoints; // 是否有两点

    private boolean isOver; // 是否结束

    public SmilingFaceView(Context context) {
        this(context, null);
    }

    public SmilingFaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPath();
        initAnimator();
    }

    // 初始化 Paint
    private void initPaint() {
        mPaint = new Paint();
        mTransparentPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);

        mTransparentPaint.set(mPaint);
        mTransparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    // 初始化 Path
    private void initPath() {
        mPath = new Path();
        mMeasure = new PathMeasure();
    }

    // 初始化 Animator
    private void initAnimator() {
//        valueAnim1 = ValueAnimator.ofFloat(0.25f, 1).setDuration(750);
//        valueAnim2 = ValueAnimator.ofFloat(0, 0.5f).setDuration(500);
//        valueAnim3 = ValueAnimator.ofFloat(0.5f, 1f).setDuration(500);
//        valueAnim4 = ValueAnimator.ofFloat(0, 0.25f).setDuration(250);

        valueAnim1 = ValueAnimator.ofFloat(0.25f, 1);
        valueAnim2 = ValueAnimator.ofFloat(1, 1.1f).setDuration(500);
        valueAnim3 = ValueAnimator.ofFloat(1, 2.25f);
        valueAnim4 = ValueAnimator.ofFloat(0.25f, 2.25f);

        valueAnim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStartPValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStartPValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStopPValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
//        valueAnim4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mStartPValue = (float) animation.getAnimatedValue();
//                mStopPValue = (float) animation.getAnimatedValue();
//                invalidate();
//            }
//        });

        mStartPAnimSet = new AnimatorSet();
        mStopPAnimSet = new AnimatorSet();

        mStartPAnimSet.play(valueAnim1).before(valueAnim2).before(valueAnim3).before(valueAnim4);
        mStartPAnimSet.setDuration(2000);

        mStopPAnimSet.play(valueAnim3);
        mStopPAnimSet.setDuration(1500);

        mStartPAnimSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isOver) {
                    mStartPAnimSet.start();
                    mStopPAnimSet.start();
                } else {
                    mCurrentState = State.NONE;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
        mRadius = Math.min(mCenterX, mCenterY) * 0.9f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mCenterX, mCenterY);

        RectF rectF = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        mPath.addCircle(0, 0, mRadius, Path.Direction.CW);
        mMeasure.setPath(mPath, false);

        hasPoints = false;

        switch (mCurrentState) {
            case NONE:
                Path dst1 = new Path();
                mMeasure.getSegment(0, mMeasure.getLength() * 0.5f, dst1, true);
                canvas.drawPath(dst1, mPaint);
                drawPoint1(canvas, mPaint);
                drawPoint2(canvas, mPaint);
                break;
            case REFRESHING:
                Path dst2 = new Path();
                mMeasure.getSegment(mMeasure.getLength() * mStartPValue, mMeasure.getLength() * mStopPValue + 10, dst2, true);
                canvas.drawPath(dst2, mPaint);
                if (!hasPoints) {

                }
                if (mStopPValue >= 0.875f) {
                    drawPoint1(canvas, mPaint);
                }
                if (mStopPValue >= 1.125f) {
                    drawPoint2(canvas, mPaint);
                }
                break;
        }
    }

    // 画点 1，即左眼
    private void drawPoint1(Canvas canvas, Paint paint) {
        canvas.drawPoint(-mRadius * (float) (Math.cos(45)), -mRadius * (float) (Math.sin(45)), paint);
    }

    // 画点 2，即右眼
    private void drawPoint2(Canvas canvas, Paint paint) {
        canvas.drawPoint(mRadius * (float) (Math.cos(45)), -mRadius * (float) (Math.sin(45)), paint);
    }

    /**
     * 提供给外部的方法
     */
    public void start() {
        if (mCurrentState == State.NONE) {
            isOver = false;
            mCurrentState = State.REFRESHING;
            mStartPAnimSet.start();
            mStopPAnimSet.start();
        }
    }

    public void stop() {
        isOver = true;
    }

}
