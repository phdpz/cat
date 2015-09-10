package com.cat.util;

/**
 * @Desecration 页索引，主要是页面上显示起始页号和终止页号
 * @author 王亚南
 * @date 2014年12月2日 下午3:32:12
 */
public class PageIndex {
	
	/** 起始页号 **/
	private int startPageNo;
	
	/** 结束页号 **/
	private int endPageNo;

	public PageIndex(int startPageNo, int endPageNo) {
		this.startPageNo = startPageNo;
		this.endPageNo = endPageNo;
	}

	public int getStartPageNo() {
		return startPageNo;
	}

	public void setStartPageNo(int startPageNo) {
		this.startPageNo = startPageNo;
	}

	public int getEndPageNo() {
		return endPageNo;
	}

	public void setEndPageNo(int endPageNo) {
		this.endPageNo = endPageNo;
	}

	/**
	 * 起始页号和终止页号
	 * 
	 * @param viewPageCounts
	 *            显示多少页
	 * @param currentPageNo
	 *            当前页号
	 * @param totalPages
	 *            总页数
	 * @return PageIndex 起始页号和终止页号
	 */
	public static PageIndex getPageIndex(int viewPageCounts, int currentPageNo,	int totalPages) {
		
		int startPageNo = currentPageNo	- (viewPageCounts % 2 == 0 ? viewPageCounts / 2 - 1	: viewPageCounts / 2);
		int endPageNo = currentPageNo + viewPageCounts / 2;
		
		if (startPageNo < 1) {
			startPageNo = 1;
			if (totalPages >= viewPageCounts)	endPageNo = viewPageCounts;
			else	endPageNo = totalPages;
		}
		
		if (endPageNo > totalPages) {
			endPageNo = totalPages;
			if ((endPageNo - viewPageCounts) > 0)
				startPageNo = endPageNo - viewPageCounts + 1;
			else
				startPageNo = 1;
		}
		
		return new PageIndex(startPageNo, endPageNo);
	}
}
