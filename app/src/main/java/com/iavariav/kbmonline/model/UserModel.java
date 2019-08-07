package com.iavariav.kbmonline.model;

import com.google.gson.annotations.SerializedName;

public class UserModel{

	@SerializedName("NAMA_USER")
	private String nAMAUSER;

	@SerializedName("PASSWORD_USER")
	private String pASSWORDUSER;

	@SerializedName("RULE_USER")
	private String rULEUSER;

	@SerializedName("NIK_USER")
	private String nIKUSER;

	@SerializedName("ID_USER")
	private String iDUSER;

	@SerializedName("REG_ID")
	private String rEGID;

	public void setNAMAUSER(String nAMAUSER){
		this.nAMAUSER = nAMAUSER;
	}

	public String getNAMAUSER(){
		return nAMAUSER;
	}

	public void setPASSWORDUSER(String pASSWORDUSER){
		this.pASSWORDUSER = pASSWORDUSER;
	}

	public String getPASSWORDUSER(){
		return pASSWORDUSER;
	}

	public void setRULEUSER(String rULEUSER){
		this.rULEUSER = rULEUSER;
	}

	public String getRULEUSER(){
		return rULEUSER;
	}

	public void setNIKUSER(String nIKUSER){
		this.nIKUSER = nIKUSER;
	}

	public String getNIKUSER(){
		return nIKUSER;
	}

	public void setIDUSER(String iDUSER){
		this.iDUSER = iDUSER;
	}

	public String getIDUSER(){
		return iDUSER;
	}

	public void setREGID(String rEGID){
		this.rEGID = rEGID;
	}

	public String getREGID(){
		return rEGID;
	}
}