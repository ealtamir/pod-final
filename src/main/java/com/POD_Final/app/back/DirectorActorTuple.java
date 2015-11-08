package main.java.com.POD_Final.app.back;

/* Helper for the 4th query */
public class DirectorActorTuple {
    private String directorName;
    private String actorName;

    public DirectorActorTuple(String directorName, String actorName) {
        this.directorName = directorName;
        this.actorName = actorName;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        DirectorActorTuple that = (DirectorActorTuple) o;

        if(!actorName.equals(that.actorName)) return false;
        if(!directorName.equals(that.directorName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = directorName.hashCode();
        result = 31 * result + actorName.hashCode();
        return result;
    }
}
