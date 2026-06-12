package game.poker;

import java.util.ArrayList;
import java.util.List;

import card.Card;

/**
 * 각각의 사용자가 가지고 있는 카드들을 관리하는 클래스
 */
class HandCards implements IHandCards
{
	private List<Card> handCards;

	@Override
	public void init()
	{
		handCards = new ArrayList<>();
	}

	@Override
	public void draw(Card card)
	{
		handCards.add(card);
	}

	@Override
	public int count()
	{
		return handCards.size();
	}

	@Override
	public List<Card> getCard()
	{
		return handCards;
	}

	@Override
	public void open(int index)
	{
		handCards.get(index).open();
	}
}
