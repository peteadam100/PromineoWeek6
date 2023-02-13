package WarApp;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private List<Card> hand = new ArrayList<Card>();
	private int score;
	private String name;
	
	public Player(String name) {
		this.name = name;
		this.score = 0;
	}

//Getters & Setters
	public List<Card> getHand() {
		return hand;
	}

	public void setHand(List<Card> hand) {
		this.hand = hand;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//Main methods of Class
	public void Describe() {
		System.out.print(name);
		System.out.print(" has: " +hand.size() + " cards in their hand");
		System.out.println("\tSCORE: " + score);
	}
	
	public Card Flip() {
		int topCard = hand.size() - 1;
		Card card = hand.get(topCard);
		hand.remove(topCard);
		card.getPartOfDeck().addToDiscardPile(card);
		return card;
	}
	
	public void Draw(Deck deck) {
		hand.add(deck.Draw());
	}
	
	public void IncrementScore(int increment) {
		score += increment;
	}
//	Intended to use for playing multiple rounds
	public void ShufflePlayerHand() {
		
	}

}
