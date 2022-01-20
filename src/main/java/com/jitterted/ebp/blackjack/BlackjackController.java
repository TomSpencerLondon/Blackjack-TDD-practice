package com.jitterted.ebp.blackjack;

import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlackjackController {

    private Game game;

    @Autowired
    public BlackjackController(Game game) {
        this.game = game;
    }

    @PostMapping("/start-game")
    public String startGame() {
        // gameService.currentGame().initialDeal()
        game.initialDeal();
        return redirectBasedOnGameState();
    }

    @GetMapping("/game")
    public String gameView(Model model) {
        populateGameView(model);
        return "blackjack";
    }

    @PostMapping("/hit")
    public String hitCommand() {
        game.playerHits();
        return redirectBasedOnGameState();
    }

    private String redirectBasedOnGameState() {
        if (game.isPlayerDone()) {
            return "redirect:/done";
        }
        return "redirect:/game";
    }

    @GetMapping("/done")
    public String doneView(Model model) {
        populateGameView(model);

        model.addAttribute("outcome", game.determineOutcome().text());

        return "done";
    }

    private void populateGameView(Model model) {
        GameView gameView = GameView.of(game);
        model.addAttribute("gameView", gameView);
    }

    @PostMapping("/stand")
    public String standCommand() {
        game.playerStands();
        game.dealerTurn();
        return "redirect:/done";
    }
}
