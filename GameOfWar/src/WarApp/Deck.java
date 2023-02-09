package WarApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {

	private List<Card> cards = new ArrayList<>();
	private String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };

	public Deck() {
		for (String suit : suits) {
			for (int i = 2; i <= 14; i++) {
				Card card = new Card(i, suit);
				this.cards.add(card);
			}
		}

	}

	public void ListCards() {
		System.out.println("Total Cards: " + cards.size());
		for (Card card : cards) {
			card.Describe();
		}

	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

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

}
