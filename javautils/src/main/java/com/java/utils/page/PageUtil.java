package com.java.utils.page;

/**
 * Created by Tim on 2017/6/14.
 * 简单的分页工具
 */
public class PageUtil {

    private int totalCount;
    private int pageSize = 10;
    private int currentPageNum;
    private int pageCount;
    private int prePage;
    private int nextPage;
    private boolean hasPrePage;
    private boolean hasNextPage;
    private int firstPage;
    private int lastPage;
    private int currentcount;

    public PageUtil(){}

    /**
     * 初始化page参数
     * @param totalCount 总页数
     * @param pageNum 当前页
     */
    public PageUtil(int totalCount, int pageNum) {
        this.totalCount = totalCount;
        this.currentPageNum = pageNum;
        this.pageCount = (int)Math.ceil(1.0 * totalCount / pageSize)
        this.currentcount = (pageCount - 1) * pageSize;
        if(pageNum > 1) {
            hasPrePage = true;
            prePage = pageNum - 1;
            firstPage = 1;
        }
        if(pageNum < pageCount ) {
            hasNextPage = true;
            nextPage = pageNum + 1;
            lastPage = pageCount;
        }
    }

    /**
     * 根据传入的总记录数，初始化page参数
     * @param totalCount
     */
    public void setTotalCount(int totalCount) {
        this.pageCount = (int)Math.ceil(1.0 * totalCount / pageSize);
        if(this.currentPageNum < 1) {
            this.currentPageNum = 1;
        }
        this.currentcount = (currentPageNum-1) * pageSize;
        if(currentPageNum > 1) {
            hasPrePage = true;
            prePage = currentPageNum - 1;
            firstPage = 1;
        }
        if(currentPageNum < pageCount ) {
            hasNextPage = true;
            nextPage = currentPageNum + 1;
            lastPage = pageCount;
        }
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getCurrentcount() {
        return currentcount;
    }

    public void setCurrentcount(int currentcount) {
        this.currentcount = currentcount;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }
}
