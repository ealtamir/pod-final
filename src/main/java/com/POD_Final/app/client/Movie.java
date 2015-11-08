package com.POD_Final.app.client;

/**
 * Created by Enzo on 08.11.15.
 */
public class Movie {

    static enum Type {
        MOVIE, SERIES
    };

    private String title = null;
    private String director = null;
    private String[] actors = null;
    private int startingYear = -1;
    private int endingYear = -1;
    private float metascore = -1;
    private int votes = -1;
    private Type type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String[] getActors() {
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }

    public int getStartingYear() {
        return startingYear;
    }

    public void setStartingYear(int year) {
        this.startingYear = year;
    }

    public int getEndingYear() {
        return endingYear;
    }

    public void setEndingYear(int endingYear) {
        this.endingYear = endingYear;
    }

    public float getMetascore() {
        return metascore;
    }

    public void setMetascore(float metascore) {
        this.metascore = metascore;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Type getType() {
        return type;
    }

    public void setType(String type) {
        if (type.toLowerCase().equals("movie")) {
            this.type = Type.MOVIE;
        } else {
            this.type = Type.SERIES;
        }
    }
}
