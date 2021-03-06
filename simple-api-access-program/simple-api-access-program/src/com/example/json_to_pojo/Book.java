package com.example.json_to_pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Book {
	private String title;
	private String author;
	private String issn;
	private String publisher;
	private String city;
    private String year;
    private int pages;
    private String language;
    private String topic;
    @SerializedName("coverurl")
	private String coverUrl;
    
    public String getTitle (){
        return title;
    }

    public void setTitle (String title){
        this.title = title;
    }

    public String getAuthor (){
        return author;
    }

    public void setAuthor (String author){
        this.author = author;
    }
    
    public String getIssn (){
        return issn;
    }

    public void setIssn (String issn){
        this.issn = issn;
    }
    
    public String getPublisher (){
        return publisher;
    }

    public void setPublisher (String publisher){
        this.publisher = publisher;
    }
    
    public String getCity (){
        return city;
    }

    public void setCity (String city){
        this.city = city;
    }

    public String getYear (){
        return year;
    }

    public void setYear (String year){
        this.year = year;
    }
    
    public int getPages (){
        return pages;
    }

    public void setPages (int pages){
        this.pages = pages;
    }
    
    public String getLanguage (){
        return language;
    }

    public void setLanguage (String language){
        this.language = language;
    }

    public String getTopic (){
        return topic;
    }

    public void setTopic (String topic){
        this.topic = topic;
    }

    public String getCoverUrl (){
        return coverUrl;
    }

    public void setCoverUrl (String coverUrl){
        this.coverUrl = coverUrl;
    }
    
    @Override
    public String toString(){
        return "Book's Description\n" + "=======================================" + 
        		"\n\t Title = " + title +
        		"\n\t Author = " + author +
        		"\n\t ISSN = " + issn +
        		"\n\t Publisher = " + publisher + 
        		"\n\t City = " + city + 
        		"\n\t Year = " + year + 
        		"\n\t Pages = " + pages + 
        		"\n\t Language = " + language + 
        		"\n\t Topic = " + topic + 
        		"\n\t Cover URL = " + coverUrl + "\n";
    }
}