package com.iavariav.kbmonline.model;

import com.google.gson.annotations.SerializedName;

public class MobilModel{

	@SerializedName("TYPE_MOBIL")
	private String tYPEMOBIL;

	@SerializedName("STATUS_MOBIL")
	private String sTATUSMOBIL;

	@SerializedName("ID_MOBIL")
	private String iDMOBIL;

	@SerializedName("DESKRIPSI_MOBIL")
	private String dESKRIPSIMOBIL;

	@SerializedName("PLAT_MOBIL")
	private String pLATMOBIL;

	@SerializedName("NAMA_SUPIR")
	private String nAMASUPIR;

	@SerializedName("JENIS_MOBIL")
	private String jENISMOBIL;

	public void setTYPEMOBIL(String tYPEMOBIL){
		this.tYPEMOBIL = tYPEMOBIL;
	}

	public String getTYPEMOBIL(){
		return tYPEMOBIL;
	}

	public void setSTATUSMOBIL(String sTATUSMOBIL){
		this.sTATUSMOBIL = sTATUSMOBIL;
	}

	public String getSTATUSMOBIL(){
		return sTATUSMOBIL;
	}

	public void setIDMOBIL(String iDMOBIL){
		this.iDMOBIL = iDMOBIL;
	}

	public String getIDMOBIL(){
		return iDMOBIL;
	}

	public void setDESKRIPSIMOBIL(String dESKRIPSIMOBIL){
		this.dESKRIPSIMOBIL = dESKRIPSIMOBIL;
	}

	public String getDESKRIPSIMOBIL(){
		return dESKRIPSIMOBIL;
	}

	public void setPLATMOBIL(String pLATMOBIL){
		this.pLATMOBIL = pLATMOBIL;
	}

	public String getPLATMOBIL(){
		return pLATMOBIL;
	}

	public void setNAMASUPIR(String nAMASUPIR){
		this.nAMASUPIR = nAMASUPIR;
	}

	public String getNAMASUPIR(){
		return nAMASUPIR;
	}

	public void setJENISMOBIL(String jENISMOBIL){
		this.jENISMOBIL = jENISMOBIL;
	}

	public String getJENISMOBIL(){
		return jENISMOBIL;
	}
}