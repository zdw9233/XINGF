package com.uyi.xinf.model.bean;

import java.io.Serializable;


/**
 * 健康资料
 * @author user
 *
 */
public class HealthInfo implements Serializable {
	private static final long serialVersionUID = 1723614937353912288L;

	private int id;//ID
	private int userId;//userId
	private String medical;		//个人既往病史
	private String infection;	//传染病史
	private String trauma;		//外伤史
	private String operation;	//手术史
	private String pregnancy;	//女性患者怀孕、分娩、流产史
	private String menstruation;//女性患者月经情况
	private String allergic;	//食物及药物过敏史
	private String blood;		//输血史
	private String familyMedical;//家族病史
	private String current;		//目前服药情况
	private String others;		//其他需要补充的情况
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getMedical() {
		return medical;
	}
	public void setMedical(String medical) {
		this.medical = medical;
	}
	public String getInfection() {
		return infection;
	}
	public void setInfection(String infection) {
		this.infection = infection;
	}
	public String getTrauma() {
		return trauma;
	}
	public void setTrauma(String trauma) {
		this.trauma = trauma;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getPregnancy() {
		return pregnancy;
	}
	public void setPregnancy(String pregnancy) {
		this.pregnancy = pregnancy;
	}
	public String getMenstruation() {
		return menstruation;
	}
	public void setMenstruation(String menstruation) {
		this.menstruation = menstruation;
	}
	public String getAllergic() {
		return allergic;
	}
	public void setAllergic(String allergic) {
		this.allergic = allergic;
	}
	public String getBlood() {
		return blood;
	}
	public void setBlood(String blood) {
		this.blood = blood;
	}
	public String getFamilyMedical() {
		return familyMedical;
	}
	public void setFamilyMedical(String familyMedical) {
		this.familyMedical = familyMedical;
	}
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	
	
	
	
	
	
	
}
