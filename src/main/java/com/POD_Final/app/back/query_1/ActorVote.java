package com.POD_Final.app.back.query_1;

/**
 * Created by Enzo on 09.11.15.
 */
public class ActorVote implements Comparable<ActorVote> {

    public final String name;
    public final int votes;

    public ActorVote(String name, int votes) {
        this.name = name;
        this.votes = votes;
    }

    @Override
    public int compareTo(ActorVote o) {
        if (votes < o.votes) {
            return -1;
        } else if (votes > o.votes) {
            return 1;
        }
        return 0;
    }
}
