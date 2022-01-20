package com.jitterted.ebp.blackjack.web;

import static org.assertj.core.api.Assertions.*;

import com.jitterted.ebp.blackjack.BlackjackController;
import com.jitterted.ebp.blackjack.GameView;
import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.StubDeck;

import com.jitterted.ebp.blackjack.domain.Suit;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

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


    @Test
    public void gameViewPopulatesViewModelWithSnapshotOfGameState() throws Exception {
        Deck stubDeck = new StubDeck(List.of(new Card(Suit.DIAMONDS, Rank.TEN),
                                             new Card(Suit.HEARTS, Rank.TWO),
                                             new Card(Suit.DIAMONDS, Rank.KING),
                                             new Card(Suit.CLUBS, Rank.THREE)));
        Game game = new Game(stubDeck);
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        Model model = new ConcurrentModel();
        blackjackController.gameView(model);

        GameView gameView = (GameView) model.getAttribute("gameView");

        assertThat(gameView.getPlayerCards())
                .containsExactly("10♦", "K♦");

        assertThat(gameView.getDealerCards())
                .containsExactly("2♥", "3♣");
    }

    @Test
    public void hitCommandDealsAdditionalCardToPlayer() throws Exception {
        Game game = new Game();
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        blackjackController.hitCommand();

        assertThat(game.playerHand().cards())
                .hasSize(3);
    }

    @Test
    void hitCommandResultsInPlayerHavingThreeCardsAndPlayerIsNotDone() {
        final StubDeck stubDeck = StubDeck.playerNotDealtBlackjackHitsAndDoesNotGoBust();
        final Game game = new Game(stubDeck);
        final BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        final String redirectPage = blackjackController.hitCommand();

        assertThat(redirectPage)
                .isEqualTo("redirect:/game");

        assertThat(game.playerHand().cards())
                .hasSize(3);

        assertThat(game.isPlayerDone())
                .isFalse();
    }

    @Test
    void hitCommandResultsInPlayerHavingThreeCardsAndPlayerGoesBustRedirectsToDonePage() {
        final StubDeck stubDeck = StubDeck.playerNotDealtBlackjackHitsAndGoesBust();
        final Game game = new Game(stubDeck);
        final BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        final String redirectPage = blackjackController.hitCommand();

        assertThat(redirectPage)
                .isEqualTo("redirect:/done");

        assertThat(game.isPlayerDone())
                .isTrue();
    }

    @Test
    public void donePageShowsFinalGameViewWithOutcome() throws Exception {
        Game game = new Game(new Deck());
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        Model model = new ConcurrentModel();
        blackjackController.doneView(model);

        assertThat(model.containsAttribute("gameView"))
                .isTrue();

        String outcome = (String) model.getAttribute("outcome");

        assertThat(outcome)
                .isNotBlank();
    }

    @Test
    public void standResultsInGamePlayerIsDone() throws Exception {
        Game game = new Game();
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        blackjackController.standCommand();

        assertThat(game.isPlayerDone())
                .isTrue();
    }

    @Test
    void standResultsInDealerDrawingCardOnTheirTurn() {
        final StubDeck dealerBeatsPlayerAfterDrawingAdditionalCardDeck = new StubDeck(Rank.TEN, Rank.QUEEN,
                                               Rank.NINE, Rank.FIVE,
                                               Rank.SIX);

        final Game game = new Game(dealerBeatsPlayerAfterDrawingAdditionalCardDeck);
        final BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        blackjackController.standCommand();

        assertThat(game.dealerHand().cards())
                .hasSize(3);
    }
}
