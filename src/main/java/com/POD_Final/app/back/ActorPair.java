package main.java.com.POD_Final.app.back;

/* Helper for the third query */
public class ActorPair {
    private String actorName1;
    private String actorName2;

    public ActorPair(String actorName1, String actorName2) {
        this.actorName1 = actorName1;
        this.actorName2 = actorName2;
    }

    @Override
    public int hashCode() {
        int result = actorName1.hashCode();
        result = 31 * result + actorName2.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        ActorPair other = (ActorPair) o;
        if(actorName1.equals(other.actorName1) && actorName2.equals(other.actorName2) ||
                actorName1.equals(other.actorName2) && actorName2.equals(other.actorName1))
            return true;

        return false;
    }
}
