package com.POD_Final.app.back.query_1;

import com.POD_Final.app.client.Query;
import com.hazelcast.mapreduce.Collator;

import java.util.*;

/**
 * Created by Enzo on 09.11.15.
 */
public class ActorsCollator implements Collator<Map.Entry<String, Integer>, List<ActorVote>> {

    private final int cantActores;

    public ActorsCollator(Query query) {
        this.cantActores = query.getCantActores();
    }

    @Override
    public List<ActorVote> collate(Iterable<Map.Entry<String, Integer>> iterable) {
        PriorityQueue<ActorVote> queue = new PriorityQueue<ActorVote>(new Comparator<ActorVote>() {
            @Override
            public int compare(ActorVote o1, ActorVote o2) {
                if (o1.votes > o2.votes)
                    return -1;
                if (o1.votes < o2.votes)
                    return 1;

                if(o1.name.compareTo(o2.name) < 0)
                    return -1;
                if(o1.name.compareTo(o2.name) > 0)
                    return 1;

                return 0;
            }
        });

        ArrayList<ActorVote> actors = new ArrayList<ActorVote>();
        for (Map.Entry<String, Integer> entry : iterable) {
            queue.add(new ActorVote(entry.getKey(), entry.getValue()));
        }
        for (int i = 0; i < cantActores && !queue.isEmpty(); i++) {
            actors.add(queue.poll());
        }
        return actors;
    }
}
