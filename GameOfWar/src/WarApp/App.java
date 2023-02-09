package WarApp;

public class App {

	public static void main(String[] args) {
		Deck deckOne = new Deck();
		deckOne.ListCards();
		System.out.println();
		System.out.println();
		deckOne.Shuffle();
		System.out.println();
		System.out.println();
		deckOne.ListCards();
		System.out.println();
		System.out.println();
		deckOne.Shuffle();
	}

}
