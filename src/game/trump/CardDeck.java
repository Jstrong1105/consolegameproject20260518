package game.trump;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 카드덱 클래스
 */
public class CardDeck
{
	private List<Card> cards;
	
	// 생성자
	public CardDeck()
	{
		init();
	}
	
	// 덱 초기화
	// 임의로 초기화 가능
	public void init()
	{
		cards = new ArrayList<>();
		
		for(int i = 2; i <= 14; i++)
		{
			for(CardShape shape : CardShape.values())
			{
				cards.add(new Card(shape,i));
			}
		}
		
		Collections.shuffle(cards);
	}
	
	// 카드 한장 주기
	public Card drawCard()
	{
		if(cards.isEmpty())
		{
			throw new IllegalStateException("카드 덱이 비어있습니다.");
		}
		
		return cards.remove(cards.size()-1);
	}
}	
