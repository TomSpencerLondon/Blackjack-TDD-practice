package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.domain.port.GameMonitor;

public class Game {

    private final Deck deck;
    private GameMonitor gameMonitor;

    private Hand dealerHand = new Hand();
    private Hand playerHand = new Hand();
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

    public void playerHits() {
        playerHand.drawFrom(deck);
        playerDone = playerHand.isBusted();

        if (playerDone) {
            gameMonitor.roundCompleted(this);
        }
    }

    public void playerStands() {
        playerDone = true;
        dealerTurn();
        gameMonitor.roundCompleted(this);
    }

    public boolean isPlayerDone() {
        return playerDone;
    }
    // "Query" rule: SNAPSHOT (point in time), does not allow
    // clients to change internal state (immutable / unmodifiable/ copy)
    // 0 - Hand - is mutable and not snapshot --> *NO*
    // 1 - Deep Copy of Hand - deep clone()
    // 2 - DTO - cards (first card), hand's value --> pure data, "JavaBean", only lives in Adapters
    // 3 - Interface - exposes just the cards and value --> only queries of Hand: ReadOnlyHand
    //          be careful that the interface isn't a "view" on the Hand that can change (*NO*)
    // 4 - Hand Value object ("HandView") - cards, hand's value --> Domain, often just data, domain-meaningful methods
    // 5 - Subclass that throws exception for command methods --> *NO* not nice

    public Hand playerHand() {
        return playerHand;
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();

        if (playerHand.hasBlackjack()) {
            playerDone = true;
        }

        if (playerDone == true) {
            gameMonitor.roundCompleted(this);
        }
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
