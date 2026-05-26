package game.trump;

import java.util.List;

/**
 * 카드 뭉치를 받아서 출력하는 클래스
 */
public class CardPrinter
{
	public void printCard(List<Card> cardDeck)
	{
		System.out.println("┌─────┐ ".repeat(cardDeck.size()));
		
		for(Card card : cardDeck)
		{
			System.out.printf("│ %2s  │ ",card.getShape());
		}
		
		System.out.println();
		
		for(Card card : cardDeck)
		{
			System.out.printf("│ %2d  │ ",card.getNumber());
		} 
		
		System.out.println();
		
		System.out.println("└─────┘ ".repeat(cardDeck.size()));
	}
}
