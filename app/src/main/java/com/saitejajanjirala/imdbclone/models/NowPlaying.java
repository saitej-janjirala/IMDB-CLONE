package com.saitejajanjirala.imdbclone.models;

import com.saitejajanjirala.imdbclone.BaseApplication;

import java.util.List;

public class NowPlaying extends BaseResponse{

    public Dates dates;
    public int page;
    public List<Result> results;
    public int total_pages;
    public int total_results;

    class Dates{
        public String maximum;
        public String minimum;

        @Override
        public String toString() {
            return "Dates{" +
                    "maximum='" + maximum + '\'' +
                    ", minimum='" + minimum + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NowPlaying{" +
                "dates=" + dates +
                ", page=" + page +
                ", results=" + results +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}
