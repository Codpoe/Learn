package com.app.learn.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.app.learn.R;

/**
 * Created by Codpoe on 2016/8/11.
 */
public class Bezier2View extends View {

    private float centerX;
    private float centerY;
    private PointF startPoint;
    private PointF endPoint;
    private PointF controlPoint1;
    private PointF controlPoint2;
    private boolean isPoint1;
    private Paint bezierPaint;
    private Paint auxiliaryPaint;

    public Bezier2View(Context context) {
        this(context, null);
    }

    public Bezier2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化 bezierPaint
        bezierPaint = new Paint();
        bezierPaint.setColor(getResources().getColor(R.color.colorAccent));
        bezierPaint.setStyle(Paint.Style.STROKE);
        bezierPaint.setStrokeWidth(10);
        bezierPaint.setStrokeCap(Paint.Cap.ROUND);
        bezierPaint.setAntiAlias(true);
        // 初始化 auxiliaryPaint
        auxiliaryPaint = new Paint();
        auxiliaryPaint.setColor(getResources().getColor(R.color.colorPrimary));
        auxiliaryPaint.setStyle(Paint.Style.STROKE);
        auxiliaryPaint.setStrokeWidth(4);
        auxiliaryPaint.setStrokeCap(Paint.Cap.ROUND);
        auxiliaryPaint.setAntiAlias(true);
        // 初始化 Point
        startPoint = new PointF(0, 0);
        endPoint = new PointF(0, 0);
        controlPoint1 = new PointF(0, 0);
        controlPoint2 = new PointF(0, 0);
        // 是否是控制点 1
        isPoint1 = true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;

        startPoint.x = centerX - 200;
        startPoint.y = centerY;
        endPoint.x = centerX + 200;
        endPoint.y = centerY;
        controlPoint1.x = centerX;
        controlPoint1.y = centerY - 100;
        controlPoint2.x = centerX;
        controlPoint2.y = centerY + 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制辅助线
        Path path1 = new Path();
        path1.moveTo(startPoint.x, startPoint.y);
        path1.lineTo(controlPoint1.x, controlPoint1.y);
        path1.lineTo(controlPoint2.x, controlPoint2.y);
        path1.lineTo(endPoint.x, endPoint.y);
        canvas.drawPath(path1, auxiliaryPaint);
        // 绘制贝塞尔曲线
        Path path2 = new Path();
        path2.moveTo(startPoint.x, startPoint.y);
        path2.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y, endPoint.x, endPoint.y);
        canvas.drawPath(path2, bezierPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isPoint1) {
            controlPoint1.set(event.getX(), event.getY());
        } else {
            controlPoint2.set(event.getX(), event.getY());
        }
        invalidate();
        return true;
    }

    /**
     * 设置控制哪个点
     * @param b
     */
    public void setIsPoint1(boolean b) {
        isPoint1 = b;
    }
}
