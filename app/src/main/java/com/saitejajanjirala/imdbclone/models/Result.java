package com.saitejajanjirala.imdbclone.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity(tableName = "results")
public class Result implements Serializable {
    public boolean adult;
    public String backdrop_path;
    public List<Integer> genre_ids;
    @PrimaryKey
    @NotNull
    public String pKey=""+System.currentTimeMillis();
    public int id;
    public String original_language;
    public String original_title;
    public String overview;
    public double popularity;
    public String poster_path;
    public String release_date;
    public String title;
    public boolean video;
    public double vote_average;
    public int vote_count;
    public boolean bookmarked;
    public String type;

    @Override
    public String toString() {
        return "Result{" +
                "adult=" + adult +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", genre_ids=" + genre_ids +
                ", id=" + id +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", poster_path='" + poster_path + '\'' +
                ", release_date='" + release_date + '\'' +
                ", title='" + title + '\'' +
                ", video=" + video +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                '}';
    }

}
