package com.POD_Final.app.client;

/**
 * Created by Enzo on 08.11.15.
 */
public class MainClient {

    static private final int QUERY = 0;
    static private final int VALUE = 1;

    public static void main(String[] args) {
        Query query = parseQuery(args);
        if (query == null) {
            return;
        }

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
