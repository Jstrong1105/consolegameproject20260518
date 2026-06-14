package main;

import java.util.function.Supplier;

import domain.GameApp;
import game.memorygame.MemoryGameFactory;
import game.minesweeper.MinesweeperFactory;
import game.poker.PokerFactory;

/**
 * 게임 목록 등록 enum
 */
public enum GameList implements GameInfo
{
	MINESWEEPER("지뢰찾기","지뢰를 피하세요!",MinesweeperFactory::getGame)
	, POKER("포커겜블","목표 코인을 달성하세요!",PokerFactory::getGame)
	, MEMORY("메모리게임","같은 카드를 모두 찾으세요!",MemoryGameFactory::getGame)
	;
	
	GameList(String gameName, String gameDescription, Supplier<GameApp> gameMaker)
	{
		this.gameName = gameName;
		this.gameDescription = gameDescription;
		this.gameMaker = gameMaker;
	}
	
	private final String gameName;
	private final String gameDescription;
	private final Supplier<GameApp> gameMaker;
	
	@Override
	public String getGameName()
	{
		return gameName;
	}

	@Override
	public String getGameDescription()
	{
		return gameDescription;
	}

	@Override
	public GameApp getGameApp()
	{
		return gameMaker.get();
	}
}
