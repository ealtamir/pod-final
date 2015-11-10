package com.POD_Final.app.back;

import com.POD_Final.app.client.Movie;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class MostAcclaimedMapper implements Mapper<String, Movie, Integer, Movie> {

    @Override
    public void map(String s, Movie movie, Context<Integer, Movie> context) {
        context.emit(movie.getStartingYear(), movie);
    }
}
