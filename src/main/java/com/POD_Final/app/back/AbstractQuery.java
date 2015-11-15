package com.POD_Final.app.back;

import com.POD_Final.app.client.Movie;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.*;

import java.util.concurrent.ExecutionException;

/**
 * Created by Enzo on 15.11.15.
 */
abstract public class AbstractQuery<T> implements QueryInterface {

    @Override
    public void executeQuery(IMap<String, Movie> map, HazelcastInstance client) {
        JobTracker tracker = client.getJobTracker("default");
        KeyValueSource<String, Movie> source = KeyValueSource.fromMap(map);
        Job<String, Movie> job = tracker.newJob(source);

        JobCompletableFuture<T> future = getFuture(job);

        T result = null;

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

        processResult(result);
    }

    protected abstract void processResult(T result);

    protected abstract JobCompletableFuture<T> getFuture(Job<String, Movie> job);
}