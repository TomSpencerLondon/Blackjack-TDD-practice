package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.adapter.in.console.ConsoleGame;

public class Blackjack {
    // Assembling and configuring objects (bootstrap or initialize)
    // Transient
    public static void main(String[] args) {
        final Game game = new Game(new Deck()); // entity like object
        ConsoleGame consoleGame = new ConsoleGame(game); // in general: entities aren't passed in to adapters
        consoleGame.start();
    }
}
