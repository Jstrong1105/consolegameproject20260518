package card;

import java.util.List;

/**
 * 카드 뭉치를 받아서 출력하는 클래스
 */
public class CardPrinter implements ICardPrinter
{
	private static final String BLUE = "\033[94m";
	private static final String RED = "\033[91m";
	private static final String NONE = "\033[0m";
	
	@Override
	public void printCards(List<Card> cardDeck)
	{
		if(cardDeck == null || cardDeck.isEmpty())
		{
			throw new IllegalArgumentException("빈 카드덱 오류");
		}
		
		System.out.println("┌─────┐ ".repeat(cardDeck.size()));
		
		for(Card card : cardDeck)
		{
			if(card.isOpen())
			{
				if(card.isSpade() || card.isClub())
				{
					System.out.printf("│  %s  │ ",BLUE + card.getShape().getShape() + NONE);
				}
				else
				{
					System.out.printf("│  %s  │ ",RED + card.getShape().getShape() + NONE);
				}
			}
			else
			{
				System.out.print("│  ?  │ ");
			}
		}
		
		System.out.println();
		
		for(Card card : cardDeck)
		{
			if(card.isOpen())
			{
				if(card.isSpade() || card.isClub())
				{
					System.out.printf("│  %s  │ ",BLUE + card.getNumber().getShape() + NONE);
				}
				else
				{
					System.out.printf("│  %s  │ ",RED + card.getNumber().getShape() + NONE);
				}
			}
			else
			{
				System.out.print("│  ?  │ ");
			}
		} 
		
		System.out.println();
		
		System.out.print("└─────┘ ".repeat(cardDeck.size()));
	}
}
