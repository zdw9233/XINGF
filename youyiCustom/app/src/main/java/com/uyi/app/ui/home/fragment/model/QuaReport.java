package com.uyi.app.ui.home.fragment.model;

import java.util.List;

/**
 * Created by ThinkPad on 2017/3/16.
 */

public class QuaReport {

    /**
     * customerId : 1627
     * realName : 付文泉
     * idNumber : 513723198902113701
     * height : 153
     * weight : 47
     * bmi : 20.08
     * bloodPressure_pic : http://localhost:8080/pics/business/images/business/2017/03/524b6c03-4956-4273-9ba7-b0935a034ff4.jpg
     * bloodSugar_pic : http://localhost:8080/pics/business/images/business/2017/03/2792f2c6-6da4-4474-b75a-2f06d6001ee7.jpg
     * allNumSugar : 5
     * allNumPressure : 8
     * allNum : 8
     * chronicDiseaseType : 高血压+糖尿病
     * routines : [{"id":56561,"checkTime":"2017-03-01","spo":"67","fatPercentage":"1.0","basalMetabolism":"1.0","waterContent":"1.0","waist":"1.0","hipline":"0.7","whr":"1.0"},{"id":56562,"checkTime":"2017-03-02","spo":"5","fatPercentage":"1.0","basalMetabolism":"1.0","waterContent":"1.0","waist":"0.6","hipline":"\u2014\u2014","whr":"0.5"}]
     * bloodFats : [{"id":56561,"checkTime":"2017-03-01","urineAcid":"33.0","bloodFatChol":"55.0","bloodFatTg":"66.0","bloodFatHdl":"88.0","bloodFatLdl":"77.0"},{"id":56562,"checkTime":"2017-03-02","urineAcid":"7.0","bloodFatChol":"4.0","bloodFatTg":"4.0","bloodFatHdl":"5.0","bloodFatLdl":"6.0"}]
     * bloodPressures : [{"id":56567,"uploadTime":"2017-03-17 16:04:40","uploadItems":"血压/血糖/脉搏","isWarning":true,"morningSystolicPressure":222,"morningSystolicPressureWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 130）异常.","mspState":1,"morningDiastolicPressure":222,"morningDiastolicPressureWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 80）异常.","mdpState":1,"pulseRate":222,"pulseRateWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 90）异常.","prState":1,"checkTime":"2016-12-24 21:00:00","warning":true},{"id":56566,"uploadTime":"2017-03-17 15:57:19","uploadItems":"血压/血糖/脉搏","isWarning":true,"morningSystolicPressure":90,"morningSystolicPressureWarning":"广州市老年慢性疾病管理团队提示您：该项指标（< 100）异常.","mspState":2,"morningDiastolicPressure":90,"morningDiastolicPressureWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 80）异常.","mdpState":1,"pulseRate":90,"prState":0,"checkTime":"2017-02-10 02:00:00","warning":true},{"id":56565,"uploadTime":"2017-03-17 15:56:28","uploadItems":"血压/血糖/脉搏","isWarning":true,"morningSystolicPressure":123,"mspState":0,"morningDiastolicPressure":123,"morningDiastolicPressureWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 80）异常.","mdpState":1,"pulseRate":123,"pulseRateWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 90）异常.","prState":1,"checkTime":"2016-12-24 02:00:00","warning":true},{"id":56562,"uploadTime":"2017-03-13 09:52:32","uploadItems":"血压/心率/血糖/尿酸/血脂/脉搏/血氧饱和度","isWarning":false,"morningSystolicPressure":80,"morningSystolicPressureWarning":"广州市老年慢性疾病管理团队提示您：该项指标（< 100）异常.","mspState":2,"morningDiastolicPressure":120,"morningDiastolicPressureWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 80）异常.","mdpState":1,"pulseRate":33,"pulseRateWarning":"广州市老年慢性疾病管理团队提示您：该项指标（< 50）异常.","prState":2,"checkTime":"2017-03-02 02:00:00","warning":false},{"id":56561,"uploadTime":"2017-03-13 09:51:04","uploadItems":"血压/心率/血糖/尿酸/血脂/脉搏/血氧饱和度","isWarning":true,"morningSystolicPressure":90,"morningSystolicPressureWarning":"广州市老年慢性疾病管理团队提示您：该项指标（< 100）异常.","mspState":2,"morningDiastolicPressure":120,"morningDiastolicPressureWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 80）异常.","mdpState":1,"pulseRate":60,"prState":0,"checkTime":"2017-03-01 01:00:00","warning":true},{"id":55185,"uploadTime":"2017-02-21 19:08:21","uploadItems":"血压/心电图/检查报告.","isWarning":false,"morningSystolicPressure":111,"mspState":0,"checkTime":"2017-02-21 19:08:21","warning":false},{"id":55184,"uploadTime":"2017-02-21 19:01:15","uploadItems":"血压/心电图","isWarning":false,"morningSystolicPressure":122,"mspState":0,"checkTime":"2017-02-21 19:01:15","warning":false},{"id":55183,"uploadTime":"2017-02-21 19:00:27","uploadItems":"血压/心电图","isWarning":false,"morningSystolicPressure":122,"mspState":0,"checkTime":"2017-02-21 19:00:27","warning":false}]
     * bloodSugars : [{"id":56567,"uploadTime":"2017-03-17 16:04:40","uploadItems":"血压/血糖/脉搏","isWarning":true,"fastBloodSugar":222,"fastBloodSugarWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 11.1）异常.","fbsState":1,"postPrandilaSugar":222,"postPrandilaSugarWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 11.1）异常.","ppsState":1,"randomBloodSugar":222,"randomBloodSugarWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 11.1）异常.","rbsState":1,"checkTime":"2016-12-24 21:00:00","warning":true},{"id":56566,"uploadTime":"2017-03-17 15:57:19","uploadItems":"血压/血糖/脉搏","isWarning":true,"fastBloodSugar":90,"fastBloodSugarWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 11.1）异常.","fbsState":1,"postPrandilaSugar":90,"postPrandilaSugarWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 11.1）异常.","ppsState":1,"randomBloodSugar":90,"randomBloodSugarWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 11.1）异常.","rbsState":1,"checkTime":"2017-02-10 02:00:00","warning":true},{"id":56565,"uploadTime":"2017-03-17 15:56:28","uploadItems":"血压/血糖/脉搏","isWarning":true,"fastBloodSugar":123,"fastBloodSugarWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 11.1）异常.","fbsState":1,"postPrandilaSugar":123,"postPrandilaSugarWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 11.1）异常.","ppsState":1,"randomBloodSugar":123,"randomBloodSugarWarning":"广州市老年慢性疾病管理团队提示您：该项指标（> 11.1）异常.","rbsState":1,"checkTime":"2016-12-24 02:00:00","warning":true},{"id":56562,"uploadTime":"2017-03-13 09:52:32","uploadItems":"血压/心率/血糖/尿酸/血脂/脉搏/血氧饱和度","isWarning":false,"fastBloodSugar":5,"fastBloodSugarWarning":"广州市老年慢性疾病管理团队提示您：该项指标（< 7.0）异常.","fbsState":2,"postPrandilaSugar":6,"postPrandilaSugarWarning":"广州市老年慢性疾病管理团队提示您：该项指标（< 7.0）异常.","ppsState":2,"randomBloodSugar":7,"rbsState":0,"checkTime":"2017-03-02 02:00:00","warning":false},{"id":56561,"uploadTime":"2017-03-13 09:51:04","uploadItems":"血压/心率/血糖/尿酸/血脂/脉搏/血氧饱和度","isWarning":true,"fastBloodSugar":5,"fastBloodSugarWarning":"广州市老年慢性疾病管理团队提示您：该项指标（< 7.0）异常.","fbsState":2,"postPrandilaSugar":6,"postPrandilaSugarWarning":"广州市老年慢性疾病管理团队提示您：该项指标（< 7.0）异常.","ppsState":2,"randomBloodSugar":7,"rbsState":0,"checkTime":"2017-03-01 01:00:00","warning":true}]
     * pressureReportText : 您从2016-12-09至2017-03-09内共测量了8次血压，其中收缩压最高为222，最低80；舒张压最高为222，最低90，其中5次超出了正常血压范围，异常率占62.5%， 提示您的血压控制不理想，根据您的年龄阶段、疾病状况和我国居民高血压治疗指南的建议，您的血压的理想控制范围是：收缩压100至130mmHg，舒张压60至80mmHg，如果您已经按照医嘱要求服用降压药物，根据您这一阶段的监测情况，需要请求医生的专业帮助，您可以通过“优医”在线咨询与您的主诊医生取得联系，必要时需要调整药物治疗方案。
     * sugarReportText : 您从2016-12-09至2017-03-09内共测量了5次血糖，其中空腹血糖最高为222，最低5；餐后血糖最高为222.0mmol/L，最低为6.0mmol/L其中5次超出了正常血糖范围，异常率占100% ，提示您的血糖控制不理想。如果您今天已经按照医嘱要求服用降糖药物或注射了胰岛素，并且您的血糖监测符合要求，根据您的年龄阶段、疾病状况和中国糖尿病防治指南的建议，您的空腹血糖的理想控制范围是7.0mmol/L至11.1mmol/L，餐后血糖的理想控制范围是7.0mmol/L至11.1mmol/L，根据您这一阶段的监测情况，需要请求医生的专业帮助，您可以通过“优医”在线咨询与您的主诊医生取得联系，必要时需要调整药物治疗方案。
     * type : 2
     */

    private int customerId;
    private String realName;
    private String idNumber;
    private String height;
    private String weight;
    private String bmi;
    private String bloodPressure_pic;
    private String bloodSugar_pic;
    private int allNumSugar;
    private int allNumPressure;
    private int allNum;
    private String chronicDiseaseType;
    private String pressureReportText;
    private String sugarReportText;
    private int type;
    private List<RoutinesBean> routines;
    private List<BloodFatsBean> bloodFats;
    private List<BloodPressuresBean> bloodPressures;
    private List<BloodSugarsBean> bloodSugars;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
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

    public int getAllNumSugar() {
        return allNumSugar;
    }

    public void setAllNumSugar(int allNumSugar) {
        this.allNumSugar = allNumSugar;
    }

    public int getAllNumPressure() {
        return allNumPressure;
    }

    public void setAllNumPressure(int allNumPressure) {
        this.allNumPressure = allNumPressure;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public String getChronicDiseaseType() {
        return chronicDiseaseType;
    }

    public void setChronicDiseaseType(String chronicDiseaseType) {
        this.chronicDiseaseType = chronicDiseaseType;
    }

    public String getPressureReportText() {
        return pressureReportText;
    }

    public void setPressureReportText(String pressureReportText) {
        this.pressureReportText = pressureReportText;
    }

    public String getSugarReportText() {
        return sugarReportText;
    }

    public void setSugarReportText(String sugarReportText) {
        this.sugarReportText = sugarReportText;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<RoutinesBean> getRoutines() {
        return routines;
    }

    public void setRoutines(List<RoutinesBean> routines) {
        this.routines = routines;
    }

    public List<BloodFatsBean> getBloodFats() {
        return bloodFats;
    }

    public void setBloodFats(List<BloodFatsBean> bloodFats) {
        this.bloodFats = bloodFats;
    }

    public List<BloodPressuresBean> getBloodPressures() {
        return bloodPressures;
    }

    public void setBloodPressures(List<BloodPressuresBean> bloodPressures) {
        this.bloodPressures = bloodPressures;
    }

    public List<BloodSugarsBean> getBloodSugars() {
        return bloodSugars;
    }

    public void setBloodSugars(List<BloodSugarsBean> bloodSugars) {
        this.bloodSugars = bloodSugars;
    }

    public static class RoutinesBean {
        /**
         * id : 56561
         * checkTime : 2017-03-01
         * spo : 67
         * fatPercentage : 1.0
         * basalMetabolism : 1.0
         * waterContent : 1.0
         * waist : 1.0
         * hipline : 0.7
         * whr : 1.0
         */

        private int id;
        private String checkTime;
        private String spo;
        private String fatPercentage;
        private String basalMetabolism;
        private String waterContent;
        private String waist;
        private String hipline;
        private String whr;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }

        public String getSpo() {
            return spo;
        }

        public void setSpo(String spo) {
            this.spo = spo;
        }

        public String getFatPercentage() {
            return fatPercentage;
        }

        public void setFatPercentage(String fatPercentage) {
            this.fatPercentage = fatPercentage;
        }

        public String getBasalMetabolism() {
            return basalMetabolism;
        }

        public void setBasalMetabolism(String basalMetabolism) {
            this.basalMetabolism = basalMetabolism;
        }

        public String getWaterContent() {
            return waterContent;
        }

        public void setWaterContent(String waterContent) {
            this.waterContent = waterContent;
        }

        public String getWaist() {
            return waist;
        }

        public void setWaist(String waist) {
            this.waist = waist;
        }

        public String getHipline() {
            return hipline;
        }

        public void setHipline(String hipline) {
            this.hipline = hipline;
        }

        public String getWhr() {
            return whr;
        }

        public void setWhr(String whr) {
            this.whr = whr;
        }
    }

    public static class BloodFatsBean {
        /**
         * id : 56561
         * checkTime : 2017-03-01
         * urineAcid : 33.0
         * bloodFatChol : 55.0
         * bloodFatTg : 66.0
         * bloodFatHdl : 88.0
         * bloodFatLdl : 77.0
         */

        private int id;
        private String checkTime;
        private String urineAcid;
        private String bloodFatChol;
        private String bloodFatTg;
        private String bloodFatHdl;
        private String bloodFatLdl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }

        public String getUrineAcid() {
            return urineAcid;
        }

        public void setUrineAcid(String urineAcid) {
            this.urineAcid = urineAcid;
        }

        public String getBloodFatChol() {
            return bloodFatChol;
        }

        public void setBloodFatChol(String bloodFatChol) {
            this.bloodFatChol = bloodFatChol;
        }

        public String getBloodFatTg() {
            return bloodFatTg;
        }

        public void setBloodFatTg(String bloodFatTg) {
            this.bloodFatTg = bloodFatTg;
        }

        public String getBloodFatHdl() {
            return bloodFatHdl;
        }

        public void setBloodFatHdl(String bloodFatHdl) {
            this.bloodFatHdl = bloodFatHdl;
        }

        public String getBloodFatLdl() {
            return bloodFatLdl;
        }

        public void setBloodFatLdl(String bloodFatLdl) {
            this.bloodFatLdl = bloodFatLdl;
        }
    }

    public static class BloodPressuresBean {
        /**
         * id : 56567
         * uploadTime : 2017-03-17 16:04:40
         * uploadItems : 血压/血糖/脉搏
         * isWarning : true
         * morningSystolicPressure : 222
         * morningSystolicPressureWarning : 广州市老年慢性疾病管理团队提示您：该项指标（> 130）异常.
         * mspState : 1
         * morningDiastolicPressure : 222
         * morningDiastolicPressureWarning : 广州市老年慢性疾病管理团队提示您：该项指标（> 80）异常.
         * mdpState : 1
         * pulseRate : 222
         * pulseRateWarning : 广州市老年慢性疾病管理团队提示您：该项指标（> 90）异常.
         * prState : 1
         * checkTime : 2016-12-24 21:00:00
         * warning : true
         */

        private int id;
        private String uploadTime;
        private String uploadItems;
        private boolean isWarning;
        private int morningSystolicPressure;
        private String morningSystolicPressureWarning;
        private int mspState;
        private int morningDiastolicPressure;
        private String morningDiastolicPressureWarning;
        private int mdpState;
        private int pulseRate;
        private String pulseRateWarning;
        private int prState;
        private String checkTime;
        private boolean warning;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(String uploadTime) {
            this.uploadTime = uploadTime;
        }

        public String getUploadItems() {
            return uploadItems;
        }

        public void setUploadItems(String uploadItems) {
            this.uploadItems = uploadItems;
        }

        public boolean isIsWarning() {
            return isWarning;
        }

        public void setIsWarning(boolean isWarning) {
            this.isWarning = isWarning;
        }

        public int getMorningSystolicPressure() {
            return morningSystolicPressure;
        }

        public void setMorningSystolicPressure(int morningSystolicPressure) {
            this.morningSystolicPressure = morningSystolicPressure;
        }

        public String getMorningSystolicPressureWarning() {
            return morningSystolicPressureWarning;
        }

        public void setMorningSystolicPressureWarning(String morningSystolicPressureWarning) {
            this.morningSystolicPressureWarning = morningSystolicPressureWarning;
        }

        public int getMspState() {
            return mspState;
        }

        public void setMspState(int mspState) {
            this.mspState = mspState;
        }

        public int getMorningDiastolicPressure() {
            return morningDiastolicPressure;
        }

        public void setMorningDiastolicPressure(int morningDiastolicPressure) {
            this.morningDiastolicPressure = morningDiastolicPressure;
        }

        public String getMorningDiastolicPressureWarning() {
            return morningDiastolicPressureWarning;
        }

        public void setMorningDiastolicPressureWarning(String morningDiastolicPressureWarning) {
            this.morningDiastolicPressureWarning = morningDiastolicPressureWarning;
        }

        public int getMdpState() {
            return mdpState;
        }

        public void setMdpState(int mdpState) {
            this.mdpState = mdpState;
        }

        public int getPulseRate() {
            return pulseRate;
        }

        public void setPulseRate(int pulseRate) {
            this.pulseRate = pulseRate;
        }

        public String getPulseRateWarning() {
            return pulseRateWarning;
        }

        public void setPulseRateWarning(String pulseRateWarning) {
            this.pulseRateWarning = pulseRateWarning;
        }

        public int getPrState() {
            return prState;
        }

        public void setPrState(int prState) {
            this.prState = prState;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }

        public boolean isWarning() {
            return warning;
        }

        public void setWarning(boolean warning) {
            this.warning = warning;
        }
    }

    public static class BloodSugarsBean {
        /**
         * id : 56567
         * uploadTime : 2017-03-17 16:04:40
         * uploadItems : 血压/血糖/脉搏
         * isWarning : true
         * fastBloodSugar : 222.0
         * fastBloodSugarWarning : 广州市老年慢性疾病管理团队提示您：该项指标（> 11.1）异常.
         * fbsState : 1
         * postPrandilaSugar : 222.0
         * postPrandilaSugarWarning : 广州市老年慢性疾病管理团队提示您：该项指标（> 11.1）异常.
         * ppsState : 1
         * randomBloodSugar : 222.0
         * randomBloodSugarWarning : 广州市老年慢性疾病管理团队提示您：该项指标（> 11.1）异常.
         * rbsState : 1
         * checkTime : 2016-12-24 21:00:00
         * warning : true
         */

        private int id;
        private String uploadTime;
        private String uploadItems;
        private boolean isWarning;
        private double fastBloodSugar;
        private String fastBloodSugarWarning;
        private int fbsState;
        private double postPrandilaSugar;
        private String postPrandilaSugarWarning;
        private int ppsState;
        private double randomBloodSugar;
        private String randomBloodSugarWarning;
        private int rbsState;
        private String checkTime;
        private boolean warning;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(String uploadTime) {
            this.uploadTime = uploadTime;
        }

        public String getUploadItems() {
            return uploadItems;
        }

        public void setUploadItems(String uploadItems) {
            this.uploadItems = uploadItems;
        }

        public boolean isIsWarning() {
            return isWarning;
        }

        public void setIsWarning(boolean isWarning) {
            this.isWarning = isWarning;
        }

        public double getFastBloodSugar() {
            return fastBloodSugar;
        }

        public void setFastBloodSugar(double fastBloodSugar) {
            this.fastBloodSugar = fastBloodSugar;
        }

        public String getFastBloodSugarWarning() {
            return fastBloodSugarWarning;
        }

        public void setFastBloodSugarWarning(String fastBloodSugarWarning) {
            this.fastBloodSugarWarning = fastBloodSugarWarning;
        }

        public int getFbsState() {
            return fbsState;
        }

        public void setFbsState(int fbsState) {
            this.fbsState = fbsState;
        }

        public double getPostPrandilaSugar() {
            return postPrandilaSugar;
        }

        public void setPostPrandilaSugar(double postPrandilaSugar) {
            this.postPrandilaSugar = postPrandilaSugar;
        }

        public String getPostPrandilaSugarWarning() {
            return postPrandilaSugarWarning;
        }

        public void setPostPrandilaSugarWarning(String postPrandilaSugarWarning) {
            this.postPrandilaSugarWarning = postPrandilaSugarWarning;
        }

        public int getPpsState() {
            return ppsState;
        }

        public void setPpsState(int ppsState) {
            this.ppsState = ppsState;
        }

        public double getRandomBloodSugar() {
            return randomBloodSugar;
        }

        public void setRandomBloodSugar(double randomBloodSugar) {
            this.randomBloodSugar = randomBloodSugar;
        }

        public String getRandomBloodSugarWarning() {
            return randomBloodSugarWarning;
        }

        public void setRandomBloodSugarWarning(String randomBloodSugarWarning) {
            this.randomBloodSugarWarning = randomBloodSugarWarning;
        }

        public int getRbsState() {
            return rbsState;
        }

        public void setRbsState(int rbsState) {
            this.rbsState = rbsState;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }

        public boolean isWarning() {
            return warning;
        }

        public void setWarning(boolean warning) {
            this.warning = warning;
        }
    }
}
