package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HandDisplayTest {
    @Test
    public void displayFirstCard() throws Exception {
        Hand hand = new Hand(List.of(new Card(Suit.HEARTS, Rank.ACE)));

        assertThat(ConsoleHand.displayFirstCard(hand))
                .isEqualTo("[31m┌─────────┐[1B[11D│A        │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│        A│[1B[11D└─────────┘");
    }

    @Test
    void cardsAsStringTransformsWholeHandToDisplayString() {
        Hand hand = new Hand(List.of(new Card(Suit.HEARTS, Rank.ACE), new Card(Suit.CLUBS, Rank.ACE)));

        assertThat(ConsoleHand.cardsAsString(hand))
                .isEqualTo("[31m┌─────────┐[1B[11D│A        │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│        A│[1B[11D└─────────┘[6A[1C[30m┌─────────┐[1B[11D│A        │[1B[11D│         │[1B[11D│    ♣    │[1B[11D│         │[1B[11D│        A│[1B[11D└─────────┘");
    }
}