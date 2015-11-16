package com.POD_Final.app.back.query_3;

import java.util.List;

/* helper bean class */
public class ActorPairWithMovies {
    private ActorPair actorPair;
    private List<String> movies;

    public ActorPairWithMovies(ActorPair actorPair, List<String> movies) {
        this.actorPair = actorPair;
        this.movies = movies;
    }

    public ActorPair getActorPair() {
        return actorPair;
    }

    public List<String> getMovies() {
        return movies;
    }
}
