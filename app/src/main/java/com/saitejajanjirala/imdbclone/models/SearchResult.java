package com.saitejajanjirala.imdbclone.models;

import java.util.List;

public class SearchResult {
    public int page;
    public List<Result> results;
    public int total_pages;
    public int total_results;

    @Override
    public String toString() {
        return "SearchResult{" +
                "page=" + page +
                ", results=" + results +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}
