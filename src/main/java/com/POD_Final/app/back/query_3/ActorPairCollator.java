package com.POD_Final.app.back.query_3;

import com.hazelcast.mapreduce.Collator;

import java.util.*;

public class ActorPairCollator implements Collator<Map.Entry<ActorPair, ArrayList<String>>, List<ActorPairWithMovies>> {
    private int maxActs;
    private ArrayList<ActorPairWithMovies> list;

    public ActorPairCollator(){
        this.maxActs = 0;
        this.list = new ArrayList<ActorPairWithMovies>();
    }

    @Override
    public ArrayList<ActorPairWithMovies> collate(Iterable<Map.Entry<ActorPair, ArrayList<String>>> entries) {
        for (Map.Entry<ActorPair, ArrayList<String>> entry : entries) {
            if(entry.getValue().size() > maxActs){
                list.clear();
                list.add(new ActorPairWithMovies(entry.getKey(), entry.getValue()));
                maxActs = entry.getValue().size();
            } else if(entry.getValue().size() == maxActs){
                list.add(new ActorPairWithMovies(entry.getKey(), entry.getValue()));
            }
        }

        return list;
    }
}
