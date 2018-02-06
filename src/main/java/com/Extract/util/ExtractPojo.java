package com.Extract.util;

public class ExtractPojo {
	private String clientID;
	private String batchSeq;
	private String Systdate;
	private String outFileName;
	
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getBatchSeq() {
		return batchSeq;
	}
	public void setBatchSeq(String batchSeq) {
		this.batchSeq = batchSeq;
	}
	public String getSystdate() {
		return Systdate;
	}
	public void setSystdate(String systdate) {
		Systdate = systdate;
	}
	
	public String getOutFileName() {
		return outFileName;
	}
	public void setOutFileName(String outFileName) {
		this.outFileName = outFileName;
	}
	
	public String toString() {
		return "clientId:" + clientID + "batchSeq:" + batchSeq + "Systdate:" + Systdate;
		
	}

}
