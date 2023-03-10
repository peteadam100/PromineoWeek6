package WarApp;

public class Card {

	private int value;
	private String[] royalty = { "Jack", "Queen", "King", "Ace" };
	private String name;
	private Deck partOfDeck;

	public Card(int value, String suit) {
		this.value = value;
		if (value > 10) {
			this.name = royalty[this.value - 11] + " of " + suit;
		} else {
			this.name = Integer.toString(this.value) + " of " + suit;
		}
	}

	public Deck getPartOfDeck() {
		return partOfDeck;
	}

	public void setPartOfDeck(Deck partOfDeck) {
		this.partOfDeck = partOfDeck;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public void Describe() {
		System.out.println("Card is: " + this.name);
	}

}
