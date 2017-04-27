package com.example.android.popularmovies2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jjesusmp
 */

public class MovieDto implements Parcelable {

    private String title;
    private String image;
    private String sypnosis;
    private String user_rating;
    private String release_date;
    private String _idMovie;


    public static final Creator<MovieDto> CREATOR = new Creator<MovieDto>() {
        @Override
        public MovieDto createFromParcel(Parcel in) {

            MovieDto movie = new MovieDto();
            movie._idMovie = in.readString();
            movie.title = in.readString();
            movie.image = in.readString();
            movie.sypnosis = in.readString();
            movie.user_rating = in.readString();
            movie.release_date = in.readString();

            return movie;
        }

        @Override
        public MovieDto[] newArray(int size) {
            return new MovieDto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_idMovie);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(sypnosis);
        dest.writeString(user_rating);
        dest.writeString(release_date);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "_idMovie='" + _idMovie + '\'' +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", sypnosis='" + sypnosis + '\'' +
                ", user_rating='" + user_rating + '\'' +
                ", release_date='" + release_date + '\'' +
                '}';
    }

    public String get__idMovie() {
        return _idMovie;
    }

    public void set__idMovie(String _id) {
        this._idMovie = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSypnosis() {
        return sypnosis;
    }

    public void setSypnosis(String sypnosis) {
        this.sypnosis = sypnosis;
    }

    public String getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


}
