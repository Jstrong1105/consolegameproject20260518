package main;

import java.util.ArrayList;
import java.util.List;

import common.MenuUtil;
import game.GameApp;
import game.memorygame.MemoryGame;
import game.minesweeper.Minesweeper;
import game.poker.Poker;

/**
 * 프로그램 진입점
 */
public class Main
{
	public static void main(String[] args)
	{
		List<GameApp> list = new ArrayList<GameApp>();
		list.add(new Minesweeper());
		list.add(new Poker());
		list.add(new MemoryGame());
		
		MenuUtil.showGameList(list);
	}
}
