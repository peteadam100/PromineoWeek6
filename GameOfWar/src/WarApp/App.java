package WarApp;

import java.util.ArrayList;
import java.util.List;

public class App {
	
	public static void main(String[] args) {
		
		GamePlay game = new GamePlay();

		List<Player> players = new ArrayList<>();
//		players = game.SetupPlayers();

		Deck deck = new Deck(1);
//		deckOne.ListCards();
//		System.out.println();
//		System.out.println();
		deck.Shuffle();
//		System.out.println();
//		System.out.println();
//		deckOne.ListCards();
//		System.out.println();
//		System.out.println();
		deck.Shuffle();
//		deck.ListCards();
//		System.out.println();
//		System.out.println();
//		for (int i = 1; i <= 52; i++) {
//			System.out.println(deckOne.Draw().getName());
//		}

//		players.add(new Player("Sam"));
//		players.add(new Player("Sally"));
//		players.add(new Player("Tom"));
//		players.add(new Player("Timmy"));

		game.DrawPlayerHands(players, deck);

		System.out.println();
		System.out.println();
		for (Player player : players) {
			player.Describe();
		}
		
		System.out.println();
		game.FlipPlayerCards(players);
		
		System.out.println();
		game.FlipPlayerCards(players);
		
		System.out.println();
		game.FlipPlayerCards(players);

		System.out.println("The discard pile: ");
		
		for (Card card : deck.getDiscardPile()) {
			card.Describe();
		}

	}


}
