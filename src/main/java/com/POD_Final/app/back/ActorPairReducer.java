package com.POD_Final.app.back;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.ArrayList;

public class ActorPairReducer implements ReducerFactory<String, String, ArrayList>{

    @Override
    public Reducer<String, ArrayList> newReducer(String s) {
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
