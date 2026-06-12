package game.minesweeper;

import domain.GameApp;

/**
 * 지뢰찾기를 조립해서 돌려주는 메소드
 */
public final class MinesweeperFactory
{
	private MinesweeperFactory() {}
	
	public static GameApp getGame()
	{
		ICellPrinter printer = new CellPrinter();
		ICellBoard board = new CellBoard();
		
		GameApp app = new Minesweeper(board,printer);
		
		return app;
	}
}
