package com.POD_Final.app.back.query_4;

import com.POD_Final.app.client.Movie;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class DirectorActorMapper implements Mapper<String, Movie, String, String> {
    @Override
    public void map(String s, Movie movie, Context<String, String> context) {
        for(String actor : movie.getActors()){
            context.emit(movie.getDirector(), actor);
        }
    }
}
