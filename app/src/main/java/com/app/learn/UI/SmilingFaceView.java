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
import android.os.Message;
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
    private Path dst1;
    private Path dst2;
    private PathMeasure mMeasure;

    private static enum State {
        NONE, REFRESHING
    }
    private State mCurrentState = State.NONE; // 当前状态

    private ValueAnimator valueAnim1;
    private ValueAnimator valueAnim2;
    private AnimatorSet mAnimSet; // 组合动画
    private int mDuration = 1000; // 动画时长
    private float mStartPValue = 0.1f; // 起点动画的数值
    private float mStopPValue = 0.1f; // 终点动画的数值

    private ValueAnimator.AnimatorListener mListener; // 动画监听器
    private ValueAnimator.AnimatorUpdateListener mStartPUpdateListener; // 动画数值监听器
    private ValueAnimator.AnimatorUpdateListener mStopPUpdateListener;

    private Handler mHandler;

    private boolean isOver; // 是否结束
    private boolean isPause = false;

    public SmilingFaceView(Context context) {
        this(context, null);
    }

    public SmilingFaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPath();
        initListener();
        initAnimator();
        initHandler();
    }

    /**
     * 初始化 Paint
     */
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

    /**
     * 初始化 Path
     */
    private void initPath() {
        mPath = new Path();
        dst1 = new Path();
        dst2 = new Path();
        mMeasure = new PathMeasure();
    }

    private void initListener() {

        mStartPUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStartPValue = (float) animation.getAnimatedValue();
                invalidate();

            }
        };

        mStopPUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStopPValue = (float) animation.getAnimatedValue();
                if (!isPause && (int) (mStopPValue * 10) == 4) {
                    isPause = true;
                    valueAnim1.pause();
                }
                if (isPause && (int) (mStopPValue * 10) == 6) {
                    isPause = false;
                    valueAnim1.resume();
                }
                invalidate();
            }
        };

        mListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };

    }

    /**
     * 初始化 Handler
     */
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (mCurrentState) {
                    case REFRESHING:
                        if (!isOver) {
                            mAnimSet.start();
                        } else {
                            mCurrentState = State.NONE;
                            invalidate();
                        }
                }
            }
        };
    }

    /**
     * 初始化 Animator
     */
    private void initAnimator() {
        valueAnim1 = ValueAnimator.ofFloat(0.1f, 0.9f).setDuration(1500);
        valueAnim2 = ValueAnimator.ofFloat(0.1f, 0.9f).setDuration(1500);
        mAnimSet = new AnimatorSet();
        mAnimSet.play(valueAnim1).with(valueAnim2);

        valueAnim1.addUpdateListener(mStartPUpdateListener);
        valueAnim2.addUpdateListener(mStopPUpdateListener);

        valueAnim1.addListener(mListener);

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

        RectF rectF1 = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        mPath.addArc(rectF1, 0, 359.99f);
        mPath.arcTo(rectF1, 359.99f, 359.99f, false);
        mPath.arcTo(rectF1, 359.98f, 180.02f, false);
        mMeasure.setPath(mPath, false);

        switch (mCurrentState) {
            case NONE:
                mMeasure.getSegment(0, mMeasure.getLength() * 0.2f, dst1, true);
                canvas.drawPath(dst1, mPaint);
                drawPoint1(canvas, mPaint);
                drawPoint2(canvas, mPaint);
                break;
            case REFRESHING:
                dst2.reset();
                mMeasure.getSegment(mMeasure.getLength() * mStartPValue, mMeasure.getLength() * (mStopPValue + 0.0001f), dst2, true);
                canvas.drawPath(dst2, mPaint);
                if (mStopPValue >= 0.25f && mStopPValue <= 0.65f) {
                    drawPoint1(canvas, mPaint);
                }
                if (mStopPValue >= 0.35f && mStopPValue <= 0.75f) {
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
            mAnimSet.start();
        }
    }

    public void stop() {
        isOver = true;
    }

}
