package com.POD_Final.app.back.query_2;

import com.POD_Final.app.client.Movie;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class MostAcclaimedMapper implements Mapper<String, Movie, Integer, Movie> {
    private final int yearSince;

    public MostAcclaimedMapper(int yearSince){
        this.yearSince = yearSince;
    }

    @Override
    public void map(String s, Movie movie, Context<Integer, Movie> context) {
        int movieYear = movie.getYear();
        if(movieYear >= yearSince){
            context.emit(movie.getYear(), movie);
        }
    }
}
