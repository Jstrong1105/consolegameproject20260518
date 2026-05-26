package game.trump;

import java.util.List;

/**
 * 카드 뭉치를 받아서 출력하는 클래스
 */
public final class CardPrinter
{
	// 인스턴스 생성 방지
	private CardPrinter() {}
	
	public static void printCards(List<Card> cardDeck)
	{
		if(cardDeck == null || cardDeck.isEmpty())
		{
			throw new IllegalArgumentException("빈 카드덱 오류");
		}
		
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
