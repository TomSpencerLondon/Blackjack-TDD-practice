package com.jitterted.ebp.blackjack.domain.port;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.StubDeck;
import org.junit.jupiter.api.Test;

public class GameMonitorTest {

    @Test
    public void playerStandsThenGameIsOverAndResultsSentToMonitor() throws Exception {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(new Deck(), gameMonitorSpy);
        game.initialDeal();

        game.playerStands();
        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }

    @Test
    public void playerHitsAndGoesBustThenGameIsOverAndResultsSentToMonitor() throws Exception {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        final StubDeck stubDeck = StubDeck.playerHitsAndGoesBust();
        Game game = new Game(stubDeck, gameMonitorSpy);
        game.initialDeal();

        game.playerHits();

        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }

    @Test
    public void playerHitsButDoesNotGoBustThenGameIsNotOverAndResultsNotSentToMonitor() throws Exception {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        final StubDeck stubDeck = StubDeck.playerNotDealtBlackjackHitsAndDoesNotGoBust();
        Game game = new Game(stubDeck, gameMonitorSpy);
        game.initialDeal();
        game.playerHits();

        verify(gameMonitorSpy, never()).roundCompleted(any(Game.class));
    }


    @Test
    public void playerGetsBlackjackOnInitialDealThenGameIsNotOverAndResultsNotSentToMonitor() throws Exception {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        final StubDeck stubDeck = StubDeck.playerGetsBlackJack();
        Game game = new Game(stubDeck, gameMonitorSpy);
        game.initialDeal();

        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }
}
