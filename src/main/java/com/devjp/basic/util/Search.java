package com.devjp.basic.util;

import java.util.Map;

public class Search extends PageMaker {
	private String keyword;
	private Map<String, Object> searchOptions;

	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Map<String, Object> getSearchOptions() {
		return searchOptions;
	}
	public void setSearchOptions(Map<String, Object> searchOptions) {
		this.searchOptions = searchOptions;
	}
	
	@Override
	public String toString() {
		return "Search [keyword=" + keyword + ", searchOptions=" + searchOptions + "]";
	}
}
