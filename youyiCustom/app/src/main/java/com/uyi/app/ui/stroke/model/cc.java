package com.uyi.app.ui.stroke.model;

import java.util.List;

/**
 * Created by ThinkPad on 2017/1/25.
 */

public class cc {
    /**
     * followUpDate : 2023-06-25
     * writeDate : 2017-01-25
     * strokeFollowUpId : null
     * doctor : null
     * doctorId : null
     * sbp : 1
     * dbp : 2
     * fbg : 3
     * pbg : 4
     * height : 5
     * weight : 5
     * bmi : 3
     * waist : 2
     * chol : 2
     * tg : 3
     * ldl : 5
     * hdl : 4
     * inr : 5
     * strokeWarningSymptoms : 突发身体一侧或双侧、上肢、下肢或面部出现无力麻木或瘫痪|突发单眼或双眼突发视物模糊，或视力下降，或视物成双
     * healthEducation : 啦啦啦
     * nonDrugTreatment : 减少烟量或戒烟
     * acceptTheDegreeOfManagement : 完全接受
     * drugTreatments : [{"drugType":"控制血压药物","drug":"阿","medicationMethods":"不规律用药","irregularMedication":"忘记|不需要药物治疗","notUse":false},{"drugType":"控制血糖药物","drug":"","medicationMethods":"不用药","irregularMedication":null,"notUse":true},{"drugType":"他汀类药物","drug":"把","medicationMethods":"规律用药","irregularMedication":"","notUse":false},{"drugType":"抗血小板聚集药物","drug":"","medicationMethods":"","irregularMedication":"","notUse":false},{"drugType":"房颤抗凝药物","drug":"","medicationMethods":"","irregularMedication":"","notUse":false}]
     * selfEvaluation : {"smoking":"明显减少","drinking":"略有减少","meatIntake":"未变化","vegetablesIntake":"","fruitIntake":"","lfdpIntake":"","physicalActivity":""}
     * vascularUltrasounds : [{"vascular":"颈动脉","result":"啊"},{"vascular":"椎动脉","result":"1"},{"vascular":"锁骨下动脉","result":"辽阔"}]
     * healthStatus : {"newDiscovery":"把","diseaseDiagnosisList":[{"disease":"颈动脉狭窄","diagnosisDate":"2017-01-12","diagnosisType":"","notHas":null},{"disease":"颈动脉斑块","diagnosisDate":null,"diagnosisType":"","notHas":null},{"disease":"脑卒中（缺血性）","diagnosisDate":"2017-01-16","diagnosisType":"心源性","notHas":null},{"disease":"脑卒中（出血性）","diagnosisDate":null,"diagnosisType":"","notHas":null}]}
     * strokePreventiveInterventionReport : {"riskFactorManagementEvaluation":"优","strokeCognitiveLevel":"中","thinkStroke":"高","planAdjustment":"啊"}
     */

    private String followUpDate;
    private String writeDate;
    private Object strokeFollowUpId;
    private Object doctor;
    private Object doctorId;
    private int sbp;
    private int dbp;
    private int fbg;
    private int pbg;
    private int height;
    private int weight;
    private int bmi;
    private int waist;
    private int chol;
    private int tg;
    private int ldl;
    private int hdl;
    private String inr;
    private String strokeWarningSymptoms;
    private String healthEducation;
    private String nonDrugTreatment;
    private String acceptTheDegreeOfManagement;
    /**
     * smoking : 明显减少
     * drinking : 略有减少
     * meatIntake : 未变化
     * vegetablesIntake :
     * fruitIntake :
     * lfdpIntake :
     * physicalActivity :
     */

    private SelfEvaluationBean selfEvaluation;
    /**
     * newDiscovery : 把
     * diseaseDiagnosisList : [{"disease":"颈动脉狭窄","diagnosisDate":"2017-01-12","diagnosisType":"","notHas":null},{"disease":"颈动脉斑块","diagnosisDate":null,"diagnosisType":"","notHas":null},{"disease":"脑卒中（缺血性）","diagnosisDate":"2017-01-16","diagnosisType":"心源性","notHas":null},{"disease":"脑卒中（出血性）","diagnosisDate":null,"diagnosisType":"","notHas":null}]
     */

    private HealthStatusBean healthStatus;
    /**
     * riskFactorManagementEvaluation : 优
     * strokeCognitiveLevel : 中
     * thinkStroke : 高
     * planAdjustment : 啊
     */

    private StrokePreventiveInterventionReportBean strokePreventiveInterventionReport;
    /**
     * drugType : 控制血压药物
     * drug : 阿
     * medicationMethods : 不规律用药
     * irregularMedication : 忘记|不需要药物治疗
     * notUse : false
     */

    private List<DrugTreatmentsBean> drugTreatments;
    /**
     * vascular : 颈动脉
     * result : 啊
     */

    private List<VascularUltrasoundsBean> vascularUltrasounds;

    public String getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(String followUpDate) {
        this.followUpDate = followUpDate;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public Object getStrokeFollowUpId() {
        return strokeFollowUpId;
    }

    public void setStrokeFollowUpId(Object strokeFollowUpId) {
        this.strokeFollowUpId = strokeFollowUpId;
    }

    public Object getDoctor() {
        return doctor;
    }

    public void setDoctor(Object doctor) {
        this.doctor = doctor;
    }

    public Object getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Object doctorId) {
        this.doctorId = doctorId;
    }

    public int getSbp() {
        return sbp;
    }

    public void setSbp(int sbp) {
        this.sbp = sbp;
    }

    public int getDbp() {
        return dbp;
    }

    public void setDbp(int dbp) {
        this.dbp = dbp;
    }

    public int getFbg() {
        return fbg;
    }

    public void setFbg(int fbg) {
        this.fbg = fbg;
    }

    public int getPbg() {
        return pbg;
    }

    public void setPbg(int pbg) {
        this.pbg = pbg;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBmi() {
        return bmi;
    }

    public void setBmi(int bmi) {
        this.bmi = bmi;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }

    public int getChol() {
        return chol;
    }

    public void setChol(int chol) {
        this.chol = chol;
    }

    public int getTg() {
        return tg;
    }

    public void setTg(int tg) {
        this.tg = tg;
    }

    public int getLdl() {
        return ldl;
    }

    public void setLdl(int ldl) {
        this.ldl = ldl;
    }

    public int getHdl() {
        return hdl;
    }

    public void setHdl(int hdl) {
        this.hdl = hdl;
    }

    public String getInr() {
        return inr;
    }

    public void setInr(String inr) {
        this.inr = inr;
    }

    public String getStrokeWarningSymptoms() {
        return strokeWarningSymptoms;
    }

    public void setStrokeWarningSymptoms(String strokeWarningSymptoms) {
        this.strokeWarningSymptoms = strokeWarningSymptoms;
    }

    public String getHealthEducation() {
        return healthEducation;
    }

    public void setHealthEducation(String healthEducation) {
        this.healthEducation = healthEducation;
    }

    public String getNonDrugTreatment() {
        return nonDrugTreatment;
    }

    public void setNonDrugTreatment(String nonDrugTreatment) {
        this.nonDrugTreatment = nonDrugTreatment;
    }

    public String getAcceptTheDegreeOfManagement() {
        return acceptTheDegreeOfManagement;
    }

    public void setAcceptTheDegreeOfManagement(String acceptTheDegreeOfManagement) {
        this.acceptTheDegreeOfManagement = acceptTheDegreeOfManagement;
    }

    public SelfEvaluationBean getSelfEvaluation() {
        return selfEvaluation;
    }

    public void setSelfEvaluation(SelfEvaluationBean selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    public HealthStatusBean getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatusBean healthStatus) {
        this.healthStatus = healthStatus;
    }

    public StrokePreventiveInterventionReportBean getStrokePreventiveInterventionReport() {
        return strokePreventiveInterventionReport;
    }

    public void setStrokePreventiveInterventionReport(StrokePreventiveInterventionReportBean strokePreventiveInterventionReport) {
        this.strokePreventiveInterventionReport = strokePreventiveInterventionReport;
    }

    public List<DrugTreatmentsBean> getDrugTreatments() {
        return drugTreatments;
    }

    public void setDrugTreatments(List<DrugTreatmentsBean> drugTreatments) {
        this.drugTreatments = drugTreatments;
    }

    public List<VascularUltrasoundsBean> getVascularUltrasounds() {
        return vascularUltrasounds;
    }

    public void setVascularUltrasounds(List<VascularUltrasoundsBean> vascularUltrasounds) {
        this.vascularUltrasounds = vascularUltrasounds;
    }

    public static class SelfEvaluationBean {
        private String smoking;
        private String drinking;
        private String meatIntake;
        private String vegetablesIntake;
        private String fruitIntake;
        private String lfdpIntake;
        private String physicalActivity;

        public String getSmoking() {
            return smoking;
        }

        public void setSmoking(String smoking) {
            this.smoking = smoking;
        }

        public String getDrinking() {
            return drinking;
        }

        public void setDrinking(String drinking) {
            this.drinking = drinking;
        }

        public String getMeatIntake() {
            return meatIntake;
        }

        public void setMeatIntake(String meatIntake) {
            this.meatIntake = meatIntake;
        }

        public String getVegetablesIntake() {
            return vegetablesIntake;
        }

        public void setVegetablesIntake(String vegetablesIntake) {
            this.vegetablesIntake = vegetablesIntake;
        }

        public String getFruitIntake() {
            return fruitIntake;
        }

        public void setFruitIntake(String fruitIntake) {
            this.fruitIntake = fruitIntake;
        }

        public String getLfdpIntake() {
            return lfdpIntake;
        }

        public void setLfdpIntake(String lfdpIntake) {
            this.lfdpIntake = lfdpIntake;
        }

        public String getPhysicalActivity() {
            return physicalActivity;
        }

        public void setPhysicalActivity(String physicalActivity) {
            this.physicalActivity = physicalActivity;
        }
    }

    public static class HealthStatusBean {
        private String newDiscovery;
        /**
         * disease : 颈动脉狭窄
         * diagnosisDate : 2017-01-12
         * diagnosisType :
         * notHas : null
         */

        private List<DiseaseDiagnosisListBean> diseaseDiagnosisList;

        public String getNewDiscovery() {
            return newDiscovery;
        }

        public void setNewDiscovery(String newDiscovery) {
            this.newDiscovery = newDiscovery;
        }

        public List<DiseaseDiagnosisListBean> getDiseaseDiagnosisList() {
            return diseaseDiagnosisList;
        }

        public void setDiseaseDiagnosisList(List<DiseaseDiagnosisListBean> diseaseDiagnosisList) {
            this.diseaseDiagnosisList = diseaseDiagnosisList;
        }

        public static class DiseaseDiagnosisListBean {
            private String disease;
            private String diagnosisDate;
            private String diagnosisType;
            private Object notHas;

            public String getDisease() {
                return disease;
            }

            public void setDisease(String disease) {
                this.disease = disease;
            }

            public String getDiagnosisDate() {
                return diagnosisDate;
            }

            public void setDiagnosisDate(String diagnosisDate) {
                this.diagnosisDate = diagnosisDate;
            }

            public String getDiagnosisType() {
                return diagnosisType;
            }

            public void setDiagnosisType(String diagnosisType) {
                this.diagnosisType = diagnosisType;
            }

            public Object getNotHas() {
                return notHas;
            }

            public void setNotHas(Object notHas) {
                this.notHas = notHas;
            }
        }
    }

    public static class StrokePreventiveInterventionReportBean {
        private String riskFactorManagementEvaluation;
        private String strokeCognitiveLevel;
        private String thinkStroke;
        private String planAdjustment;

        public String getRiskFactorManagementEvaluation() {
            return riskFactorManagementEvaluation;
        }

        public void setRiskFactorManagementEvaluation(String riskFactorManagementEvaluation) {
            this.riskFactorManagementEvaluation = riskFactorManagementEvaluation;
        }

        public String getStrokeCognitiveLevel() {
            return strokeCognitiveLevel;
        }

        public void setStrokeCognitiveLevel(String strokeCognitiveLevel) {
            this.strokeCognitiveLevel = strokeCognitiveLevel;
        }

        public String getThinkStroke() {
            return thinkStroke;
        }

        public void setThinkStroke(String thinkStroke) {
            this.thinkStroke = thinkStroke;
        }

        public String getPlanAdjustment() {
            return planAdjustment;
        }

        public void setPlanAdjustment(String planAdjustment) {
            this.planAdjustment = planAdjustment;
        }
    }

    public static class DrugTreatmentsBean {
        private String drugType;
        private String drug;
        private String medicationMethods;
        private String irregularMedication;
        private boolean notUse;

        public String getDrugType() {
            return drugType;
        }

        public void setDrugType(String drugType) {
            this.drugType = drugType;
        }

        public String getDrug() {
            return drug;
        }

        public void setDrug(String drug) {
            this.drug = drug;
        }

        public String getMedicationMethods() {
            return medicationMethods;
        }

        public void setMedicationMethods(String medicationMethods) {
            this.medicationMethods = medicationMethods;
        }

        public String getIrregularMedication() {
            return irregularMedication;
        }

        public void setIrregularMedication(String irregularMedication) {
            this.irregularMedication = irregularMedication;
        }

        public boolean isNotUse() {
            return notUse;
        }

        public void setNotUse(boolean notUse) {
            this.notUse = notUse;
        }
    }

    public static class VascularUltrasoundsBean {
        private String vascular;
        private String result;

        public String getVascular() {
            return vascular;
        }

        public void setVascular(String vascular) {
            this.vascular = vascular;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
