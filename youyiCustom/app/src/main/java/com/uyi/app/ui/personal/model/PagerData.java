package com.uyi.app.ui.personal.model;

public class PagerData {

        /**
         * bloodPressure_pic : http://121.42.142.228:8080/pics/business/images/business/2016/07/bfc30058-0edb-4e09-92ee-0dab79b54ee8.jpg
         * bloodSugar_pic : http://121.42.142.228:8080/pics/business/images/business/2016/07/aaf48243-ca88-4023-9e2d-271db75474ae.jpg
         * comment1 : 您最近30天共测量血压  4次，收缩压和舒张压均控制在比较理想的范围，根据您的年龄段（此处应加入不同年龄段血压控制标准）和我国居民高血压治疗指南的建议，请您继续保持目前的饮食及运动习惯，按时服药，每日监测血压并及时将数据上传到“优医”中。（点击蓝色字体可全文阅读相关内容）。
         */

        private String bloodPressure_pic;
    private String bloodSugar_pic;

    @Override
    public String toString() {
        return "PagerData{" +
                "bloodPressure_pic='" + bloodPressure_pic + '\'' +
                ", bloodSugar_pic='" + bloodSugar_pic + '\'' +
                ", comment1='" + comment1 + '\'' +
                '}';
    }

    public String getBloodPressure_pic() {
        return bloodPressure_pic;
    }

    public void setBloodPressure_pic(String bloodPressure_pic) {
        this.bloodPressure_pic = bloodPressure_pic;
    }

    public String getBloodSugar_pic() {
        return bloodSugar_pic;
    }

    public void setBloodSugar_pic(String bloodSugar_pic) {
        this.bloodSugar_pic = bloodSugar_pic;
    }

    public String getComment1() {
        return comment1;
    }

    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    public String getComment2() {
        return comment2;
    }

    public void setComment2(String comment2) {
        this.comment2 = comment2;
    }

    private String comment1;
    private String comment2;
    }