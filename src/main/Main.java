package main;

import domain.GameList;

/**
 * 프로그램 진입점
 */
public class Main
{
	public static void main(String[] args)
	{
		MainMenu.showGameList(GameList.values());
	}
}
