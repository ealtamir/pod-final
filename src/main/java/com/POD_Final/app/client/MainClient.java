package com.POD_Final.app.client;

import com.POD_Final.app.back.CustomMapper;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.server.ExportException;
import java.util.Properties;

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
        try {
            IMap<String, Movie> map = parser.parseJSON(client.getMap(MAP_NAME));
        } catch (IOException e) {
            System.out.println("ERROR: Unable to obtain IMap from Hazelcast.");
            e.printStackTrace();
            return;
        }

        JobTracker tracker = client.getJobTracker("default");

        KeyValueSource<String, Movie> source = KeyValueSource.fromMap(map);
        Job<String, Movie> job = tracker.newJob(source);
//
//        ICompletableFuture<Map<String, FormulaTupla>> future = job
//                .mapper(new CustomMapper())
//                .reducer(new Reducer_5())
//                .submit();
//
//        // Tomar resultado e Imprimirlo
//        Map<String, FormulaTupla> rta = future.get();
//
//        for (Entry<String, FormulaTupla> e : rta.entrySet())
//        {
//            System.out.println(String.format("Distrito %s => Ganador %s",
//                    e.getKey(), e.getValue() ));
//        }
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
