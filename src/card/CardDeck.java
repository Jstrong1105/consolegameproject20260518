package card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 카드덱 클래스
 */
public final class CardDeck implements ICardDeck
{
	private List<Card> cards;
	
	// 생성자
	public CardDeck()
	{
		init();
	}
	
	// 덱 초기화
	// 임의로 초기화 가능
	@Override
	public void init()
	{
		cards = new ArrayList<>();
		
		for(CardShape shape : CardShape.values())
		{
			for(CardNumber number : CardNumber.values())
			{
				cards.add(new Card(shape,number));
			}
		}
		
		Collections.shuffle(cards);
	}
	
	// 카드 한장 주기
	@Override
	public Card drawCard()
	{
		if(cards.isEmpty())
			throw new IllegalStateException("카드 덱이 비어있습니다.");
		
		return cards.remove(cards.size()-1);
	}
}	
