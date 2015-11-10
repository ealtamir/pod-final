package com.POD_Final.app.back.query_1;

import com.POD_Final.app.back.QueryInterface;
import com.POD_Final.app.client.Movie;
import com.POD_Final.app.client.Query;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Enzo on 09.11.15.
 */
public class ActorVotesQuery implements QueryInterface {

    private final Query query;

    public ActorVotesQuery(Query query) {
        this.query = query;
    }

    @Override
    public void executeQuery(IMap<String, Movie> map, HazelcastInstance client) {
        JobTracker tracker = client.getJobTracker("default");
        KeyValueSource<String, Movie> source = KeyValueSource.fromMap(map);
        Job<String, Movie> job = tracker.newJob(source);

        ICompletableFuture<List<ActorVote>> future = job
                .mapper(new CustomMapper())
                .reducer(new ActorsReducer())
                .submit(new ActorsCollator(query));

        List<ActorVote> result = null;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            System.out.println("ERROR: Task was interrupted. Aborting...");
            System.exit(1);
        } catch (ExecutionException e) {
            System.out.println("ERROR: There was a problem with the execution of your query. Please try again.");
            System.exit(1);
        }

        ActorVote av;
        for (int i = 0; i < result.size(); i++) {
            av = result.get(i);
            System.out.println(String.format("%d) Actor: %s, votos: %d", i + 1, av.name, av.votes));
        }
    }
}
