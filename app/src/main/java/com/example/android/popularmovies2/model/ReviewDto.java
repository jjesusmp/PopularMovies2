package com.example.android.popularmovies2.model;

/**
 * Created by jjesusmp on 27/04/2017.
 */


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewDto implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("url")
    private String url;

    public final static Parcelable.Creator<ReviewDto> CREATOR = new Creator<ReviewDto>() {

        public ReviewDto createFromParcel(Parcel in) {
            ReviewDto instance = new ReviewDto();
            instance.id = in.readString();
            instance.author = in.readString();
            instance.content = in.readString();
            instance.url = in.readString();
            return instance;
        }

        public ReviewDto[] newArray(int size) {
            return (new ReviewDto[size]);
        }

    }
            ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(author);
        dest.writeValue(content);
        dest.writeValue(url);
    }

    public int describeContents() {
        return 0;
    }


}