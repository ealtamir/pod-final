package com.POD_Final.app.back;

import com.POD_Final.app.client.Movie;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class ActorPairMapper implements Mapper<String, Movie, String, String> {
    @Override
    public void map(String s, Movie movie, Context<String, String> context) {
        for(String name1 : movie.getActors()) {
            for(String name2 : movie.getActors()){
                if(!name1.equals(name2)){
                    context.emit(orderedAppend(name1, name2), movie.getTitle());
                }
            }
        }
    }

    private String orderedAppend(String a, String b){
        if(a.compareTo(b) <= 0)
            return a + " & " + b;

        return b + " & " + a;
    }
}
