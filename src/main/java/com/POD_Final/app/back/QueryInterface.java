package com.POD_Final.app.back;

import com.POD_Final.app.client.Movie;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

/**
 * Created by Enzo on 09.11.15.
 */
public interface QueryInterface {

    public long[] executeQuery(IMap<String, Movie> map, HazelcastInstance client);

}
