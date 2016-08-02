package com.uyi.app.ui.consult.model;

import java.util.List;

/**
 * Created by Leeii on 2016/7/14.
 */
public class Health {

    /**
     * height : 190
     * weight : 80
     * healthCondition : 健康状况：(过去健康状况，如体质、抵抗力、劳动力等)
     * chronicDiseaseType : 3
     * healthInfo : {"medical":"疾病史：(重要的疾病史、传染病史、外伤史、手术史、中毒史、过敏史、输血史 及其他病史)","infection":"传染病史：(主要的传染病史，如无传染病史，亦须将与目前疾病有关而确未发生 的传染病名称记入并说明)","trauma":"外伤史：(受伤情况、受伤情况、受伤部位等)","operation":"手术史：(手术项目、手术时间、恢复情况等)","pregnancy":null,"menstruation":null,"allergic":"中毒史：","blood":"输血史：(输血时间，输血频率等)","familyMedical":"家族病史(家族中有无慢性疾病、肿瘤、精神疾病、罕少见病患者)","current":null,"others":"其他需要补充的情况","vaccinationHistory":"预防接种史：(应记录种类和最近一次接种日期)","retrospection":"系统回顾：(应记录既往各系统中重要的阳性症状或有鉴别意义的阴性表现)","abnormalEventJsons":{"id":1,"time":"2016-01-01","name":"脑血拴","eventType":2,"description":"头疼，脑热 DealPul"},"eventOccurrenceTime":null,"description":"头疼，脑热 DealPul","historyOfAllergy":"食物及药物过敏史：(应记录致敏药物、发生时间、症状及就诊情况，如无药物过敏史亦须说明)","drugAddiction":"成瘾的药物：(应记录成瘾的药物名称和使用情况)","medicineJsons":null,"medicationUsingSituations":[{"startTime":"2016-07-01","endTime":"2016-07-03","medicineId":1,"medicineName":"咳特灵","usingFrequency":"2","frequencyUnit":"小时","singleDose":"10","medicineUnit":"g"},{"startTime":"2016-07-03","endTime":"2016-07-06","medicineId":2,"medicineName":"头孢","usingFrequency":"1","frequencyUnit":"天","singleDose":"33","medicineUnit":"ml"}],"externalSituations":[{"treatmentTime":"2016-07-01","content":"系统外就医情况：1"},{"treatmentTime":"2016-07-06","content":"系统外就医情况：2"}]}
     */

    public String height;
    public String weight;
    public String healthCondition;
    public int chronicDiseaseType;
    /**
     * medical : 疾病史：(重要的疾病史、传染病史、外伤史、手术史、中毒史、过敏史、输血史 及其他病史)
     * infection : 传染病史：(主要的传染病史，如无传染病史，亦须将与目前疾病有关而确未发生 的传染病名称记入并说明)
     * trauma : 外伤史：(受伤情况、受伤情况、受伤部位等)
     * operation : 手术史：(手术项目、手术时间、恢复情况等)
     * pregnancy : null
     * menstruation : null
     * allergic : 中毒史：
     * blood : 输血史：(输血时间，输血频率等)
     * familyMedical : 家族病史(家族中有无慢性疾病、肿瘤、精神疾病、罕少见病患者)
     * current : null
     * others : 其他需要补充的情况
     * vaccinationHistory : 预防接种史：(应记录种类和最近一次接种日期)
     * retrospection : 系统回顾：(应记录既往各系统中重要的阳性症状或有鉴别意义的阴性表现)
     * abnormalEventJsons : {"id":1,"time":"2016-01-01","name":"脑血拴","eventType":2,"description":"头疼，脑热 DealPul"}
     * eventOccurrenceTime : null
     * description : 头疼，脑热 DealPul
     * historyOfAllergy : 食物及药物过敏史：(应记录致敏药物、发生时间、症状及就诊情况，如无药物过敏史亦须说明)
     * drugAddiction : 成瘾的药物：(应记录成瘾的药物名称和使用情况)
     * medicineJsons : null
     * medicationUsingSituations : [{"startTime":"2016-07-01","endTime":"2016-07-03","medicineId":1,"medicineName":"咳特灵","usingFrequency":"2","frequencyUnit":"小时","singleDose":"10","medicineUnit":"g"},{"startTime":"2016-07-03","endTime":"2016-07-06","medicineId":2,"medicineName":"头孢","usingFrequency":"1","frequencyUnit":"天","singleDose":"33","medicineUnit":"ml"}]
     * externalSituations : [{"treatmentTime":"2016-07-01","content":"系统外就医情况：1"},{"treatmentTime":"2016-07-06","content":"系统外就医情况：2"}]
     */

    public HealthInfoBean healthInfo;

    public static class HealthInfoBean {
        public String medical;
        public String infection;
        public String trauma;
        public String operation;
        public String pregnancy;
        public String menstruation;
        public String allergic;
        public String blood;
        public String familyMedical;
        public String current;
        public String others;
        public String vaccinationHistory;
        public String retrospection;
        /**
         * id : 1
         * time : 2016-01-01
         * name : 脑血拴
         * eventType : 2
         * description : 头疼，脑热 DealPul
         */

        public AbnormalEventJsonsBean abnormalEventJsons;
        public String eventOccurrenceTime;
        public String description;
        public String historyOfAllergy;
        public String drugAddiction;
        public String medicineJsons;
        /**
         * startTime : 2016-07-01
         * endTime : 2016-07-03
         * medicineId : 1
         * medicineName : 咳特灵
         * usingFrequency : 2
         * frequencyUnit : 小时
         * singleDose : 10
         * medicineUnit : g
         */

        public List<MedicationUsingSituationsBean> medicationUsingSituations;
        /**
         * treatmentTime : 2016-07-01
         * content : 系统外就医情况：1
         */

        public List<ExternalSituationsBean> externalSituations;


        public static class AbnormalEventJsonsBean {
            public int id;
            public String time;
            public String name;
            public int eventType;
            public String description;
            public int eventId;
        }

        public static class MedicationUsingSituationsBean {
            public int id;
            public String startTime;
            public String endTime;
            public int medicineId;
            public String medicineName;
            public String usingFrequency;
            public String frequencyUnit;
            public String singleDose;
            public String medicineUnit;
        }

        public static class ExternalSituationsBean {
            public int id;
            public String treatmentTime;
            public String content;
        }
    }
}
