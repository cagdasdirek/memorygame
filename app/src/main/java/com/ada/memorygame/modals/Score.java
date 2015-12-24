package com.ada.memorygame.modals;

/**
 * ENUM to provide high score information to Database and Content Provider.
 * Created by Cagdas Direk on 23/12/15.
 */
public enum Score {
    _ID("_id"),
    NAME("username"),
    SCORE("score");

    private final String name;

    Score(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

