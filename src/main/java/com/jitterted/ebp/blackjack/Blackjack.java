package com.jitterted.ebp.blackjack;

public class Blackjack {
    // Assembling and configuring objects (bootstrap or initialize)
    // Transient
    public static void main(String[] args) {
        final Game game = new Game(); // entity like object
//        com.jitterted.ebp.blackjack.console.ConsoleGame consoleGame = new com.jitterted.ebp.blackjack.console.ConsoleGame(game); // in general: entities aren't passed in to adapters
//        consoleGame.start();
    }
}
