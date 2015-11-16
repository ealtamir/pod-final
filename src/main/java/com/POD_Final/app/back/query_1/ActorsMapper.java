package com.POD_Final.app.back.query_1;

import com.POD_Final.app.client.Movie;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

/* Mapper for the 1st query */
public class ActorsMapper implements Mapper<String, Movie, String, Integer> {
    @Override
    public void map(String s, Movie movie, Context<String, Integer> context) {
        for(String actorName : movie.getActors()) {
            context.emit(actorName, movie.getVotes());
        }
    }
}
