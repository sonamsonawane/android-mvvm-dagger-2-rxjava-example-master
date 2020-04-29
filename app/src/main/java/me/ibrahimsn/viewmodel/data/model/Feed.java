package me.ibrahimsn.viewmodel.data.model;

import java.util.List;

public class Feed {

    private transient long id;
    private String status;
    private long totalResults;
    private List<Record> articles;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public List<Record> getArticles() {
        return articles;
    }

    public void setArticles(List<Record> articles) {
        this.articles = articles;
    }
}
