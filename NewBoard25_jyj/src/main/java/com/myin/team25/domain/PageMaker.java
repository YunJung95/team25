package com.myin.team25.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {
	
	private int totalCount;//전체게시물수
	private int startPage;//시작 페이지 
	private int endPage; //끝페이지
	private boolean prev;//화살표 <
	private boolean next;	//화살표 >
	private int displayPageNum = 10;	//페이지 번호를 몇번까지 보여줄건지
	private SearchCriteria scri; //크리를 가지고 다녀야함
	
	public SearchCriteria getScri() {
		return scri;
	}
	public void setScri(SearchCriteria scri) {
		this.scri = scri;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		System.out.println("totalCount:"+totalCount);
		calcData();
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}	
	
	private void calcData(){
		endPage = (int) (Math.ceil(scri.getPage()/
				(double)displayPageNum) * displayPageNum);
				//display 페이지 번호를 몇번까지 보여줄건지
		startPage = (endPage - displayPageNum)+1; //10- 10 +1
		
		int tempEndPage = (int) (Math.ceil(totalCount)/(double) scri.getPerPageNum());
		
		if(endPage>tempEndPage) {
			endPage = tempEndPage;
		}		
		prev = startPage == 1 ? false : true;		
		next = endPage * scri.getPerPageNum() >= totalCount ? false : true;
	}	
	
	public String makeQuery(int page){
		UriComponents uriComponents = 
				UriComponentsBuilder
				.newInstance()
				.queryParam("page", page)
			//	.queryParam("perPageNum", cri.getPerPageNum())
				.build();
				
		return uriComponents.toUriString();
	}
	
	public String makeSearch(int page){
		UriComponents uriComponents = 
				UriComponentsBuilder
				.newInstance()
				.queryParam("page", page)
			//	.queryParam("perPageNum", cri.getPerPageNum())
				.queryParam("searchType", scri.getSearchType())
				.queryParam("keyword", encoding(scri.getKeyword()))
				.build();
				
		return uriComponents.toUriString();
	}
	
	public String encoding(String keyword){
		
		if(keyword==null || keyword.trim().length()==0){
			return "";
		}		
		try{
			return URLEncoder.encode(keyword, "UTF-8");
		}catch(UnsupportedEncodingException e){
			return "";			
		}
	}
	
	}

