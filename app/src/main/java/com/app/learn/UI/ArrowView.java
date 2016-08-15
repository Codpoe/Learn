package com.app.learn.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import com.app.learn.R;

/**
 * Created by Codpoe on 2016/8/16.
 */
public class ArrowView extends View {

    private float mCenterX;
    private float mCenterY;
    private Paint mPaint;
    private Bitmap mBitmap; // 箭头图片
    private Matrix mMatrix; // 矩阵，用于对图片进行一些操作
    private float[] pos; // 当前点的位置
    private float[] tan; // 当前点的 tan 值，用于计算图片旋转的角度
    private float curValue; // 用于映射 Path，取值范围：[0, 1]

    public ArrowView(Context context) {
        this(context, null);
    }

    public ArrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
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
        path.addCircle(0, 0, 200, Path.Direction.CW);

        PathMeasure measure = new PathMeasure(path, false);

        curValue += 0.005;
        if (curValue >= 1) {
            curValue = 0;
        }

        measure.getPosTan(measure.getLength() * curValue, pos, tan);

        mMatrix.reset(); // 重置 Matrix
        float degree = (float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI); // 图片所需旋转角度
        mMatrix.postRotate(degree, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2); // 旋转图片
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2); // 平移图片

        canvas.drawPath(path, mPaint); // 绘制 Path
        canvas.drawBitmap(mBitmap, mMatrix, mPaint); // 绘制箭头

        invalidate(); // 刷新

    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow, options);
        mMatrix = new Matrix();

        pos = new float[2];
        tan = new float[2];

        curValue = 0;
    }
}
