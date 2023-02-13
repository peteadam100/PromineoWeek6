package WarApp;

import java.util.ArrayList;
import java.util.List;
//import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {

	private List<Card> cards = new ArrayList<>();
	private String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };
	private List<Card> discardPile = new ArrayList<>();
//	private String deckName = "WarDeck";
//
//	public void TestDeckOnCard() {
//		System.out.println("the card is a part of: " + deckName);
//	}

//Constructor
	public Deck(int deckCount) {
		CreateDeck(deckCount);
	}

	private void CreateDeck(int deckCount) {
		for (int a = 0; a < deckCount; a++) {
			for (String suit : suits) {
				for (int i = 2; i <= 14; i++) {
					Card card = new Card(i, suit);
					cards.add(card);
					card.setPartOfDeck(this);
				}
			}
		}
//		System.out.println("Created Deck");
	}

//Getters & Setters
	public List<Card> getDiscardPile() {
		return discardPile;
	}

	public void addToDiscardPile(Card card) {
		discardPile.add(card);
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

//Main methods of Class
	public void Shuffle() {
		List<Card> tempList = new ArrayList<>();

		do {
			int max = cards.size();
			int card = ThreadLocalRandom.current().nextInt(0, max);
			tempList.add(cards.get(card));
			cards.remove(card);
		} while (cards.size() > 0);

		cards = tempList;
//		for (Card card : tempList) {
//			card.Describe();
//		}
	}

	public Card Draw() {
		int topCard = cards.size() - 1;
		Card card = cards.get(topCard);
		cards.remove(topCard);
		return card;
	}

	public void ListCards() {
		System.out.println("Total Cards: " + cards.size());
		for (Card card : cards) {
			card.Describe();
		}

	}

}
