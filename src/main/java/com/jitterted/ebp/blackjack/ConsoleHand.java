package com.jitterted.ebp.blackjack;

import static org.fusesource.jansi.Ansi.ansi;

import com.jitterted.ebp.blackjack.ConsoleCard;
import com.jitterted.ebp.blackjack.Hand;

import java.util.stream.Collectors;

public class ConsoleHand {
    public static String displayFirstCard(Hand hand) {
        return ConsoleCard.display(hand.firstCard());
    }

    public static String cardsAsString(Hand hand) {
        return hand.cards().stream()
                   .map(ConsoleCard::display)
                   .collect(Collectors.joining(
                            ansi().cursorUp(6).cursorRight(1).toString()));
    }
}
