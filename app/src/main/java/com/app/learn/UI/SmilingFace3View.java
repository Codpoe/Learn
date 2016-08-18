package com.app.learn.UI;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.app.learn.R;

/**
 * Created by Codpoe on 2016/8/18.
 */
public class SmilingFace3View extends View {

    private Paint mPaint; // 画笔
    private Paint mTransparentPaint; // 透明画笔

    private float mCenterX; // 中心 X
    private float mCenterY; // 中心 Y
    private float mRadius; // 半径

    private Path mPath;
    private PathMeasure mMeasure;

    private static enum State { // 动画的几个状态
        NONE,
        MOUTH_TO_POINT,
        POINT,
        POINT_TO_MOUTH
    }
    private State mCurrentState = State.NONE;
    private ValueAnimator mM2PAnimator; // 嘴变点的动画
    private ValueAnimator mPAnimator; // 点的动画
    private ValueAnimator mP2MAnimator; // 点变嘴的动画
    private int mDuration = 500; // 动画时长
    private float mAnimatorValue = 0; // 动画数值

    private ValueAnimator.AnimatorListener mListener; // 动画监听器
    private ValueAnimator.AnimatorUpdateListener mUpdateListener; // 动画数值监听器

    private Handler mHandler; // 转换动画状态

    private boolean hasPoints;

    private boolean isOver;

    public SmilingFace3View(Context context) {
        this(context, null);
    }

    public SmilingFace3View(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPath();
        initListener();
        initHandler();
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
        mM2PAnimator = ValueAnimator.ofFloat(0, 0.48f).setDuration(mDuration);
        mPAnimator = ValueAnimator.ofFloat(0.5f, 1).setDuration(mDuration);
        mP2MAnimator = ValueAnimator.ofFloat(0, 0.5f).setDuration(mDuration);

        mM2PAnimator.addListener(mListener);
        mPAnimator.addListener(mListener);
        mP2MAnimator.addListener(mListener);

        mM2PAnimator.addUpdateListener(mUpdateListener);
        mPAnimator.addUpdateListener(mUpdateListener);
        mP2MAnimator.addUpdateListener(mUpdateListener);

    }

    // 初始化 AnimatorListener
    private void initListener() {
        mListener = new ValueAnimator.AnimatorListener() {
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
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (mCurrentState) {
                    case MOUTH_TO_POINT:
                        mCurrentState = State.POINT;
                        mPAnimator.start();
                        break;
                    case POINT:
                        mCurrentState = State.POINT_TO_MOUTH;
                        mP2MAnimator.start();
                        break;
                    case POINT_TO_MOUTH:
                        if (!isOver) {
                            mCurrentState = State.MOUTH_TO_POINT;
                            mM2PAnimator.start();
                        } else {
                            mCurrentState = State.NONE;
                        }
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
        mRadius = Math.min(mCenterX, mCenterY) * 0.9f;
        mPath.addCircle(0, 0, mRadius, Path.Direction.CW);
        mMeasure.setPath(mPath, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mCenterX, mCenterY);

        hasPoints = true;
        switch (mCurrentState) {
            case NONE:
                Path dst1 = new Path();
                mMeasure.getSegment(0, mMeasure.getLength() * 0.5f, dst1, true);
                canvas.drawPath(dst1, mPaint);
                hasPoints = true;
                drawPoint1(canvas, mPaint);
                drawPoint2(canvas, mPaint);
                break;
            case MOUTH_TO_POINT:
                Path dst2 = new Path();
                mMeasure.getSegment(mMeasure.getLength() * mAnimatorValue, mMeasure.getLength() * 0.5f, dst2, true);
                canvas.drawPath(dst2, mPaint);
                if (hasPoints) {
                    drawPoint1(canvas, mPaint);
                    drawPoint2(canvas, mPaint);
                }
                break;
            case POINT:
                Path dst3 = new Path();
                mMeasure.getSegment(mMeasure.getLength() * mAnimatorValue, mMeasure.getLength() * mAnimatorValue + 10, dst3, true);
                canvas.drawPath(dst3, mPaint);
                if (hasPoints) {
                    if (mAnimatorValue >= 0.625) {
                        drawPoint1(canvas, mTransparentPaint);
                    }
                    if (mAnimatorValue >= 0.875) {
                        drawPoint2(canvas, mTransparentPaint);
                    }
                    hasPoints = false;
                } else {
                    if (mAnimatorValue >= 0.625) {
                        drawPoint1(canvas, mPaint);
                    }
                    if (mAnimatorValue >= 0.875) {
                        drawPoint2(canvas, mPaint);
                    }
                    hasPoints = true;
                }
                break;
            case POINT_TO_MOUTH:
                Path dst4 = new Path();
                mMeasure.getSegment(0, mMeasure.getLength() * mAnimatorValue, dst4, true);
                canvas.drawPath(dst4, mPaint);
                if (hasPoints) {
                    drawPoint1(canvas, mPaint);
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
            mCurrentState = State.MOUTH_TO_POINT;
            mM2PAnimator.start();
        }
    }

    public void stop() {
        isOver = true;
    }
}
