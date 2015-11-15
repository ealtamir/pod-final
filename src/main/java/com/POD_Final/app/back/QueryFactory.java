package com.POD_Final.app.back;

import com.POD_Final.app.back.query_1.ActorVotesQuery;
import com.POD_Final.app.back.query_2.MostAcclaimedQuery;
import com.POD_Final.app.back.query_3.ActorPairQuery;
import com.POD_Final.app.back.query_4.DirectorActorQuery;
import com.POD_Final.app.client.Query;

/**
 * Created by Enzo on 09.11.15.
 */
public class QueryFactory {

    static public QueryInterface getQueryObject(Query query) {
        if (query.getQueryNum() == 1) {
            return new ActorVotesQuery(query);
        } else if (query.getQueryNum() == 2) {
            return new MostAcclaimedQuery(query);
        } else if (query.getQueryNum() == 3) {
            return new ActorPairQuery(query);
        } else {
            return new DirectorActorQuery(query);
        }
    }
}
