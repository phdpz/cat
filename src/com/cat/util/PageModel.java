package com.cat.util;

/**
 * @Desecration 封装分页数据，包括分页信息和查询结果集
 * @author 王亚南
 * @date 2014年12月2日 下午3:35:23
 * @param <T>
 */
public class PageModel<T>
{
	/** 查询结果集，包括数据和总记录数 **/
	private QueryResult<T> queryResult;
	
	/** 页索引（页面上显示的起始页号和结束页号 **/
	private PageIndex pageIndex;
	
	/** 总记录数  **/
	private int recordCount;
	
	/** 总页数 **/
	private int totalPages = 1;
	
	/** 每页显示记录数 （页面大小）**/
	private int pageSize=10;
	
	/** 当前页号 **/
	private int currentPageNo = 1;
	
	/** 每次显示多少页，必须保证大于3页，保证左右链接都可以使用 **/
	private int viewPageCounts;

	public QueryResult<T> getQueryResult() {
		return queryResult;
	}

	public void setQueryResult(QueryResult<T> queryResult)
	{
		this.queryResult = queryResult;
		int totalRecords = this.queryResult.getTotalRecords();
		setTotalPages(totalRecords % this.pageSize == 0 ? totalRecords / this.pageSize : totalRecords / this.pageSize + 1);
	}

	public PageIndex getPageIndex()
	{
		return pageIndex;
	}

	public void setPageIndex(PageIndex pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getTotalPages()
	{	
		Integer size = this.recordCount / this.pageSize;
		Integer mod = recordCount % pageSize;
		if (mod != 0) {
			size++;
		}
		return this.recordCount == 0 ? 1 : size;
	}

	public void setTotalPages(int totalPages)
	{
		this.totalPages = totalPages;
		this.pageIndex = PageIndex.getPageIndex(viewPageCounts, currentPageNo, totalPages);
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPageNo()
	{
		return currentPageNo;
	}
	
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = (currentPageNo <= 0 ? 1 : currentPageNo);
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getViewPageCounts()
	{
		return viewPageCounts;
	}

	public void setViewPageCounts(int viewPageCounts)
	{
		this.viewPageCounts = viewPageCounts;
	}

	public PageModel(int pageSize, int currentPageNo, int viewPageCounts) {
		
		this.pageSize = pageSize;
		this.currentPageNo = currentPageNo;
		this.viewPageCounts = viewPageCounts;
	}

	public PageModel(int pageSize, int viewPageCounts) {
		super();
		this.pageSize = pageSize;
		this.viewPageCounts = viewPageCounts;
	}

	public PageModel() {
		super();
	}
	

}
