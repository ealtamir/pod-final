package com.POD_Final.app.back;

import com.POD_Final.app.back.query_1.ActorVotesQuery;
import com.POD_Final.app.client.Query;

/**
 * Created by Enzo on 09.11.15.
 */
public class QueryFactory {

    static public QueryInterface getQueryObject(Query query) {
        if (query.getQueryNum() == 1) {
            return new ActorVotesQuery(query);
        } else if (query.getQueryNum() == 2) {
            return null;
        } else if (query.getQueryNum() == 3) {
            return null;
        } else {
            return null;
        }
    }
}
