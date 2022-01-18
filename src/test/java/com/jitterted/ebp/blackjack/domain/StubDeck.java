package com.jitterted.ebp.blackjack.domain;

class StubDeck extends Deck {
    public Card draw() {
        return new Card(Suit.CLUBS, Rank.TEN);
    }
}
