package com.POD_Final.app;

import com.POD_Final.app.client.CustomJSONParser;
import com.POD_Final.app.client.Movie;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Enzo on 08.11.15.
 */
public class JSONParserTest {

    @Test
    public void testFileIsParsedCorrectly() {
        ClassLoader classLoader = getClass().getClassLoader();
        File testFile = new File(classLoader.getResource("data/imdb-1.json").getFile());

        CustomJSONParser parser = new CustomJSONParser(testFile);
        ArrayList<Movie> movieList = null;
        try {
            movieList = parser.parseJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Movie movie = movieList.get(0);

        Assert.assertEquals(movie.getTitle(), "The Dark Knight Rises");
        Assert.assertEquals(movie.getDirector(), "Christopher Nolan");
        int votes = Integer.valueOf("1030777");
        Assert.assertEquals(movie.getVotes(), votes);
        float metascore = Float.valueOf("78");
        Assert.assertEquals(movie.getMetascore(), metascore, 0.001);
        String[] actors = movie.getActors();
        Assert.assertEquals(actors[0], "Christian Bale");
        Assert.assertEquals(actors[1], "Gary Oldman");
        Assert.assertEquals(actors[2], "Tom Hardy");
        Assert.assertEquals(actors[3], "Joseph Gordon-Levitt");
    }

    @Test
    public void fileWith40EntriesIsParsedCorrectly() {
        ClassLoader classLoader = getClass().getClassLoader();
        File testFile = new File(classLoader.getResource("data/imdb-40.json").getFile());

        CustomJSONParser parser = new CustomJSONParser(testFile);
        ArrayList<Movie> movieList = null;
        try {
            movieList = parser.parseJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Movie movie = movieList.get(movieList.size() - 1);

        Assert.assertEquals(movie.getTitle(), "300: Rise of an Empire");
    }

    @Test
    public void fileWith200EntriesIsParsedCorrectly() {
        ClassLoader classLoader = getClass().getClassLoader();
        File testFile = new File(classLoader.getResource("data/imdb-200.json").getFile());

        CustomJSONParser parser = new CustomJSONParser(testFile);
        ArrayList<Movie> movieList = null;
        try {
            movieList = parser.parseJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Movie movie = movieList.get(197);

        Assert.assertEquals(movieList.size(), 198);
        Assert.assertEquals(movie.getTitle(), "Bananas");

    }
}
