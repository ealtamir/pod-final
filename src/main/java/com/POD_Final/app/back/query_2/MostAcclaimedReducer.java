package com.POD_Final.app.back.query_2;

import com.POD_Final.app.client.Movie;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.ArrayList;

/* Reducer for the 2nd query */
public class MostAcclaimedReducer implements ReducerFactory<Integer, Movie, ArrayList<Movie>> {

    @Override
    public Reducer<Movie, ArrayList<Movie>> newReducer(final Integer year) {
        return new Reducer<Movie, ArrayList<Movie>>() {
            private ArrayList<Movie> mostAcclaimedMovies;

            public void beginReduce() {
                mostAcclaimedMovies = new ArrayList<Movie>();
            }

            @Override
            public void reduce(Movie movie) {
                if(mostAcclaimedMovies.isEmpty()){
                    mostAcclaimedMovies.add(movie);
                    return;
                }

                float highestMetascore = mostAcclaimedMovies.get(0).getMetascore();
                if(highestMetascore < movie.getMetascore()) {
                    mostAcclaimedMovies.clear();
                    mostAcclaimedMovies.add(movie);
                } else if(highestMetascore == movie.getMetascore()) {
                    mostAcclaimedMovies.add(movie);
                }
            }

            @Override
            public ArrayList<Movie> finalizeReduce() {
                return mostAcclaimedMovies;
            }
        };
    }
}
