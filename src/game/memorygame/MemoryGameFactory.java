package game.memorygame;

import card.CardDeck;
import card.CardPrinter;
import card.ICardDeck;
import card.ICardPrinter;
import domain.GameApp;

/**
 * 메모리 게임 생성 후 반환 클래스
 */
public final class MemoryGameFactory
{
	private MemoryGameFactory() {}
	
	public static GameApp getGame()
	{
		ICardPrinter printer = new CardPrinter();
		ICardDeck cardDeck = new CardDeck();
		IMemoryBoard board = new MemoryBoard(cardDeck);
		GameApp app = new MemoryGame(printer, board);
		
		return app;
	}
}
