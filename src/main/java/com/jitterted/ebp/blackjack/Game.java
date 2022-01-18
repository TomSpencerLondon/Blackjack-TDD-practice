package com.jitterted.ebp.blackjack;

import com.jitterted.ebp.blackjack.console.ConsoleGame;
import com.jitterted.ebp.blackjack.console.ConsoleHand;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

    private final Deck deck;

    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();

    public static void main(String[] args) {
        ConsoleGame.displayWelcomeScreen();
        ConsoleGame.waitForEnterFromUser();

        playGame();

        ConsoleGame.resetScreen();
    }

    public Deck deck() {
        return deck;
    }

    public Hand dealerHand() {
        return dealerHand;
    }

    public Hand playerHand() {
        return playerHand;
    }

    private static void playGame() {
        Game game = new Game();
        game.initialDeal();
        game.play();
    }

    public Game() {
        deck = new Deck();
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
    }

    public void play() {
        ConsoleGame.playerTurn(this);

        dealerTurn();

        displayFinalGameState();

        ConsoleGame.determineOutcome(this);
    }

    private void dealRoundOfCards() {
        // why: players first because this is the rule of Blackjack
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    private void dealerTurn() {
        // Dealer makes its choice automatically based on a simple heuristic (value of hand: <=16 must hit, =>17 must stand)
        if (!playerHand.isBusted()) {
            while (dealerHand.dealerMustDrawCard()) {
                dealerHand.drawFrom(deck);
            }
        }
    }

    private void displayFinalGameState() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        System.out.println(ConsoleHand.cardsAsString(dealerHand));
        System.out.println(" (" + dealerHand.value() + ")");

        System.out.println();
        System.out.println("Player has: ");
        System.out.println(ConsoleHand.cardsAsString(playerHand));
        System.out.println(" (" + playerHand.value() + ")");
    }

}
