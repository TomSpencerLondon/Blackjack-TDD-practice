package com.jitterted.ebp.blackjack.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

class StubDeck extends Deck {
    private static final Suit DUMMY_SUIT = Suit.HEARTS;
    private final ListIterator<Card> iterator;

    public StubDeck(Rank... ranks) {
        List<Card> cards = new ArrayList<>();
        for (Rank rank : ranks) {
            cards.add(new Card(DUMMY_SUIT, rank));
        }
        this.iterator = cards.listIterator();
    }

    public static StubDeck playerHitsAndGoesBust() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.QUEEN, Rank.JACK,
                            Rank.THREE);
    }

    public static StubDeck playerBeatsDealerUponInitialDeal() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.QUEEN, Rank.JACK);
    }

    public static Deck playerGetsBlackJack() {
        return new StubDeck(Rank.TEN, Rank.FIVE,
                            Rank.ACE, Rank.TEN);
    }

    public static Deck normalStartHand() {
        return new StubDeck(Rank.FIVE, Rank.FIVE,
                            Rank.ACE, Rank.TEN);
    }

    @Override
    public Card draw() {
        return iterator.next();
    }
}
