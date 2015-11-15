package com.POD_Final.app.back.query_3;

import com.POD_Final.app.back.AbstractQuery;
import com.POD_Final.app.back.QueryInterface;
import com.POD_Final.app.client.Movie;
import com.POD_Final.app.client.Query;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;

/**
 * Created by Enzo on 15.11.15.
 */
public class ActorPairQuery extends AbstractQuery<Map<ActorPair, ArrayList>> {

    private final Query query;

    public ActorPairQuery(Query query) {
        this.query = query;
    }

    @Override
    protected void processResult(Map<ActorPair, ArrayList> result) {
        for (Map.Entry<ActorPair, ArrayList> entry : result.entrySet()) {
            ActorPair pair = entry.getKey();
            ArrayList<String> movies = entry.getValue();

            System.out.println(String.format("%s - %s", pair.getFirstActorName(), pair.getSecondActorName()));
            for (String title : movies) {
                System.out.println(String.format("\t%s", title));
            }
        }
    }

    @Override
    protected JobCompletableFuture<Map<ActorPair, ArrayList>> getFuture(Job<String, Movie> job) {
        return job.mapper(new ActorPairMapper())
                .reducer(new ActorPairReducer())
                .submit();
    }
}
