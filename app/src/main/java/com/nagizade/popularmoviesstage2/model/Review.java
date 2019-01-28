package com.nagizade.popularmoviesstage2.model;

/**
 * Created by Hasan Nagizade on 23 January 2019
 */

public class Review {

    private String id;
    private String review_author;
    private String review_content;
    private String url_adress;

    public Review(String review_author, String review_content, String url_adress) {
        this.review_author = review_author;
        this.review_content = review_content;
        this.url_adress = url_adress;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", review_author='" + review_author + '\'' +
                ", review_content='" + review_content + '\'' +
                ", url_adress='" + url_adress + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReview_author() {
        return review_author;
    }

    public void setReview_author(String review_author) {
        this.review_author = review_author;
    }

    public String getReview_content() {
        return review_content;
    }

    public void setReview_content(String review_content) {
        this.review_content = review_content;
    }

    public String getUrl_adress() {
        return url_adress;
    }

    public void setUrl_adress(String url_adress) {
        this.url_adress = url_adress;
    }

}
