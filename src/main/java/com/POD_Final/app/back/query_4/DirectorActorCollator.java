package com.POD_Final.app.back.query_4;

import com.hazelcast.mapreduce.Collator;

import java.util.*;

/**
 * Created by Enzo on 15.11.15.
 */
public class DirectorActorCollator implements Collator<Map.Entry<String, ArrayList<ActorWrapper>>, Map<String, ArrayList<ActorWrapper>>> {

    @Override
    public Map collate(Iterable iterable) {
        HashMap<String, ArrayList<ActorWrapper>> map = new HashMap<String, ArrayList<ActorWrapper>>();
        ArrayList<ActorWrapper> list = null;
        Iterator<Map.Entry> iter = iterable.iterator();
        Map.Entry<String, ArrayList<ActorWrapper>> entry;
        Comparator<ActorWrapper> comparator = new Comparator<ActorWrapper>() {
            @Override
            public int compare(ActorWrapper o1, ActorWrapper o2) {
                return o1.getActorName().compareTo(o2.getActorName());
            }
        };
        while (iter.hasNext()) {
            entry = iter.next();
            list = (ArrayList<ActorWrapper>) entry.getValue();
            list.sort(comparator);
            map.put(entry.getKey(), list);
        }
        return map;
    }
}
