package com.POD_Final.app;

import com.POD_Final.app.client.MainClient;
import com.POD_Final.app.client.Query;
import junit.framework.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by Enzo on 08.11.15.
 */
public class QueryTest {

    @Test
    public void testGoodParamsAreParsedCorrectly() {
        Query query = new Query();

        query.setQueryParam("query", "1");
        Assert.assertEquals(query.getQueryNum(), 1);

        query.setQueryParam("tope", "1998");
        Assert.assertEquals(query.getTope(), 1998);

        query.setQueryParam("N", "70000");
        Assert.assertEquals(query.getCantActores(), 70000);

        ClassLoader classLoader = getClass().getClassLoader();
        File testFile = new File(classLoader.getResource("data/imdb-40.json").getFile());
        query.setQueryParam("path", testFile.getAbsolutePath());
        Assert.assertEquals(query.getDataFilePath().getPath(), testFile.getPath());
    }

    @Test
    public void testThatArrayOfParamsGetsParsed() {
        String[] args = {"query=2", "tope=1996", "N=10000"};
        Query query = MainClient.parseQuery(args);
        Assert.assertEquals(query.getQueryNum(), 2);
        Assert.assertEquals(query.getTope(), 1996);
        Assert.assertEquals(query.getCantActores(), 10000);
    }
}
