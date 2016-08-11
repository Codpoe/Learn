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
public class BezierView extends View {

    private float centerX;
    private float centerY;
    private PointF startPoint;
    private PointF endPoint;
    private PointF controlPoint;
    private Paint bezierPaint;
    private Paint auxiliaryPaint;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
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
        auxiliaryPaint.setAntiAlias(true);
        // 初始化 point
        startPoint = new PointF(0, 0);
        endPoint = new PointF(0, 0);
        controlPoint = new PointF(0, 0);
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
        controlPoint.x = centerX;
        controlPoint.y = centerY - 100;

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controlPoint.x = event.getX();
        controlPoint.y = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPoint(startPoint.x, startPoint.y, bezierPaint);
        canvas.drawPoint(endPoint.x, endPoint.y, bezierPaint);
        canvas.drawPoint(controlPoint.x, controlPoint.y, auxiliaryPaint);

        canvas.drawLine(startPoint.x, startPoint.y, controlPoint.x, controlPoint.y, auxiliaryPaint);
        canvas.drawLine(endPoint.x, endPoint.y, controlPoint.x, controlPoint.y, auxiliaryPaint);

        Path path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        path.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y);

        canvas.drawPath(path, bezierPaint);
    }
}
