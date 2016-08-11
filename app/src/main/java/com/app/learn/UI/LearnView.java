package com.app.learn.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
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
        mPaint.setAntiAlias(true);

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);

        // Path 直线操作
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

        // Path 基本图形操作
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        Path path1 = new Path();
        Path path2 = new Path();
        path1.addRect(-100, -100, 100, 100, Path.Direction.CW);
        path2.addCircle(0, 0, 100, Path.Direction.CW);
        path1.addPath(path2, 0, -100);

        canvas.drawPath(path1, mPaint);

        // Path 弧线操作
        RectF rectF = new RectF(0, -400, 100, -300);
        Path path3 = new Path();
        path3.moveTo(0, -300);
        path3.lineTo(100, -200);
        path3.arcTo(rectF, 180, 180, true);

        canvas.drawPath(path3, mPaint);

        Path path4 = new Path();
        Path path5 = new Path();
        path4.addCircle(0, -600, 100, Path.Direction.CW);
        path5.addRect(-100, -700, 100, -500, Path.Direction.CW);
        path4.offset(100, 0, path5); // offset(float dx, float dy, Path dst) 中的 dx 和 dy 是偏移量，不是绝对量

        canvas.drawPath(path4, mPaint);
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawPath(path5, mPaint);

    }
}
