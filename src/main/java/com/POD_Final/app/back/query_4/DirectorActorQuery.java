package com.POD_Final.app.back.query_4;

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
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Enzo on 15.11.15.
 */
public class DirectorActorQuery extends AbstractQuery<Map<String, ArrayList<ActorWrapper>>> {

    private final Query query;

    public DirectorActorQuery(Query query) {
        this.query = query;
    }

    @Override
    protected void processResult(Map<String, ArrayList<ActorWrapper>> result) {
        for (Map.Entry<String, ArrayList<ActorWrapper>> entry : result.entrySet()) {
            String directorName = entry.getKey();
            ArrayList<ActorWrapper> queue = entry.getValue();
            System.out.println("Director: " + directorName);
            for (ActorWrapper wrapper : queue) {
                System.out.println(String.format("\t %s", wrapper.getActorName(), wrapper.getTimesActed()));
            }
        }
    }

    @Override
    protected JobCompletableFuture<Map<String, ArrayList<ActorWrapper>>> getFuture(Job<String, Movie> job) {
        return job.mapper(new DirectorActorMapper())
                .reducer(new DirectorActorReducer())
                .submit(new DirectorActorCollator());
    }
}
