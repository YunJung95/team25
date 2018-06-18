package com.myin.team25.domain;

public class CommentVo {
	
	private int cidx;
	private String cwriter;
	private String ccontent;
	private String writeday;
	private String deleteday;
	private String ip;
	private String del_yn;
	private int bbidx;
	private int memberMidx;
	
	public int getCidx() {
		return cidx;
	}
	public void setCidx(int cidx) {
		this.cidx = cidx;
	}
	public String getCwriter() {
		return cwriter;
	}
	public void setCwriter(String cwriter) {
		
		System.out.println("cwriter:"+cwriter);
		this.cwriter = cwriter;
	}
	public String getCcontent() {
		return ccontent;
	}
	public void setCcontent(String ccontent) {
		this.ccontent = ccontent;
	}
	public String getWriteday() {
		return writeday;
	}
	public void setWriteday(String writeday) {
		this.writeday = writeday;
	}
	public String getDeleteday() {
		return deleteday;
	}
	public void setDeleteday(String deleteday) {
		this.deleteday = deleteday;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	public int getBbidx() {
		return bbidx;
	}
	public void setBbidx(int bbidx) {
		this.bbidx = bbidx;
	}
	public int getMemberMidx() {
		return memberMidx;
	}
	public void setMemberMidx(int memberMidx) {
		this.memberMidx = memberMidx;
	}

}
