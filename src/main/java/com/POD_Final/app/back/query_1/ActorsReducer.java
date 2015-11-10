package com.POD_Final.app.back.query_1;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class ActorsReducer implements ReducerFactory<String, Integer, Integer> {

    @Override
    public Reducer<Integer, Integer> newReducer(final String actorName) {
        return new Reducer<Integer, Integer>() {
            private int totalVotes;

            public void beginReduce() {
                totalVotes = 0;
            }

            @Override
            public void reduce(Integer votes) {
                totalVotes += votes;
            }

            @Override
            public Integer finalizeReduce() {
                return totalVotes;
            }
        };
    }
}
