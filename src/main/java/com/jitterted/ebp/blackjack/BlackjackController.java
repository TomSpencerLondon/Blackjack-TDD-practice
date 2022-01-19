package com.jitterted.ebp.blackjack;

import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlackjackController {

    private Game game;

    public BlackjackController(Game game) {
        this.game = game;
    }

    @PostMapping("/start-game")
    public String startGame() {
        // gameService.currentGame().initialDeal()
        game.initialDeal();
        return "redirect:/game";
    }

    @GetMapping("/game")
    public String gameView(Model model) {
        GameView gameView = GameView.of(game);
        model.addAttribute("gameView", gameView);
        return "blackjack";
    }

    @PostMapping("/hit")
    public String hitCommand() {
        game.playerHits();
        if (game.isPlayerDone()) {
            return "redirect:/done";
        }
        return "redirect:/game";
    }

    @GetMapping("/done")
    public String doneView(Model model) {
        GameView gameView = GameView.of(game);
        model.addAttribute("gameView", gameView);

        model.addAttribute("outcome", game.determineOutcome().toString());

        return "done";
    }
}
