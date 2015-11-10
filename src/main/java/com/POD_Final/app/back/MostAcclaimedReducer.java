package com.POD_Final.app.back;

import com.POD_Final.app.client.Movie;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/* Reducer for the 2nd query */
public class MostAcclaimedReducer implements ReducerFactory<Integer, Movie, PriorityQueue<Movie>> {

    @Override
    public Reducer<Movie, PriorityQueue<Movie>> newReducer(final Integer year) {
        return new Reducer<Movie, PriorityQueue<Movie>>() {
            private PriorityQueue<Movie> mostAcclaimedMovies;

            public void beginReduce() {
                mostAcclaimedMovies = new PriorityQueue<Movie>(new Comparator<Movie>() {
                    @Override
                    public int compare(Movie o1, Movie o2) {
                        if(o1.getMetascore() < o2.getMetascore())
                            return -1;
                        if(o1.getMetascore() > o2.getMetascore())
                            return 1;
                        return 0;
                    }
                });
            }

            @Override
            public void reduce(Movie movie) {
                if(mostAcclaimedMovies.isEmpty())
                    mostAcclaimedMovies.add(movie);

                float highestMetascore = mostAcclaimedMovies.peek().getMetascore();
                if(highestMetascore < movie.getMetascore()) {
                    mostAcclaimedMovies.clear();
                    mostAcclaimedMovies.add(movie);
                } else if(highestMetascore == movie.getMetascore()) {
                    mostAcclaimedMovies.add(movie);
                }
            }

            @Override
            public PriorityQueue<Movie> finalizeReduce() {
                return mostAcclaimedMovies;
            }
        };
    }
}
