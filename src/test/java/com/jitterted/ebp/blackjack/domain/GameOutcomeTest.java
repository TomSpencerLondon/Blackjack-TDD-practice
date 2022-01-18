package com.jitterted.ebp.blackjack.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameOutcomeTest {
    @Test
    void playerHitsAndGoesBustAndLoses() {
        Deck stubDeck = new StubDeck(/* predictable set of cards for this scenario */);
        Game game = new Game(stubDeck);

        game.initialDeal();

        game.playerHits();

        assertThat(game.isPlayerDone())
                .isEqualTo(true);
    }

    @Test
    public void playerBeatsDealer() {
        Deck stubDeck = new StubDeck(/* predictable set of cards for this scenario */);
        Game game = new Game(stubDeck);
        game.initialDeal();

        game.playerStands(); // make sure the player stands
        game.dealerTurn(); // dealer needs to take its turn

        String outcome = game.determineOutcome().text();
        assertThat(outcome)
                .isEqualTo("Dealer went BUST, Player wins! Yay for you!! 💵");
    }

    private class StubDeck extends Deck {
        public Card draw() {
            return new Card(Suit.CLUBS, Rank.TEN);
        }
    }
}