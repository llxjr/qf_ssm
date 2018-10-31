package com.ssm.promotion.core.entity;

/**
 * @author liu66
 * @project_name perfect-ssm
 * @date 2017-3-1
 */
public class PageBean {

    private int page; // 页码
    private int pageSize; // 单页数据量
    private int start;


    public PageBean(int page, int pageSize) {
        super();
        this.page = page;
        this.pageSize = pageSize;
    }
    
    public PageBean(String page, String pageSize) {
    	super();
    	this.page = Integer.parseInt(page);
    	this.pageSize = Integer.parseInt(pageSize);
    }
    
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return (page - 1) * pageSize;
    }


}
