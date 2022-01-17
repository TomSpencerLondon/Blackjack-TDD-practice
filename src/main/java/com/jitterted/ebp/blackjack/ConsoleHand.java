package com.jitterted.ebp.blackjack;

import com.jitterted.ebp.blackjack.ConsoleCard;
import com.jitterted.ebp.blackjack.Hand;

public class ConsoleHand {
    public static String displayFirstCard(Hand hand) {
        return ConsoleCard.display(hand.firstCard());
    }
}
