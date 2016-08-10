package com.app.learn.model;

/**
 * Created by Codpoe on 2016/8/9.
 */
public class PieData {

    private String name; // 名字
    private float value; // 数值
    private float percentage; // 百分比
    private int color = 0; // 颜色
    private float angle = 0; // 角度

    public PieData(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }


}
