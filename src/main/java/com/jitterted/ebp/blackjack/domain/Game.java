package com.jitterted.ebp.blackjack.domain;

public class Game {

    private final Deck deck;

    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();

    public Deck deck() {
        return deck;
    }

    public Hand dealerHand() {
        return dealerHand;
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

    public Game() {
        deck = new Deck();
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
    }

    private void dealRoundOfCards() {
        // why: players first because this is the rule of Blackjack
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

}
