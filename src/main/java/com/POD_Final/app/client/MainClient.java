package com.POD_Final.app.client;

import com.POD_Final.app.back.QueryFactory;
import com.POD_Final.app.back.QueryInterface;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Enzo on 08.11.15.
 */
public class MainClient {

    private static final int QUERY = 0;
    private static final int VALUE = 1;

    private static final String MAP_NAME = "query1";

    public static void main(String[] args) {
        Query query = parseQuery(args);
        if(query == null) {
            return;
        }
        HazelcastInstance client = obtainHazelcastClient();
        CustomJSONParser parser = new CustomJSONParser(query.getDataFilePath());
        IMap<String, Movie> map = null;
        long[] readingTimes = new long[2];
        try {
            map = client.getMap(MAP_NAME);
            readingTimes[0] = System.currentTimeMillis();
            map = parser.parseJSON(map);
            readingTimes[1] = System.currentTimeMillis();
        } catch(IOException e) {
            System.out.println("ERROR: Unable to obtain IMap from Hazelcast.");
            return;
        }

        QueryInterface queryObject = QueryFactory.getQueryObject(query);
        long[] mapReduceTimes = queryObject.executeQuery(map, client);
        printTimestamps(readingTimes, mapReduceTimes);
        System.exit(0);
    }

    private static HazelcastInstance obtainHazelcastClient() {
        Properties properties = new Properties();
        InputStream input = null;

        try {
            input = MainClient.class.getClassLoader().getResourceAsStream("params.properties");
            properties.load(input);
        } catch(Exception e) {
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

        String[] arrayAddresses = addresses.split("[,;]");
        ClientNetworkConfig net = new ClientNetworkConfig();
        net.addAddress(arrayAddresses);
        ccfg.setNetworkConfig(net);

        System.out.println(String.format("Conectándose a la red %s con password [%s]", name, pass));
        HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        System.out.println(client.getCluster());
        return client;
    }

    public static Query parseQuery(String[] args) {
        Query query = new Query();
        String[] parts;
        String paramName, paramVal;
        for(String param : args) {
            parts = param.split("=");
            paramName = parts[QUERY].trim();
            paramVal = parts[VALUE].trim();
            try {
                query.setQueryParam(paramName, paramVal);
            } catch(IllegalArgumentException e) {
                System.out.println("\nSe encontró un problema durante el parseo. Terminando ejecución.");
                query = null;
                break;
            }
        }
        return query;
    }

    private static void printTimestamps(long[] readingTimes, long[] mapReduceTimes) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSSS");

        String str = "\n\n>>>>>>> INICIO DE LECTURA DEL ARCHIVO" + "\n\t" + sdf.format(new Date(readingTimes[0]));
        System.out.println(str);

        str = "\n>>>>>>> FIN DE LECTURA DEL ARCHIVO" + "\n\t" + sdf.format(new Date(readingTimes[1]));
        System.out.println(str);

        str = "\n>>>>>>> INICIO DEL TRABAJO MAP/REDUCE" + "\n\t" + sdf.format(new Date(mapReduceTimes[0]));
        System.out.println(str);

        str = "\n>>>>>>> FIN DEL TRABAJO MAP/REDUCE" + "\n\t" + sdf.format(new Date(mapReduceTimes[1]));
        System.out.println(str);
    }

}
