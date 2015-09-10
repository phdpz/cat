package com.cat.util;

import java.util.List;
/**
 * @Desecration 查询结果集，包括数据和总记录数
 * @author 王亚南
 * @date 2014年12月2日 下午3:38:12
 * @param <T>
 */
public class QueryResult<T>
{
	/** 查询得出的数据List **/
	private List<T> resultList;
	
	/** 查询得出的总记录数 **/
	private int totalRecords;

	public List<T> getResultList()
	{
		return resultList;
	}

	public void setResultList(List<T> resultList)
	{
		this.resultList = resultList;
	}

	public int getTotalRecords()
	{
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords)
	{
		this.totalRecords = totalRecords;
	}
}
