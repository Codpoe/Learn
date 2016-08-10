package com.app.learn.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.app.learn.R;

/**
 * Created by Codpoe on 2016/8/11.
 */
public class LearnView extends View {

    private float mWidth;
    private float mHeight;

    private Paint mPaint;

    public LearnView(Context context) {
        this(context, null);
    }

    public LearnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);

        Path path = new Path();
        path.lineTo(200, 200);
        path.setLastPoint(200, 100);
        path.lineTo(200, 0);
        path.close();
        path.moveTo(400, 200);
        path.lineTo(400, 0);
        path.addRect(-300, -300, 300, 300, Path.Direction.CCW);
        path.setLastPoint(-400, 400);

        canvas.drawPath(path, mPaint);

        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        Path path1 = new Path();
        Path path2 = new Path();
        path1.addRect(-100, -100, 100, 100, Path.Direction.CW);
        path2.addCircle(0, 0, 100, Path.Direction.CW);
        path1.addPath(path2, 0, -100);

        canvas.drawPath(path1, mPaint);

    }
}
