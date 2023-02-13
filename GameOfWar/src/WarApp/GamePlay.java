package WarApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
//import java.util.Scanner;

public class GamePlay {

	private Deck deck;
	private List<Player> players;
	private List<Player> tiedPlayers;
	private int round;
	private int currentValueAtStake;
	private int playerCount;
	private int cardsLeftInHands;

	private final static int MINIMUMPLAYERS = 2;
	private final static int MINIMUMCARDSINHAND = 13;

//Constructor - setup the deck to play with. Shuffle Twice but never thrice	
	public GamePlay(int deckSize) {
		deck = new Deck(deckSize);
		deck.Shuffle();
		deck.Shuffle();
		round = 1;
//		this.deckSize = deckSize;
	}

//Getters/Setters
	public int GetCardsLeftToPlay() {
		return cardsLeftInHands;
	}

	public List<Player> GetPlayers() {
		return players;
	}

	public int GetPlayerCount() {
		return players.size();
	}

	public int GetDeckSize() {
		return deck.getCards().size();
	}

	public List<String> GetLeftOverCards() {
		List<String> cards = new ArrayList<>();
		for (Card card : deck.getCards()) {
			cards.add(card.getName());
		}
		return cards;
	}

//Game functions
	public void SetupPlayers() {
		List<Player> players = new ArrayList<>();
		boolean continueInput = false;

		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

		int playerCount = 0;
		do {
			do {
				try {
					System.out.print("How many players do you want to battle? ");
					playerCount = Integer.parseInt(buffer.readLine());
					continueInput = false;
				} catch (InputMismatchException e) {
					System.out.println("Invalid input.");
					continueInput = true;
				} catch (NumberFormatException e) {
					System.out.println("Invalid input.");
					continueInput = true;
				} catch (IOException e) {
					System.out.println("Invalid input.");
					continueInput = true;
				}
			} while (continueInput);

			// Verify that the number of players is reasonable
			if (playerCount < MINIMUMPLAYERS) {
				System.out.println("You can't go to battle against yourself!! Pick a fight!");
				continueInput = true;
			} else if (playerCount > deck.getCards().size() / MINIMUMCARDSINHAND) {
				System.out.println("This war is too spread out! Consolidate your forces with allies!");
				continueInput = true;
			}
		} while (continueInput);

		for (String name : SetupPlayerNames(playerCount)) {
			players.add(new Player(name));
		}
		this.players = players;
		this.playerCount = this.players.size();
	}

	private List<String> SetupPlayerNames(int count) {
		List<String> strPlayerNames = new ArrayList<>();
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

		for (int i = 1; i <= count; i++) {
			System.out.print("\tEnter the name for Player " + i + ": ");
			Optional<String> enteredName = null;
			try {
				enteredName = Optional.ofNullable(buffer.readLine());
			} catch (IOException e) {
//				e.printStackTrace();
				continue;
			}

			if (enteredName.get().isEmpty()) {
				strPlayerNames.add("Soldier " + i);
			} else {
				strPlayerNames.add(enteredName.get());
			}
		}
//		System.out.println();
//		System.out.println("Warriors chosen for battle:");
//		for (String name : strPlayerNames) {
//			System.out.println("\t" + name);
//		}

		return strPlayerNames;
	}

	public void DrawPlayerHands() {
		int cardsToDraw = GetDeckSize() - (GetDeckSize() % playerCount);
		cardsLeftInHands = cardsToDraw / playerCount;
		do {
			try {
				for (Player player : players) {
					player.Draw(deck);
					cardsToDraw -= 1;
				}
			} catch (Exception e) {
				continue;
			}
		} while (cardsToDraw > 0);
//		} while (deck.getCards().size() > 0);
	}

	public void PlayTrick() {
		PrintRoundStart();
		FlipPlayerCards();
		PrintRoundEnd();
		PrintStandings();
		round += 1;
	}

	private void PrintRoundStart() {
		System.out.println();
		System.out.println("-".repeat(60));
		System.out.println("\t\tStart of Round " + round);
		System.out.println("-".repeat(60));
		System.out.println();
	}

	private void PrintRoundEnd() {
		System.out.println("-".repeat(60));
		System.out.println("\t\tRound " + round + " has concluded");
		System.out.println("-".repeat(60));

	}

	public void PrintStandings() {
		System.out.println();
		System.out.println("\t" + "*".repeat(50));
		System.out.println("\t\t\tSCOREBOARD");
		System.out.println("\t" + "*".repeat(50));
		Collections.sort(players, Comparator.comparingInt(Player::getScore).reversed());
		for (Player player : players) {
			System.out.print("\t\t" + player.getName() + ":");
			System.out.println("\t\t" + player.getScore());
		}
	}

	private void FlipPlayerCards() {
		Map<Player, Card> currentTrick = new HashMap<>();
		Player winner = null;
		currentValueAtStake = 0;

		boolean emptyFinish = false;
		do {
			currentTrick = GetCurrentTrick();
			if (currentTrick == null) {
				PrintDeckEmpty();
				emptyFinish = true;
				break;
			} else {
				winner = CompareFlippedCards(currentTrick);
			}
		} while (winner == null);

		if (!emptyFinish) {
			winner.IncrementScore(currentValueAtStake);
			PrintWinnerInfo(winner);
		}
	}

	private void PrintWinnerInfo(Player winner) {
		System.out.println();
		System.out.println("\tAND THE WINNER IS........");

		System.out.println("\t\t" + winner.getName() + " --- gets " + currentValueAtStake + " points");
	}

	private void PrintDeckEmpty() {
		System.out.println();
		System.out.println("Not enough resources left to finish the war.");
		System.out.println("No one gets any points for this round");
	}

	private Map<Player, Card> GetCurrentTrick() {
		Map<Player, Card> currentTrick = new HashMap<>();

		if (players.get(0).getHand().size() > 0) {
			for (Player player : players) {
				Card playerCard = player.Flip();
				System.out.print("\t" + player.getName() + "'s ");
				playerCard.Describe();
				currentTrick.put(player, playerCard);
			}
			currentValueAtStake += 1;
			cardsLeftInHands -= 1;
			return currentTrick;
		} else {
			return null;
		}
	}

	private Player CompareFlippedCards(Map<Player, Card> trick) {
		int largest = 0;
		Player winner = null;
		boolean isTie = false;
		List<Player> tiedPlayers = new ArrayList<>();
		
		for (Map.Entry<Player, Card> entry : trick.entrySet()) {
			int currentValue = entry.getValue().getValue();
			if (this.tiedPlayers == null || this.tiedPlayers.contains(entry.getKey())) {
				if (currentValue > largest) {
					largest = currentValue;
					winner = entry.getKey();
					isTie = false;
					tiedPlayers.removeAll(tiedPlayers);
					tiedPlayers.add(entry.getKey());
				} else if (currentValue == largest) {
					isTie = true;
					tiedPlayers.add(entry.getKey());
				}
			}
		}
		if (isTie) {
			this.tiedPlayers = tiedPlayers;
			PrintTiedTrickInfo();
			return null;
		} else {
			this.tiedPlayers = null;
			return winner;
		}
	}

	private void PrintTiedTrickInfo() {
		System.out.println();
		System.out.println("\t!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("\t\tTHIS BATTLE FOR ROUND " + round + " TIED. HERE IS THE TIE BREAKER");
		System.out.println("\t\t\t The following players tied");
		for (Player player : tiedPlayers) {
			System.out.println("\t\t\t\t" + player.getName());
		}
		System.out.println("\t\tOther players cannot win the hand, but they must surrender a card");
		System.out.println("\t!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println();
	}

	public String GetWinner() {
//		Collections.sort(players, Comparator.comparingInt(Player::getScore).reversed());
		
		Player winner = null;
		boolean isTie = false;
		List<Player> tiedPlayers = new ArrayList<>();
		int largest = 0;
		 
		for (Player player : players) {
			int currentValue = player.getScore();
			if (currentValue > largest) {
				largest = currentValue;
				winner = player;
				isTie = false;
				tiedPlayers.removeAll(tiedPlayers);
				tiedPlayers.add(player);
			} else if (currentValue == largest) {
				isTie = true;
				tiedPlayers.add(player);
			}
		}
		if (isTie) {
			StringBuilder winners = new StringBuilder();
			
			for (int i = 0; i < tiedPlayers.size(); i++) {
				winners.append(tiedPlayers.get(i).getName());
				if ((i + 1) < tiedPlayers.size()) {
					winners.append(" and ");
				}
			}
			winners.append(" tied\n\tThe game is a draw");
			return winners.toString();
		} else {
			return winner.getName();
		}
	}

	public boolean isDeckEmpty() {
		boolean empty;
		int size = GetDeckSize();
		if (size == 0) {
			empty = true;
		} else {
			empty = false;
		}
		return empty;
	}

}
