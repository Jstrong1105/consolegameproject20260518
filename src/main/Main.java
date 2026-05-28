package main;

import java.util.ArrayList;
import java.util.List;

import game.minesweeper.Minesweeper;
import game.poker.Poker;
import game.trump.Card;
import game.trump.CardDeck;
import game.trump.CardPrinter;

/**
 * 프로그램 진입점
 */
public class Main
{
	public static void main(String[] args)
	{
		//Minesweeper mine = new Minesweeper();
		
		//mine.run();
		
		/*
		 * CardDeck cardDeck = new CardDeck();
		 * 
		 * List<Card> deck = new ArrayList<Card>();
		 * 
		 * deck.add(cardDeck.drawCard()); deck.add(cardDeck.drawCard());
		 * deck.add(cardDeck.drawCard()); deck.add(cardDeck.drawCard());
		 * deck.add(cardDeck.drawCard());
		 * 
		 * CardPrinter.printCards(deck);
		 */
		
		Poker poker = new Poker();
		
		poker.run();
		
	}
}
