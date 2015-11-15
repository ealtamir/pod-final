package com.POD_Final.app.back.query_4;

/* Helper for the 4th query */
public class ActorWrapper {
    private String actorName;
    private int timesActed;

    public ActorWrapper(String actorName, int timesActed) {
        this.actorName = actorName;
        this.timesActed = timesActed;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        ActorWrapper that = (ActorWrapper) o;

        if(timesActed != that.timesActed) return false;
        if(!actorName.equals(that.actorName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = actorName.hashCode();
        result = 31 * result + timesActed;
        return result;
    }

    public int getTimesActed(){
        return timesActed;
    }
}
