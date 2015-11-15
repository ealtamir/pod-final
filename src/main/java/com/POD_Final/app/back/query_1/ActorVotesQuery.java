package com.POD_Final.app.back.query_1;

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

import java.util.List;

/**
 * Created by Enzo on 09.11.15.
 */
public class ActorVotesQuery extends AbstractQuery<List<ActorVote>> {

    private final Query query;

    public ActorVotesQuery(Query query) {
        this.query = query;
    }

    @Override
    protected void processResult(List<ActorVote> result) {
        ActorVote av;
        for (int i = 0; i < result.size(); i++) {
            av = result.get(i);
            System.out.println(String.format("%d) Actor: %s, votos: %d", i + 1, av.name, av.votes));
        }
    }

    @Override
    protected JobCompletableFuture<List<ActorVote>> getFuture(Job<String, Movie> job) {
        return job.mapper(new ActorsMapper())
                .reducer(new ActorsReducer())
                .submit(new ActorsCollator(query));
    }
}
