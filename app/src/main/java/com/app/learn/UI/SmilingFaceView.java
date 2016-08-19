package com.app.learn.UI;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
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
    int mColor;

    private float mCenterX; // 中心 X
    private float mCenterY; // 中心 Y
    private float mRadius; // 半径

    private Path mPath;
    private Path dst1;
    private Path dst2;
    private Path dst3;
    private PathMeasure mMeasure;

    private static enum State { // 三个状态：初始，刷新中，刷新完成
        NONE, REFRESHING, OK
    }
    private State mCurrentState = State.NONE; // 当前状态
    private Handler mHandler; // 用于状态切换

    private ValueAnimator mStartPAnim; // 起点动画
    private ValueAnimator mStopPAnim; // 终点动画
    private ValueAnimator mOkAnim; // 刷新完成的动画
    private AnimatorSet mAnimSet; // 刷新的组合动画
    private int mDuration = 1500; // 动画时长
    private float mStartPValue = 0.1f; // 起点动画的数值
    private float mStopPValue = 0.1f; // 终点动画的数值
    private float mOkValue = 0.1f; // 刷新完成动画的数值

    private ValueAnimator.AnimatorListener mListener; // 动画监听器
    private ValueAnimator.AnimatorUpdateListener mStartPUpdateListener; // 起点的动画数值监听器
    private ValueAnimator.AnimatorUpdateListener mStopPUpdateListener; // 终点的动画数值监听器
    private ValueAnimator.AnimatorUpdateListener mOkUpdateListener; // 刷新完成的动画监听器

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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
        mRadius = Math.min(mCenterX, mCenterY) * 0.8f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mCenterX, mCenterY); // 移到画布中心

        // 添加Path
        RectF rectF1 = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        mPath.addArc(rectF1, 0, 359.99f);
        mPath.arcTo(rectF1, 359.99f, 359.99f, false);
        mPath.arcTo(rectF1, 359.98f, 180.02f, false);
        mMeasure.setPath(mPath, false);

        switch (mCurrentState) {
            case NONE:
                mMeasure.getSegment(0, mMeasure.getLength() * 0.2f, dst1, true);
                canvas.drawPath(dst1, mPaint);
                drawPoint1(canvas);
                drawPoint2(canvas);
                break;
            case REFRESHING:
                dst2.reset();
                mMeasure.getSegment(mMeasure.getLength() * mStartPValue, mMeasure.getLength() * mStopPValue, dst2, true);
                canvas.drawPath(dst2, mPaint);
                if (mStopPValue >= 0.25f && mStopPValue <= 0.65f) {
                    drawPoint1(canvas);
                }
                if (mStopPValue >= 0.35f && mStopPValue <= 0.75f) {
                    drawPoint2(canvas);
                }
                break;
            case OK:
                dst3.reset();
                mMeasure.getSegment(
                        mMeasure.getLength() * (-mOkValue + 0.1f),
                        mMeasure.getLength() * (mOkValue + 0.1f),
                        dst3,
                        true
                );
                canvas.drawPath(dst3, mPaint);
                if (mOkValue == 0.1f) {
                    drawPoint1(canvas);
                    drawPoint2(canvas);
                }
                break;
        }
    }

    /**
     * 初始化 Paint
     */
    private void initPaint() {
        mPaint = new Paint();
        mColor = getResources().getColor(R.color.colorAccent);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);

    }

    /**
     * 初始化 Path
     */
    private void initPath() {
        mPath = new Path();
        dst1 = new Path();
        dst2 = new Path();
        dst3 = new Path();
        mMeasure = new PathMeasure();
    }

    /**
     * 初始化 Listener
     */
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
                mStopPValue = (float) animation.getAnimatedValue() + 0.001f;
                if (!isPause && (int) (mStopPValue * 10) == 4) {
                    isPause = true;
                    mStartPAnim.pause();
                }
                if (isPause && (int) (mStopPValue * 10) == 6) {
                    isPause = false;
                    mStartPAnim.resume();
                }
                invalidate();
            }
        };

        mOkUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOkValue = (float) animation.getAnimatedValue();
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
                            mCurrentState = State.OK;
                            mOkAnim.start();
                        }
                        break;
                    case OK:
                        mCurrentState = State.NONE;
                        break;
                }
            }
        };
    }

    /**
     * 初始化 Animator
     */
    private void initAnimator() {
        mStartPAnim = ValueAnimator.ofFloat(0.1f, 0.9f).setDuration(mDuration);
        mStopPAnim = ValueAnimator.ofFloat(0.1f, 0.9f).setDuration(mDuration);
        mAnimSet = new AnimatorSet();
        mAnimSet.play(mStartPAnim).with(mStopPAnim);

        mOkAnim = ValueAnimator.ofFloat(0, 0.1f).setDuration(mDuration / 5);

        mStartPAnim.addUpdateListener(mStartPUpdateListener);
        mStopPAnim.addUpdateListener(mStopPUpdateListener);
        mOkAnim.addUpdateListener(mOkUpdateListener);

        mAnimSet.addListener(mListener);
        mOkAnim.addListener(mListener);

    }

    /**
     * 画点 1，即左眼
     * @param canvas
     */
    private void drawPoint1(Canvas canvas) {
        canvas.drawPoint(-mRadius * (float) (Math.cos(45)), -mRadius * (float) (Math.sin(45)), mPaint);
    }

    /**
     * 画点 2，即右眼
     * @param canvas
     */
    private void drawPoint2(Canvas canvas) {
        canvas.drawPoint(mRadius * (float) (Math.cos(45)), -mRadius * (float) (Math.sin(45)), mPaint);
    }

    /**
     * 提供给外部的方法
     */

    /**
     * 开始刷新
     */
    public void start() {
        if (mCurrentState == State.NONE) {
            isOver = false;
            mCurrentState = State.REFRESHING;
            mAnimSet.start();
        }
    }

    /**
     * 结束刷新
     */
    public void stop() {
        isOver = true;
    }

    /**
     * 设置动画时长
     * @param duration
     */
    public void setDuration(int duration) {
        mDuration = duration;
        mStartPAnim.setDuration(mDuration);
        mStopPAnim.setDuration(mDuration);
        mOkAnim.setDuration(mDuration / 5);
        invalidate();
    }

    /**
     * 设置画笔颜色
     * @param color
     */
    public void setColor(int color) {
        mColor = color;
        mPaint.setColor(mColor);
        invalidate();
    }

}
