package WarApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class GamePlay {

	private Deck deck;
	private List<Player> players;
	private int deckSize = 2;
	
	private static int MINIMUMPLAYERS = 2;
	private static int MINIMUMCARDSINHAND = 4;

	public GamePlay() {
		SetupPlayers();
		
		deck = new Deck(deckSize);
	}

	public void DrawPlayerHands(List<Player> players, Deck deck) {
		do {
			try {
				for (Player player : this.players) {
					player.Draw(this.deck);
				}
//				Exception here would be if there are an uneven number of cards for the number of players. 
//				If this happens, just continue dealing until the deck is empty, then stop.
			} catch (Exception e) {
				continue;
			}
		} while (deck.getCards().size() > 0);
	}

	public void PlayTrick() {

	}

	public void FlipPlayerCards(List<Player> players) {
		Map<Player, Card> currentTrick = new HashMap<>();
		Player winner = null;

		for (Player player : players) {
			Card playerCard = player.Flip();
			System.out.print(player.getName() + "'s ");
			playerCard.Describe();
			currentTrick.put(player, playerCard);
		}
		System.out.println();
		for (Player player : players) {
			player.Describe();
		}

		System.out.println("AND THE WINNER IS........");
//		Thread.sleep(1000);
		winner = CompareFlippedCards(currentTrick);
		System.out.println("\t\t" + winner.getName());
		;
		winner.Describe();

	}

	private Player CompareFlippedCards(Map<Player, Card> trick) {
		int largest = 0;
		Player winner = null;
		for (Map.Entry<Player, Card> entry : trick.entrySet()) {
			int currentValue = entry.getValue().getValue();
			if (currentValue > largest) {
				largest = currentValue;
				winner = entry.getKey();
			}
		}
		winner.IncrementScore();
		return winner;
	}

	public List<Player> SetupPlayers() {
		List<Player> players = new ArrayList<>();
		boolean continueInput = false;
		Scanner input = new Scanner(System.in);

		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

		int playerCount = 0;
		do {
			do {
				try {
					System.out.print("How many players do you want to battle? ");
//					playerCount = input.nextInt();
//					playerCount = int(buffer.readLine();
					playerCount = Integer.parseInt(buffer.readLine());
					continueInput = false;
				} catch (InputMismatchException e) {
					System.out.println("Invalid input.");
//					input.nextLine();
					continueInput = true;
				} catch (NumberFormatException e) {
					System.out.println("Invalid input.");
//					System.out.println(playerCount);
//					input.nextLine();
					continueInput = true;
//					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("Invalid input.");
//					input.nextLine();
					continueInput = true;
//					e.printStackTrace();
				}
			} while (continueInput);
//			Verify that the number of players is reasonable
			if (playerCount < MINIMUMPLAYERS) {
				System.out.println("You can't go to battle against yourself!! Pick a fight!");
				continueInput = true;
			} else if (playerCount > MINIMUMCARDSINHAND) {
				System.out.println("This war is too spread out! Consolidate your forces with allies!");
				continueInput = true;
			}
		} while (continueInput);

		continueInput = false;

		for (String name : SetupPlayerNames(playerCount)) {
			players.add(new Player(name));
		}
		this.players = players;
		return players;
	}

	private List<String> SetupPlayerNames(int count) {
		List<String> strPlayerNames = new ArrayList<>();
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

		for (int i = 1; i <= count; i++) {
			System.out.print("\tEnter the name for Player " + i + " ");
			Optional<String> enteredName = null;
			try {
				enteredName = Optional.ofNullable(buffer.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (enteredName.get().isEmpty()) {
				strPlayerNames.add("Soldier " + i);
			} else {
				strPlayerNames.add(enteredName.get());
			}
		}

		for (String name : strPlayerNames) {
			System.out.println("name: " + name);
		}

		return strPlayerNames;
	}
	
	
	

	public void FlipTieBreaker() {

	}

	public void setDeckSize(int deckSize) {
		this.deckSize = deckSize;
	}

}
