package com.app.learn.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.app.learn.model.PieData;

import java.util.List;

/**
 * Created by Codpoe on 2016/8/9.
 */
public class PieView extends View {

    private int mWidth; // 宽度
    private int mHeight; // 高度
    private List<PieData> mPieDataList; // 数据
    private float mStartAngle = 0; // 开始角度
    private int[] mColors = { // 颜色
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.GRAY
    };
    private Paint mPaint; // 画笔

    /**
     * 构造方法
     * @param context
     */
    public PieView(Context context) {
        this(context, null);
    }

    /**
     * 构造方法
     * @param context
     * @param attrs
     */
    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
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
        if (mPieDataList == null) {
            return;
        }
        canvas.translate(mWidth / 2, mHeight / 2); // 画布移动到中心
        float radius = Math.min(mWidth, mHeight) / 2; // 半径
        RectF rectF = new RectF(-radius, -radius, radius, radius); // 矩形
        float curAngle = mStartAngle;

        for (int i = 0; i < mPieDataList.size(); i ++) {
            mPaint.setColor(mColors[i]);
            canvas.drawArc(rectF, curAngle, mPieDataList.get(i).getAngle(), true, mPaint);
            curAngle += mPieDataList.get(i).getAngle();
        }

    }

    /**
     * 初始化
     * @param pieDataList
     */
    public void init(List<PieData> pieDataList) {
        if (pieDataList == null || pieDataList.size() == 0) {
            return;
        }
        float sumValue = 0;
        for (int i = 0; i < pieDataList.size(); i ++) { // 计算数值总和
            sumValue += pieDataList.get(i).getValue();
        }

        for (int i = 0; i < pieDataList.size(); i ++) { // 计算角度
            pieDataList.get(i).setAngle(
                    pieDataList.get(i).getValue() / sumValue * 360
            );
        }
    }

    /**
     * 设置数据
     * @param pieDataList
     */
    public void setPieDataList(List<PieData> pieDataList) {
        mPieDataList = pieDataList;
        init(mPieDataList);
        invalidate(); // 刷新
    }

    /**
     * 设置角度
     * @param startAngle
     */
    public void setStartAngle(float startAngle) {
        mStartAngle = startAngle;
        invalidate(); // 刷新
    }
}
