package main;

import game.minesweeper.Minesweeper;

/**
 * 프로그램 진입점
 */
public class Main
{
	public static void main(String[] args)
	{
		Minesweeper mine = new Minesweeper();
		
		mine.run();
	}
}
