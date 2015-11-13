package com.POD_Final.app.back.query_4;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class DirectorActorReducer implements ReducerFactory<String, String, PriorityQueue<ActorWrapper>>{
    @Override
    public Reducer<String, PriorityQueue<ActorWrapper>> newReducer(final String directorName) {
        return new Reducer<String, PriorityQueue<ActorWrapper>>() {
            Map<String, Integer> actorsCounts;
            PriorityQueue<ActorWrapper> favoriteActors; // TODO maybe use an ArrayList?

            public void beginReduce(){
                actorsCounts = new HashMap<String, Integer>();
                favoriteActors = new PriorityQueue<ActorWrapper>(new Comparator<ActorWrapper>() {
                    @Override
                    public int compare(ActorWrapper o1, ActorWrapper o2) {
                        if(o1.getTimesActed() < o2.getTimesActed())
                            return -1;
                        if(o1.getTimesActed() > o2.getTimesActed())
                            return 1;
                        return 0;
                    }
                });
                favoriteActors.add(new ActorWrapper("N/A", 0));
            }

            @Override
            public void reduce(String actorName) {
                Integer moviesTogether = actorsCounts.get(actorName);

                if(moviesTogether == null)
                    moviesTogether = 1;
                else
                    moviesTogether++;

                actorsCounts.put(actorName, moviesTogether);

                if(favoriteActors.peek().getTimesActed() > moviesTogether)
                    return;
                if(favoriteActors.peek().getTimesActed() < moviesTogether)
                    favoriteActors.clear();
                favoriteActors.add(new ActorWrapper(actorName, moviesTogether));
            }

            @Override
            public PriorityQueue<ActorWrapper> finalizeReduce() {
                return favoriteActors;
            }
        };
    }
}
