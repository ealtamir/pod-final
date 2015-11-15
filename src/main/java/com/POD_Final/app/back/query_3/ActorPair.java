package com.POD_Final.app.back.query_3;

import java.io.Serializable;

/* Helper for the 3rd query */
public class ActorPair implements Serializable {
    private String actorName1;
    private String actorName2;

    public ActorPair(String actorName1, String actorName2) {
        /* Enforce tuple order for equals() and hashCode() consistency */
        if(actorName1.compareTo(actorName2) <= 0){
            this.actorName1 = actorName1;
            this.actorName2 = actorName2;
        } else {
            this.actorName1 = actorName2;
            this.actorName2 = actorName1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        ActorPair actorPair = (ActorPair) o;

        if(!actorName1.equals(actorPair.actorName1)) return false;
        if(!actorName2.equals(actorPair.actorName2)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = actorName1.hashCode();
        result = 31 * result + actorName2.hashCode();
        return result;
    }

    public String getFirstActorName() {
        return actorName1;
    }

    public String getSecondActorName() {
        return actorName2;
    }
}
