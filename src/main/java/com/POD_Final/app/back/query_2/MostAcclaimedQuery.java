package com.POD_Final.app.back.query_2;

import com.POD_Final.app.back.AbstractQuery;
import com.POD_Final.app.back.QueryInterface;
import com.POD_Final.app.client.Movie;
import com.POD_Final.app.client.Query;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;

/**
 * Created by Enzo on 15.11.15.
 */
public class MostAcclaimedQuery extends AbstractQuery<Map<Integer, ArrayList<Movie>>> {

    private final Query query;

    public MostAcclaimedQuery(Query query) {
        this.query = query;
    }

    @Override
    protected void processResult(Map<Integer, ArrayList<Movie>> result) {
        for (Map.Entry<Integer, ArrayList<Movie>> entry : result.entrySet()) {
            int year = entry.getKey();
            ArrayList<Movie> movies = entry.getValue();
            System.out.println(String.valueOf(year));
            for (Movie movie : movies) {
                System.out.println(String.format("\t%s", movie.getTitle()));
            }
        }
    }

    @Override
    protected JobCompletableFuture<Map<Integer, ArrayList<Movie>>> getFuture(Job<String, Movie> job) {
        return job.mapper(new MostAcclaimedMapper(query.getTope()))
                .reducer(new MostAcclaimedReducer())
                .submit();
    }
}
