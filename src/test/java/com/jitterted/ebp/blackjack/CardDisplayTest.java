package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardDisplayTest {

    @Test
    void displayTenAsString() {
        final Card card = new Card(Suit.CLUBS, Rank.TEN);
        final String display = Card.display(card.rank(), card.suit());
        assertThat(display)
                .isEqualTo("[30m┌─────────┐[1B[11D│10       │[1B[11D│         │[1B[11D│    ♣    │[1B[11D│         │[1B[11D│       10│[1B[11D└─────────┘");
    }

    @Test
    void displayNonTenAsString() {
        final Card card = new Card(Suit.CLUBS, Rank.TEN);
        final String display = Card.display(card.rank(), card.suit());
        assertThat(display)
                .isEqualTo("[30m┌─────────┐[1B[11D│10       │[1B[11D│         │[1B[11D│    ♣    │[1B[11D│         │[1B[11D│       10│[1B[11D└─────────┘");
    }
}