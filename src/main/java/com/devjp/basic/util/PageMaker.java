package com.devjp.basic.util;

public class PageMaker {
	private int currPage;
	private int perPage;
	private int totalRow;
	private int totalPage;
	private int start;
	
	public PageMaker() {
		this.currPage=1;
	}
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		if(currPage < 1) currPage = 1;
		this.currPage = currPage;
	}
	public int getPerPage() {
		return perPage;
	}
	public void setPerPage(int perPage) {
		if(perPage < 1 || perPage > 100) perPage = 10;
		this.perPage = perPage;
	}
	public int getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
		calcData();
	}
	public int getStart() {
		return start;
	}
	public void setStart() {
		start = (currPage-1)*perPage;
		if(start < 0) start = 0;
	}
	public int getTotalPage() {
		return totalPage;
	}
	private void calcData() {
		totalPage = (int)Math.ceil(totalRow/(double)perPage);
	}
	
	@Override
	public String toString() {
		return "PageMaker [currPage=" + currPage + ", perPage=" + perPage + ", totalRow=" + totalRow + ", totalPage="
				+ totalPage + ", start=" + start + "]";
	}
}
