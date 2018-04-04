package com.example.kylemeystedt.news;

/**
 * Created by Kyle Meystedt on 3/30/2018.
 */

public class article {
    public static String ARTICLE_URL = "article url";
    public static String ARTICLE_AUTHOR = "article author";
    public static String ARTICLE_TITLE = "article title";
    public static String ARTICLE_DESCRIPTION = "article description";
    public String author, title, description, url;

    public article(String url, String author, String title, String description )
    {
        this.url = url;
        this.author = author;
        this.title = title;
        this.description = description;

    }
}
