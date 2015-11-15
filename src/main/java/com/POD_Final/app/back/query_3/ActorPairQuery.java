package com.POD_Final.app.back.query_3;

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
public class ActorPairQuery implements QueryInterface {

    private final Query query;

    public ActorPairQuery(Query query) {
        this.query = query;
    }

    @Override
    public void executeQuery(IMap<String, Movie> map, HazelcastInstance client) {
        JobTracker tracker = client.getJobTracker("default");
        KeyValueSource<String, Movie> source = KeyValueSource.fromMap(map);
        Job<String, Movie> job = tracker.newJob(source);

        JobCompletableFuture<Map<ActorPair, ArrayList>> future = job
                .mapper(new ActorPairMapper())
                .reducer(new ActorPairReducer())
                .submit();

        Map<ActorPair, ArrayList> result = null;

        try {
            result = future.get();
        } catch (InterruptedException e) {
            System.out.println("ERROR: Task was interrupted. Aborting...");
            System.exit(1);
        } catch (ExecutionException e) {
            System.out.println("ERROR: There was a problem with the execution of your query. Please try again.");
            System.exit(1);
        }

        for (Map.Entry<ActorPair, ArrayList> entry : result.entrySet()) {
            ActorPair pair = entry.getKey();
            ArrayList<String> movies = entry.getValue();

            System.out.println(String.format("%s - %s", pair.getFirstActorName(), pair.getSecondActorName()));
            for (String title : movies) {
                System.out.println(String.format("\t%s", title));
            }
        }

    }
}
