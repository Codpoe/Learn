package com.app.learn.model;

/**
 * Created by Codpoe on 2016/5/5.
 */
public class ChinaCalendar {

    /**
     * reason : successed
     * result : {"id":"1657","yangli":"2014-09-11","yinli":"甲午(马)年八月十八","wuxing":"井泉水 建执位","chongsha":"冲兔(己卯)煞东","baiji":"乙不栽植千株不长 酉不宴客醉坐颠狂","jishen":"官日 六仪 益後 月德合 除神 玉堂 鸣犬","yi":"祭祀 出行 扫舍 馀事勿取","xiongshen":"月建 小时 土府 月刑 厌对 招摇 五离","ji":"诸事不宜"}
     * error_code : 0
     */

    private String reason;
    /**
     * id : 1657
     * yangli : 2014-09-11
     * yinli : 甲午(马)年八月十八
     * wuxing : 井泉水 建执位
     * chongsha : 冲兔(己卯)煞东
     * baiji : 乙不栽植千株不长 酉不宴客醉坐颠狂
     * jishen : 官日 六仪 益後 月德合 除神 玉堂 鸣犬
     * yi : 祭祀 出行 扫舍 馀事勿取
     * xiongshen : 月建 小时 土府 月刑 厌对 招摇 五离
     * ji : 诸事不宜
     */

    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        private String yi;
        private String ji;

        public String getYi() {
            return yi;
        }

        public void setYi(String yi) {
            this.yi = yi;
        }

        public String getJi() {
            return ji;
        }

        public void setJi(String ji) {
            this.ji = ji;
        }
    }
}
