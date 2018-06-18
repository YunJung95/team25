package com.myin.team25.domain;

import org.springframework.web.multipart.MultipartFile;

public class BoardVo {
	
	private int bbidx;
	private int oidx;
	private int updown;
	private int leftright;
	private int memberMidx;
	private String subject;
	private String content;
	private String writer;
	private String password;
	private String writeday;
	private String modifyday;
	private String ip;
	private String del_yn;	
	
	private MultipartFile uploadfile;
	private String filename;
	
	
	public int getBbidx() {
		return bbidx;
	}
	public void setBbidx(int bbidx) {
		this.bbidx = bbidx;
	}
	public int getOidx() {
		return oidx;
	}
	public void setOidx(int oidx) {
		this.oidx = oidx;
	}
	public int getUpdown() {
		return updown;
	}
	public void setUpdown(int updown) {
		this.updown = updown;
	}
	public int getLeftright() {
		return leftright;
	}
	public void setLeftright(int leftright) {
		this.leftright = leftright;
	}
	public int getMemberMidx() {
		return memberMidx;
	}
	public void setMemberMidx(int memberMidx) {
		this.memberMidx = memberMidx;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getWriteday() {
		return writeday;
	}
	public void setWriteday(String writeday) {
		this.writeday = writeday;
	}
	public String getModifyday() {
		return modifyday;
	}
	public void setModifyday(String modifyday) {
		this.modifyday = modifyday;
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
	
	
	
	
	public MultipartFile getUploadfile() {
		return uploadfile;
	}
	public void setUploadfile(MultipartFile uploadfile) {
		this.uploadfile = uploadfile;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}	
	
}
