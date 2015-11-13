package com.POD_Final.app.back.query_3;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.ArrayList;

public class ActorPairReducer implements ReducerFactory<ActorPair, String, ArrayList>{

    @Override
    public Reducer<String, ArrayList> newReducer(final ActorPair actorPair) {
        return new Reducer<String, ArrayList>() {
            private ArrayList<String> movieTitles;

            public void beginReduce() {
                movieTitles = new ArrayList<String>();
            }

            @Override
            public void reduce(String s) {
                movieTitles.add(s);
            }

            @Override
            public ArrayList finalizeReduce() {
                return movieTitles;
            }
        };
    }
}
