package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.domain.port.GameMonitor;

public class Game {

    private final Deck deck;
    private final GameMonitor gameMonitor;

    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();
    private boolean playerDone;

    public Game() {
        this(new Deck());
    }

    public Game(Deck deck) {
        this.deck = deck;
        this.gameMonitor = game -> {};
    }

    public Game(Deck deck, GameMonitor gameMonitor) {
        this.deck = deck;
        this.gameMonitor = gameMonitor;
    }

    public Deck deck() {
        return deck;
    }

    public Hand dealerHand() {
        return dealerHand;
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
        updatePlayerDone(playerHand.isBlackjack());
    }

    public void playerHits() {
        playerHand.drawFrom(deck);
        updatePlayerDone(playerHand.isBusted());
    }

    public void playerStands() {
        dealerTurn();
        updatePlayerDone(true);
    }

    private void updatePlayerDone(boolean isPlayerDone) {
        playerDone = isPlayerDone;
        if (playerDone) {
            gameMonitor.roundCompleted(this);
        }
    }

    /*
        gameService.currentPlayerStands() {
            // repo.findGame();
            game.playerStands();
            if (game.isPlayerDone()) {
                monitor...
            }

            // repo.saveGame();
        }
     */

    public boolean isPlayerDone() {
        return playerDone;
    }

    public Hand playerHand() {
        return playerHand;
    }

    private void dealRoundOfCards() {
        // why: players first because this is the rule of Blackjack
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    public GameOutcome determineOutcome() {
        // PROTOCOL: throw exception if game isn't over
        if (playerHand().isBusted()) {
            return GameOutcome.PLAYER_BUSTED;
        } else if (playerHand().hasBlackjack()) {
            return GameOutcome.PLAYER_BLACKJACK;
        }else if (dealerHand().isBusted()) {
            return GameOutcome.DEALER_BUSTED;
        } else if (playerHand().beats(dealerHand())) {
            return GameOutcome.PLAYER_WINS;
        } else if (playerHand().pushes(dealerHand())) {
            return GameOutcome.DRAW;
        } else {
            return GameOutcome.PLAYER_LOSES;
        }
    }

    private void dealerTurn() {
        // Dealer makes its choice automatically based on a simple heuristic
        // value of hand: <= 16 must hit, => 17 must stand
        if (!playerHand.isBusted()) {
            while(dealerHand.dealerMustDrawCard()) {
                dealerHand.drawFrom(deck);
            }
        }
    }
}
