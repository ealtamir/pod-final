package com.POD_Final.app.back.query_2;

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

import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;

/**
 * Created by Enzo on 15.11.15.
 */
public class MostAcclaimedQuery implements QueryInterface {

    private final Query query;

    public MostAcclaimedQuery(Query query) {
        this.query = query;
    }

    @Override
    public void executeQuery(IMap<String, Movie> map, HazelcastInstance client) {
        JobTracker tracker = client.getJobTracker("default");
        KeyValueSource<String, Movie> source = KeyValueSource.fromMap(map);
        Job<String, Movie> job = tracker.newJob(source);

        JobCompletableFuture<Map<Integer, PriorityQueue<Movie>>> future = job
                .mapper(new MostAcclaimedMapper(query.getTope()))
                .reducer(new MostAcclaimedReducer())
                .submit();

        Map<Integer, PriorityQueue<Movie>> result = null;

        try {
            result = future.get();
        } catch (InterruptedException e) {
            System.out.println("ERROR: Task was interrupted. Aborting...");
            System.exit(1);
        } catch (ExecutionException e) {
            System.out.println("ERROR: There was a problem with the execution of your query. Please try again.");
            System.exit(1);
        }

        for (Map.Entry<Integer, PriorityQueue<Movie>> entry : result.entrySet()) {
            int year = entry.getKey();
            PriorityQueue<Movie> movies = entry.getValue();
            System.out.println("Year: " + String.valueOf(year));
            for (Movie movie : movies) {
                System.out.println(String.format("\t%s", movie.getTitle()));
            }
        }

    }
}
