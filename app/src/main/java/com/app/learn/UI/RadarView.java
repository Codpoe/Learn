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
public class RadarView extends View {
    private int count = 6; // 数据个数，维度
    private float angle = (float) (2 * Math.PI / count);
    private float centerX; // 中心 X
    private float centerY; // 中心 Y
    private float radius; // 半径
    private String[] titles = {"A", "B", "C", "D", "E", "F"};
    private double[] datas = {100, 70, 70, 80, 90, 30};
    private float maxValue = 100;
    private Paint radarPaint; // 雷达画笔
    private Paint valuePaint; // 数据画笔
    private Paint textPaint; // 文本画笔

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化 radarPaint
        radarPaint = new Paint();
        radarPaint.setColor(getResources().getColor(R.color.radarColor));
        radarPaint.setStyle(Paint.Style.STROKE);
        radarPaint.setStrokeCap(Paint.Cap.ROUND);
        radarPaint.setStrokeWidth(4);
        radarPaint.setAntiAlias(true);
        // 初始化 valuePaint
        valuePaint = new Paint();
        valuePaint.setColor(getResources().getColor(R.color.colorAccent));
        valuePaint.setStyle(Paint.Style.FILL);
        valuePaint.setStrokeCap(Paint.Cap.ROUND);
        valuePaint.setAntiAlias(true);
        // 初始化 textPaint
        textPaint = new Paint();
        textPaint.setColor(getResources().getColor(R.color.colorAccent));
        textPaint.setTextSize(30);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        radius = Math.min(centerX, centerY) * 0.9f;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);
        drawLines(canvas);
        drawRegion(canvas);
        drawText(canvas);
    }

    /**
     * 绘制多边形
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        float r = radius / (count - 1); // 蛛丝之间的距离
        float curR; // 当前半径
        for (int i = 1; i < count; i ++) {
            path.reset();
            curR = r * i;
            for (int j = 0; j < count; j ++) {
                if (j == 0) {
                    path.moveTo(centerX + curR, centerY);
                } else { // 根据半径，计算每个点的坐标
                    path.lineTo((float) (centerX + curR * Math.cos(angle * j)),
                            (float) (centerY + curR * Math.sin(angle * j)));
                }
            }
            path.close();
            canvas.drawPath(path, radarPaint);
        }
    }

    /**
     * 绘制从中心到顶点的直线
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < count; i ++) {
            path.moveTo(centerX, centerY);
            path.lineTo((float) (centerX + radius * Math.cos(angle * i)),
                    (float) (centerY + radius * Math.sin(angle * i)));
        }
        canvas.drawPath(path, radarPaint);
    }

    /**
     * 绘制覆盖区域
     */
    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        float x;
        float y;
        for (int i = 0; i < count; i ++) {
            x = (float) (centerX + radius * (datas[i] / maxValue) * Math.cos(angle * i));
            y = (float) (centerY + radius * (datas[i] / maxValue) * Math.sin(angle * i));
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            canvas.drawCircle(x, y, 10, valuePaint);
        }
        path.close();
        valuePaint.setColor(getResources().getColor(R.color.colorAccent));
        valuePaint.setAlpha(200);
        canvas.drawPath(path, valuePaint);
    }

    /**
     * 绘制文本
     */
    private void drawText(Canvas canvas) {
        float x;
        float y;
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for(int i=0;i<count;i++){
            x = (float) (centerX + (radius + fontHeight / 2) * Math.cos(angle * i));
            y = (float) (centerY + (radius + fontHeight / 2) * Math.sin(angle * i));
            if(angle * i >= 0 && angle * i <= Math.PI / 2){ // 第 4 象限
                canvas.drawText(titles[i], x, y, textPaint);
            } else if(angle * i >= 3 * Math.PI / 2 && angle * i <= Math.PI * 2){ // 第 3 象限
                canvas.drawText(titles[i], x, y, textPaint);
            } else if(angle * i > Math.PI / 2 && angle * i <= Math.PI){ // 第 2 象限
                float dis = textPaint.measureText(titles[i]); // 文本长度
                canvas.drawText(titles[i], x - dis, y, textPaint);
            } else if(angle * i >= Math.PI && angle * i < 3 * Math.PI / 2){ // 第 1 象限
                float dis = textPaint.measureText(titles[i]); // 文本长度
                canvas.drawText(titles[i], x - dis, y, textPaint);
            }
        }
    }
}
