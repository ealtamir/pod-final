package com.POD_Final.app.back.query_4;

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
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;

/**
 * Created by Enzo on 15.11.15.
 */
public class DirectorActorQuery implements QueryInterface {

    private final Query query;

    public DirectorActorQuery(Query query) {
        this.query = query;
    }

    @Override
    public void executeQuery(IMap<String, Movie> map, HazelcastInstance client) {
        JobTracker tracker = client.getJobTracker("default");
        KeyValueSource<String, Movie> source = KeyValueSource.fromMap(map);
        Job<String, Movie> job = tracker.newJob(source);

        JobCompletableFuture<Map<String, ArrayList<ActorWrapper>>> future = job
                .mapper(new DirectorActorMapper())
                .reducer(new DirectorActorReducer())
                .submit();

        Map<String, ArrayList<ActorWrapper>> result = null;

        try {
            result = future.get();
        } catch (InterruptedException e) {
            System.out.println("ERROR: Task was interrupted. Aborting...");
            System.exit(1);
        } catch (ExecutionException e) {
            System.out.println("ERROR: There was a problem with the execution of your query. Please try again.");
            e.printStackTrace();
            System.exit(1);
        }
        for (Map.Entry<String, ArrayList<ActorWrapper>> entry : result.entrySet()) {
            String directorName = entry.getKey();
            ArrayList<ActorWrapper> queue = entry.getValue();
            System.out.println("Director: " + directorName);
            for (ActorWrapper wrapper : queue) {
                System.out.println(String.format("\t %s %d", wrapper.getActorName(), wrapper.getTimesActed()));
            }
        }

    }
}
