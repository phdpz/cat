package com.cat.base.controller;

import com.cat.util.PageModel;

public class BaseController {
	
	//JSP页面传递过来的当前页号,默认的话页面初始值为“1”
	private int currentPageNo = 1;

	public int getCurrentPageNo() {
		return currentPageNo < 1 ? 1 : currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
}
