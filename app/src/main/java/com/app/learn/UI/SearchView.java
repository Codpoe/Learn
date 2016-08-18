package com.app.learn.UI;

import android.animation.Animator;
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
 * Created by Codpoe on 2016/8/16.
 */
public class SearchView extends View {

    private Paint mPaint;
    private float mCenterX;
    private float mCenterY;

    // View 状态相关
    private static enum State { // 四个状态
        NONE, STARTING, SEARCHING, ENDING
    }
    private State mCurrentState = State.NONE; // 当前状态

    // Path 相关
    private Path mSearchPath; //放大镜 Path
    private Path mCirclePath; // 搜索时的圆圈 Path
    private PathMeasure mMeasure;

    // 动画相关
    private ValueAnimator mStartingAnimator; // 开始时的动画
    private ValueAnimator mSearchingAnimator; // 搜索时的动画
    private ValueAnimator mEndingAnimator; // 结束时的动画
    private float mAnimatorValue = 0; // 动画数值
    private int mDefaultDuration = 1500; // 动画时长

    private ValueAnimator.AnimatorListener mAnimatorListener; // 动画监听器，当前状态的动画结束时，发送 msg 给 Handler
    private ValueAnimator.AnimatorUpdateListener mUpdateListener; // 动画更新监听器

    private Handler mAnimatorHandler; // 用于转换动画状态

    private boolean isOver; // 结束标识

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPath();
        initListener();
        initHandler();
        initAnimator();

    }

    /**
     * 初始化 Paint
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
    }

    /**
     * 初始化 Path
     */
    private void initPath() {
        mSearchPath = new Path();
        mCirclePath = new Path();
        mMeasure = new PathMeasure();

        RectF oval1 = new RectF(-50, -50, 50, 50);
        mSearchPath.addArc(oval1, 45, 359.9f); // 放大镜的圆框

        RectF oval2 = new RectF(-100, -100, 100, 100);
        mCirclePath.addArc(oval2, 45, 359.9f); // 搜索的圆圈

        float[] pos = new float[2];
        mMeasure.setPath(mCirclePath, false);
        mMeasure.getPosTan(0, pos, null); // 放大镜手柄的末端

        mSearchPath.lineTo(pos[0], pos[1]); // 放大镜的手柄
    }

    // 初始化动画
    private void initAnimator() {
        mStartingAnimator = ValueAnimator.ofFloat(0, 1).setDuration(mDefaultDuration);
        mSearchingAnimator = ValueAnimator.ofFloat(0, 1).setDuration(mDefaultDuration);
        mEndingAnimator = ValueAnimator.ofFloat(1, 0).setDuration(mDefaultDuration);

        mStartingAnimator.addListener(mAnimatorListener);
        mSearchingAnimator.addListener(mAnimatorListener);
        mEndingAnimator.addListener(mAnimatorListener);

        mStartingAnimator.addUpdateListener(mUpdateListener);
        mSearchingAnimator.addUpdateListener(mUpdateListener);
        mEndingAnimator.addUpdateListener(mUpdateListener);
    }

    // 初始化动画监听器
    private void initListener() {
        mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimatorHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };

        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        };
    }

    // 初始化 Handler
    private void initHandler() {
        mAnimatorHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (mCurrentState) {
                    case STARTING:
                        isOver = false;
                        mCurrentState = State.SEARCHING;
                        mSearchingAnimator.start();
                        break;
                    case SEARCHING:
                        if (!isOver) {
                            mSearchingAnimator.start();
                        } else {
                            mCurrentState = State.ENDING;
                            mEndingAnimator.start();
                        }
                        break;
                    case ENDING:
                        mCurrentState = State.NONE;
                        break;

                }
            }
        };
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

        switch (mCurrentState) {
            case NONE:
                canvas.drawPath(mSearchPath, mPaint);
                break;
            case STARTING:
                mMeasure.setPath(mSearchPath, false);
                Path dst1 = new Path();
                mMeasure.getSegment(mMeasure.getLength() * mAnimatorValue, mMeasure.getLength(), dst1, true);
                canvas.drawPath(dst1, mPaint);
                break;
            case SEARCHING:
                mMeasure.setPath(mCirclePath, false);
                Path dst2 = new Path();
                mMeasure.getSegment(mMeasure.getLength() * mAnimatorValue - (0.5f - Math.abs(mAnimatorValue - 0.5f)) * mMeasure.getLength() * 0.7f,
                        mMeasure.getLength() * mAnimatorValue,
                        dst2,
                        true
                );
                canvas.drawPath(dst2, mPaint);
                break;
            case ENDING:
                mMeasure.setPath(mSearchPath, false);
                Path dst3 = new Path();
                mMeasure.getSegment(mMeasure.getLength() * mAnimatorValue, mMeasure.getLength(), dst3, true);
                canvas.drawPath(dst3, mPaint);
                break;

        }
    }

    /**
     * 提供给外部的接口方法
     * 控制动画开始
     */
    public void start() {
        if (mCurrentState == State.NONE) {
            isOver = false;
            mCurrentState = State.STARTING;
            mStartingAnimator.start();
        }
    }

    /**
     * 控制动画结束
     */
    public void stop() {
        if (mCurrentState == State.SEARCHING) {
            isOver = true;
        }
    }
}
