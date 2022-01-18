package com.jitterted.ebp.blackjack.domain;

public enum GameOutcome {
    PLAYER_BUSTED("You Busted, so you lose.  💸"),
    DEALER_BUSTED("Dealer went BUST, Player wins! Yay for you!! 💵"),
    PLAYER_WINS("You beat the Dealer! 💵"),
    DRAW("Push: Nobody wins, we'll call it even."),
    PLAYER_LOSES("You lost to the Dealer. 💸"),
    PLAYER_BLACKJACK("You got BLACKJACK! 💵");

    private final String text;

    GameOutcome(final String text) {
        this.text = text;
    }


    public String text() {
        return text;
    }
}
