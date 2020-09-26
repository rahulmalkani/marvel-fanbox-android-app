package com.marvelfanbox.main;

public class NewsItemModel {
    private String source;
    private String urlToSource;
    private String title;
    private String content;
    private String urlToImage;
    private String dateOfPublish;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrlToSource() {
        return urlToSource;
    }

    public void setUrlToSource(String urlToSource) {
        this.urlToSource = urlToSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getDateOfPublish() {
        return dateOfPublish;
    }

    public void setDateOfPublish(String dateOfPublish) {
        this.dateOfPublish = dateOfPublish;
    }

    @Override
    public String toString() {
        return "Title: " + getTitle() + "\n" + getContent() + "\n" + getDateOfPublish() + "\n" + getSource() + "\n" + getUrlToImage() + "\n" + getUrlToSource();
    }
}
