package com.POD_Final.app.back;

public interface RemoteQuery {
    public void mostPopularActors(int n);
    public void mostCriticallyAcclaimed(int sinceYear);
    public void mostFrequentPairs();
    public void directorsFavoriteActors();
}
