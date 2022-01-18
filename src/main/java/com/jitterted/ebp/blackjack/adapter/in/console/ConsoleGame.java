package com.jitterted.ebp.blackjack.adapter.in.console;

import static org.fusesource.jansi.Ansi.ansi;

import com.jitterted.ebp.blackjack.domain.Game;

import java.util.Scanner;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

public class ConsoleGame {
    private final Game game;

    public ConsoleGame(Game game) {
        this.game = game;
    }



    public static void resetScreen() {
        System.out.println(ansi().reset());
    }

    public static void waitForEnterFromUser() {
        System.out.println(ansi()
                                   .cursor(3, 1)
                                   .fgBrightBlack().a("Hit [ENTER] to start..."));

        System.console().readLine();
    }

    public static void displayWelcomeScreen() {
        AnsiConsole.systemInstall();
        System.out.println(ansi()
                                   .bgBright(Ansi.Color.WHITE)
                                   .eraseScreen()
                                   .cursor(1, 1)
                                   .fgGreen().a("Welcome to")
                                   .fgRed().a(" JitterTed's")
                                   .fgBlack().a(" BlackJack game"));
    }

    public void displayGameState(Game game) {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        System.out.println(ConsoleHand.displayFirstCard(game.dealerHand())); // first card is Face Up

        // second card is the Dealer's hole card, which is hidden
        ConsoleCard.displayBackOfCard();

        System.out.println();
        System.out.println("Player has: ");
        System.out.println(ConsoleHand.cardsAsString(game.playerHand()));
        System.out.println(" (" + game.playerHand().value() + ")");
    }

    public void playerTurn() {
        // get Player's decision: hit until they stand, then they're done (or they go bust)

        while (!game.playerHand().isBusted()) {
            displayGameState(game);
            String playerChoice = inputFromPlayer().toLowerCase();
            if (playerChoice.startsWith("s")) {
                break;
            }
            if (playerChoice.startsWith("h")) {
                game.playerHand().drawFrom(game.deck());
                if (game.playerHand().isBusted()) {
                    return;
                }
            } else {
                System.out.println("You need to [H]it or [S]tand");
            }
        }
    }

    public static String inputFromPlayer() {
        System.out.println("[H]it or [S]tand?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void displayFinalGameState() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        System.out.println(ConsoleHand.cardsAsString(game.dealerHand()));
        System.out.println(" (" + game.dealerHand().value() + ")");

        System.out.println();
        System.out.println("Player has: ");
        System.out.println(ConsoleHand.cardsAsString(game.playerHand()));
        System.out.println(" (" + game.playerHand().value() + ")");
    }

    public void dealerTurn() {
        // Dealer makes its choice automatically based on a simple heuristic (value of hand: <=16 must hit, =>17 must stand)
        if (!game.playerHand().isBusted()) {
            while (game.dealerHand().dealerMustDrawCard()) {
                game.dealerHand().drawFrom(game.deck());
            }
        }
    }

    public void start() {
        ConsoleGame.displayWelcomeScreen();
        ConsoleGame.waitForEnterFromUser();

        game.initialDeal();
        playerTurn();

        dealerTurn();
        displayFinalGameState();
        System.out.println(game.determineOutcome());
        resetScreen();
    }
}
