package com.POD_Final.app.back.query_4;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.*;

public class DirectorActorReducer implements ReducerFactory<String, String, ArrayList<ActorWrapper>>{
    @Override
    public Reducer<String, ArrayList<ActorWrapper>> newReducer(final String directorName) {
        return new Reducer<String, ArrayList<ActorWrapper>>() {
            Map<String, Integer> actorsCounts;
            ArrayList<ActorWrapper> favoriteActors; // TODO maybe use an ArrayList?

            public void beginReduce(){
                actorsCounts = new HashMap<String, Integer>();
                favoriteActors = new ArrayList<ActorWrapper>();
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

                int timesActed = favoriteActors.get(0).getTimesActed();
                if(timesActed > moviesTogether)
                    return;
                if(timesActed < moviesTogether)
                    favoriteActors.clear();
                favoriteActors.add(new ActorWrapper(actorName, moviesTogether));
            }

            @Override
            public ArrayList<ActorWrapper> finalizeReduce() {
                return favoriteActors;
            }
        };
    }
}
