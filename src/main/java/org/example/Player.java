package org.example;

public class Player {
    private final String name;
    private int gamesPlayed;

    public Player(String name) {
        this.name = name;
    }

    public void incrementGamesPlayed() { gamesPlayed++; }
}
