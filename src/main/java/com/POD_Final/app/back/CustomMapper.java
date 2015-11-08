package main.java.com.POD_Final.app.back;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class CustomMapper implements Mapper<String, Movie, String, Integer> {
    /* inputKey: nombre de la película */
    /* inputValue: Movie */
    /* outputKey: actor name */
    /* outputValue: movie votes */

    @Override
    public void map(String s, Movie movie, Context<String, Integer> context) {
        for(String actorName : movie.getActors()) {
            context.emit(actorName, movie.getVotes());
        }
    }
}
