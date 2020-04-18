package Server.model.DTO;

import java.util.Date;

public class Criteria {

    private boolean ascending;
    private int itemPerPage = 20;
    private String keyword = "";
    private int currentPage = -1;
    private long categoryId = -1;
    private int top = -1;
    private Class clazz;
    private Date startDate; // Ngày bắt đầu dùng để láy ngày bắt đầu theo một số trường hợp
    private Date endDate; // Ngày kết thúc dùng để láy ngày bắt đầu theo một số trường hợp

    public Criteria() {
    }

    public Criteria(boolean ascending, int itemPerPage, String keyword, int currentPage, long categoryId, int top, Class clazz, Date startDate, Date endDate) {
        this.ascending = ascending;
        this.itemPerPage = itemPerPage;
        this.keyword = keyword;
        this.currentPage = currentPage;
        this.categoryId = categoryId;
        this.top = top;
        this.clazz = clazz;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public int getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(int itemPerPage) {
        this.itemPerPage = itemPerPage;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
