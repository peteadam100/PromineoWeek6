package WarApp;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private List<Card> hand = new ArrayList<Card>();
	private int score;
	private String name;

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
	public String Describe() {
		return null;
	}
	
	public Card Flip() {
		
		return card;
	}
	
	public Card Draw(Deck deck) {
		
		return card;
	}
	
	public void IncrementScore() {
		
	}
	
	public void ShufflePlayerHand() {
		
	}

}
