package com.jitterted.ebp.blackjack.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameOutcomeTest {
    @Test
    public void playerBeatsDealer() {
        Deck stubDeck = StubDeck.playerBeatsDealerUponInitialDeal();
        Game game = new Game(stubDeck);
        game.initialDeal();

        game.playerStands(); // make sure the player stands
        game.dealerTurn(); // dealer needs to take its turn

        GameOutcome outcome = game.determineOutcome();
        assertThat(outcome)
                .isEqualTo(GameOutcome.PLAYER_WINS);
    }

    @Test
    void playerHitsAndGoesBustAndLoses() {
        Deck stubDeck = StubDeck.playerHitsAndGoesBust();
        Game game = new Game(stubDeck);

        game.initialDeal();

        game.playerHits();

        assertThat(game.determineOutcome())
                .isEqualTo(GameOutcome.PLAYER_BUSTED);
    }

}