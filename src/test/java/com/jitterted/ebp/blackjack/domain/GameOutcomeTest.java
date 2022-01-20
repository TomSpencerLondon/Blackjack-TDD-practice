package com.jitterted.ebp.blackjack.domain;

import static org.assertj.core.api.Assertions.*;

import com.jitterted.ebp.blackjack.BlackjackController;
import org.junit.jupiter.api.Test;

class GameOutcomeTest {
    @Test
    public void playerBeatsDealer() {
        Deck stubDeck = StubDeck.playerBeatsDealerUponInitialDeal();
        Game game = new Game(stubDeck);
        game.initialDeal();

        game.playerStands(); // make sure the player stands

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

    @Test
    void playerDealtBlackjackUponInitialDealWinsAndIsDone() {
        Deck stubDeck = StubDeck.playerGetsBlackJack();
        final Game game = new Game(stubDeck);

        game.initialDeal();

        assertThat(game.determineOutcome())
                .isEqualTo(GameOutcome.PLAYER_BLACKJACK);

        assertThat(game.isPlayerDone())
                .isTrue();
    }

    @Test
    void normalStartHandIsNotDone() {
        Deck stubDeck = StubDeck.normalStartHand();
        final Game game = new Game(stubDeck);

        game.initialDeal();
        assertThat(game.isPlayerDone())
                .isFalse();
    }

    @Test
    void standResultsInDealerDrawingCardOnTheirTurn() {
        final StubDeck dealerBeatsPlayerAfterDrawingAdditionalCardDeck = new StubDeck(Rank.TEN, Rank.QUEEN,
                                                                                      Rank.NINE, Rank.FIVE,
                                                                                      Rank.SIX);

        final Game game = new Game(dealerBeatsPlayerAfterDrawingAdditionalCardDeck);
        game.initialDeal();
        game.playerStands();

        assertThat(game.dealerHand().cards())
                .hasSize(3);
    }
}