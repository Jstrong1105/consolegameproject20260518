package game.poker;

import card.CardDeck;
import card.CardPrinter;
import card.ICardDeck;
import card.ICardPrinter;
import domain.GameApp;

/**
 * 포커 클래스를 조립해서 반환하는 클래스
 */
public final class PokerFactory
{
	private PokerFactory() {}
	
	public static GameApp getGame()
	{
		IHandCards playerCard = new HandCards();
		IHandCards cpuCard = new HandCards();
		IHandRankEvaluator evaluator = new HandRankEvaluator();
		ICardPrinter printer = new CardPrinter();
		ICardDeck deck = new CardDeck();
		
		GameApp app = new Poker(playerCard, cpuCard, evaluator, printer, deck);
		
		return app;
	}
}
