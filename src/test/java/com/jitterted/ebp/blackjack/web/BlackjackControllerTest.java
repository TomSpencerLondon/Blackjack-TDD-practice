package com.jitterted.ebp.blackjack.web;

import static org.assertj.core.api.Assertions.*;

import com.jitterted.ebp.blackjack.BlackjackController;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import org.junit.jupiter.api.Test;

public class BlackjackControllerTest {
    @Test
    void startGameResultsInTwoCardsDealtToPlayer() {
        Game game = new Game(new Deck());

        final BlackjackController blackjackController = new BlackjackController(game);

        final String redirectPage = blackjackController.startGame();

        assertThat(redirectPage)
                .isEqualTo("redirect:/game");

        assertThat(game.playerHand().cards())
                .hasSize(2);
    }
}
