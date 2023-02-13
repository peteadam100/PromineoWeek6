package WarApp;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//
//import java.util.ArrayList;
//import java.util.List;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {

		//can have multiple decks for more rounds and/or more players
		int decks = 1;

//		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		Scanner scanner = new Scanner(System.in);

		// creates game object for all game type actions
		// constructor creates the deck to play with
		GamePlay game = new GamePlay(decks);

		// Ask user for the number of players and their names
		game.SetupPlayers();

		System.out.println();
		System.out.println("=".repeat(60));
		System.out.println("\tTHE BATTLEFIELD IS SET. HERE IS THE SITUATION");
		System.out.println("=".repeat(60));

//		System.out.println();
		System.out.println("size of deck for this war: " + game.GetDeckSize() + " total cards, " + decks + " deck(s)");
		System.out.println("Warriors chosen for battle:");

		game.DrawPlayerHands();

		for (Player player : game.GetPlayers()) {
			System.out.println(
					"\t" + player.getName().toUpperCase() + "\t has " + player.getHand().size() + " cards in hand.");
		}
		
		if (game.GetDeckSize() > 0) {
			System.out.println();
			System.out.print(game.GetLeftOverCards().size());
			System.out.println(" resources that were unable to be allocated over : " + game.GetPlayerCount() + " warriors");
			for (String card : game.GetLeftOverCards()) {
				System.out.println("\t" + card);
			}
		}

		System.out.println("=".repeat(60));
		System.out.println();

		System.out.print("PRESS ENTER TO CONTINUE");
		scanner.nextLine();

		System.out.println("\n\n\n\n\n");

//		for (int i = 0; i < 26; i ++) {
		while (game.GetCardsLeftToPlay() > 0) {
//			try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
////			e.printStackTrace();
//		}
			game.PlayTrick();
			System.out.println("\n\n\n\n");
			System.out.print("PRESS ENTER FOR NEXT ROUND");
			scanner.nextLine();
			System.out.println();
		}

		System.out.println();
		System.out.println("\t" + "!".repeat(40));
		System.out.println("\t\tFINAL STANDINGS");
		System.out.println("\t" + "!".repeat(40));
		game.PrintStandings();

		System.out.println();
		System.out.println("WINNER".repeat(5));
		System.out.println("\t" + game.GetWinner().toUpperCase());
		System.out.println("WINNER".repeat(5));

		scanner.close();


	}

}
