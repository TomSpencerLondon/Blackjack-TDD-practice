package com.jitterted.ebp.blackjack;

import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.stereotype.Controller;
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
}
