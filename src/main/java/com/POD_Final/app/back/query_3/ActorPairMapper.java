package com.POD_Final.app.back.query_3;

import com.POD_Final.app.client.Movie;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class ActorPairMapper implements Mapper<String, Movie, ActorPair, String> {
    @Override
    public void map(String s, Movie movie, Context<ActorPair, String> context) {
        String[] actors = movie.getActors();
        int totalActors = actors.length;
        for(int i = 0; i < totalActors-1; i++){
            for(int j = i+1; j < totalActors; j++){
                context.emit(new ActorPair(actors[i], actors[j]), movie.getTitle());
            }
        }
    }
}
