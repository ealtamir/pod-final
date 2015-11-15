package com.POD_Final.app.client;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.hazelcast.core.IMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Enzo on 08.11.15.
 */
public class CustomJSONParser {

    private final File jsonFile;

    public CustomJSONParser(File file) {
        jsonFile = file;
    }

    @SuppressWarnings("deprecation")
    public IMap<String, Movie> parseJSON(IMap<String, Movie> map) throws IOException {
        String field = null;
        Movie movie = null;
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser parser = jsonFactory.createJsonParser(jsonFile);

        // Consumes one token to set the order straight
        parser.nextToken();

        while(parser.nextToken() != JsonToken.END_ARRAY) {
            movie = new Movie();
            while(parser.nextToken() != JsonToken.END_OBJECT) {
                field = parser.getCurrentName().toLowerCase();
                parser.nextToken();
                processField(field, parser, movie);
            }
            map.put(movie.getTitle(), movie);
        }

        parser.close();
        return map;
    }

    @SuppressWarnings("deprecation")
    public ArrayList<Movie> parseJSON() throws IOException {
        String field = null;
        Movie movie = null;
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser parser = jsonFactory.createJsonParser(jsonFile);

        // Consumes one token to set the order straight
        parser.nextToken();

        while(parser.nextToken() != JsonToken.END_ARRAY) {
            movie = new Movie();
            while(parser.nextToken() != JsonToken.END_OBJECT) {
                field = parser.getCurrentName().toLowerCase();
                parser.nextToken();
                processField(field, parser, movie);
            }
            movieList.add(movie);
        }

        parser.close();
        return movieList;
    }

    private void processField(String field, JsonParser parser, Movie movie) throws IOException {

        if(field.equals("title")) {
            movie.setTitle(parser.getText());

        } else if(field.equals("year")) {
            String year = parser.getText();
            try {
                movie.setStartingYear(Integer.valueOf(year));
            } catch(NumberFormatException e) {
                String[] years = year.split("[^0-9]");
                if(years.length == 2) {
                    movie.setStartingYear(Integer.valueOf(years[0]));
                    movie.setEndingYear(Integer.valueOf(years[1]));
                } else {
                    movie.setStartingYear(Integer.valueOf(years[0]));
                    movie.setEndingYear(2015);
                }
            }

        } else if(field.equals("metascore")) {
            if(parser.getText().equals("N/A")) {
                movie.setMetascore(0);
            } else {
                movie.setMetascore(Float.valueOf(parser.getText()));
            }

        } else if(field.equals("director")) {
            movie.setDirector(parser.getText());

        } else if(field.equals("imdbvotes")) {
            if(parser.getText().equals("N/A")) {
                movie.setMetascore(0);
            } else {
                String formattedString = parser.getText().replace(",".toString(), "");
                movie.setVotes(Integer.valueOf(formattedString));
            }

        } else if(field.equals("actors")) {
            movie.setActors(parser.getText().split(", "));

        } else if(field.equals("type")) {
            movie.setType(parser.getText());
        }
    }
}
