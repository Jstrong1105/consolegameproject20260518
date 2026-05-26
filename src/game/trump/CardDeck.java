package game.trump;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 카드덱 클래스
 */
public class CardDeck
{
	private List<Card> cardDeck;
	
	// 생성자
	public CardDeck()
	{
		init();
	}
	
	// 덱 초기화
	public void init()
	{
		cardDeck = new ArrayList<>();
		
		for(int i = 2; i <= 14; i++)
		{
			for(CardShape shape : CardShape.values())
			{
				cardDeck.add(new Card(shape,i));
			}
		}
		
		Collections.shuffle(cardDeck);
	}
	
	// 카드 한장 주기
	public Card drawCard()
	{
		if(cardDeck.isEmpty())
		{
			throw new IllegalStateException("카드 덱이 비어있습니다.");
		}
		
		return cardDeck.remove(cardDeck.size()-1);
	}
}	
