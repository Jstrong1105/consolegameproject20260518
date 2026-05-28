package game.poker;

import java.util.ArrayList;
import java.util.List;

import game.trump.Card;
import game.trump.CardPrinter;

/**
 * 각각의 사용자가 가지고 있는 카드들을 관리하는 클래스
 */
public class HandCards implements IHandCards
{
	private List<Card> handCards;

	public HandCards(IHandRankEvaluator evaluator)
	{
		this.evaluator = evaluator;
	}
	
	private IHandRankEvaluator evaluator;
	
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
	public void print()
	{
		CardPrinter.printCards(handCards);
	}

	@Override
	public void open(int index)
	{
		handCards.get(index).open();
	}

	@Override
	public HandRank getResult()
	{
		return evaluator.eval(handCards);
	}
	
}
