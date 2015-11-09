package com.POD_Final.app.client;

import com.POD_Final.app.back.ActorsReducer;
import com.POD_Final.app.back.CustomMapper;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.server.ExportException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by Enzo on 08.11.15.
 */
public class MainClient {

    static private final int QUERY = 0;
    static private final int VALUE = 1;

    static private final String MAP_NAME = "query1";

    public static void main(String[] args) {
        Query query = parseQuery(args);
        if (query == null) {
            return;
        }
        HazelcastInstance client = obtainHazelcastClient();
        CustomJSONParser parser = new CustomJSONParser(query.getDataFilePath());
        IMap<String, Movie> map = null;
        try {
            map = parser.parseJSON(client.getMap(MAP_NAME));
        } catch (IOException e) {
            System.out.println("ERROR: Unable to obtain IMap from Hazelcast.");
            e.printStackTrace();
            return;
        }

        JobTracker tracker = client.getJobTracker("default");

        KeyValueSource<String, Movie> source = KeyValueSource.fromMap(map);
        Job<String, Movie> job = tracker.newJob(source);

        ICompletableFuture<Map<String, Integer>> future = job
                .mapper(new CustomMapper())
                .reducer(new ActorsReducer())
                .submit();

        Map<String, Integer> result = null;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            System.out.println("ERROR: Task was interrupted. Aborting...");
            System.exit(1);
        } catch (ExecutionException e) {
            System.out.println("ERROR: There was a problem with the execution of your query. Please try again.");
            e.printStackTrace();
            System.exit(1);
        }

        for (Map.Entry<String, Integer> e : result.entrySet())
        {
            System.out.println(String.format("Actor: %s, votos: %d", e.getKey(), e.getValue() ));
        }
        System.exit(0);
    }

    private static HazelcastInstance obtainHazelcastClient() {
        Properties properties = new Properties();
        InputStream input = null;

        try {
            input = MainClient.class.getClassLoader().getResourceAsStream("params.properties");
            properties.load(input);
        } catch (Exception e) {
            System.out.println("Properties file not found.");
            e.printStackTrace();
            return null;
        }

        // load a properties file
        String name = properties.getProperty("name");
        String pass = properties.getProperty("password");
        String addresses = properties.getProperty("addresses");

        ClientConfig ccfg = new ClientConfig();
        ccfg.getGroupConfig().setName(name).setPassword(pass);

        String[] arrayAddresses= addresses.split("[,;]");
        ClientNetworkConfig net= new ClientNetworkConfig();
        net.addAddress(arrayAddresses);
        ccfg.setNetworkConfig(net);

        System.out.println(String.format("Conectándose a la red %s con password [%s]", name, pass));
        HazelcastInstance client =  HazelcastClient.newHazelcastClient(ccfg);

        System.out.println(client.getCluster());
        return client;
    }

    public static Query parseQuery(String[] args) {
        Query query = new Query();
        String[] parts;
        String paramName, paramVal;
        for (String param : args) {
            parts = param.split("=");
            paramName = parts[QUERY].trim();
            paramVal = parts[VALUE].trim();
            try {
                query.setQueryParam(paramName, paramVal);
            } catch (IllegalArgumentException e) {
                System.out.println("\nSe encontró un problema durante el parseo. Terminando ejecución.");
                query = null;
                break;
            }
        }
        return query;
    }

}
