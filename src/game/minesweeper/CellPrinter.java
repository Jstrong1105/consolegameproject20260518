package game.minesweeper;

import common.ConsoleUtil;

/**
 * ICellPrinter 를 구현한 프린터
 */
class CellPrinter implements ICellPrinter 
{
	private static final String GREEN = "\033[92m";
	private static final String BLUE = "\033[94m";
	private static final String RED = "\033[91m";
	private static final String NONE = "\033[0m";
	
	private static final String[] OPEN_SHAPE = {" □ ",GREEN + " ① " + NONE
			,BLUE + " ② " + NONE,RED + " ③ " + NONE
			,RED + " ④ " + NONE,RED + " ⑤ " + NONE
			,RED + " ⑥ " + NONE,RED + " ⑦ " + NONE
			,RED + " ⑧ " + NONE};
	private static final String CLOSED_SHAPE = " ■ ";
	private static final String MINE_SHAPE = " ※ ";
	private static final String FLAG_SHAPE = " P ";
	private static final String ERR_SHAPE = " ＃ ";
	
	@Override
	public void print(Cell[][] board)
	{
		if(board == null || board.length <= 0)
			throw new IllegalArgumentException("유효하지 않은 보드판입니다.");
		
		int size = board.length;
		
		ConsoleUtil.clear();
		// 화면 정리
		
		
		// ========== 상단 출력 ============
		System.out.print("==".repeat(size));
		
		System.out.print("지뢰 찾기");
		
		System.out.println("==".repeat(size));
		
		System.out.println();
		
		System.out.print("      ");
		// ========== 상단 출력 ============
		
		// ========== 상단 열 번호 출력 ============
		for (int i = 0; i < size; i++)
			System.out.printf("%2d ",i+1);
		
		System.out.println();
		
		System.out.print("      ");
		System.out.print("───".repeat(size));
		
		System.out.println();
		// ========== 상단 열 번호 출력 ============
		
		// ========== 보드 판 출력 ============
		for(int row = 0; row < size; row++)
		{
			System.out.printf(" %2d │ ",row+1);
			
			for(int col = 0; col < size; col++)
			{
				String str = "";
				
				if(board[row][col].isClosed())
					str = CLOSED_SHAPE;
				else if(board[row][col].isFlag())
					str = FLAG_SHAPE;
				else if(board[row][col].isOpen())
				{
					if(board[row][col].isMine())
						str = MINE_SHAPE;
					else
						str = OPEN_SHAPE[board[row][col].getAdjacentMines()];
				}
				else
					str = ERR_SHAPE;
				
				if(board[row][col].isChoice())
					str = "\033[92;103m" + str + "\033[0m";
				
				System.out.printf("%s",str);
			}
			
			System.out.printf(" │ %2d",row+1);
			
			System.out.println();
		}
		// ========== 보드 판 출력 ============
		
		// ========== 보드 하단 출력 ============
		System.out.print("      ");
		System.out.print("───".repeat(size));
		
		System.out.println();
		
		System.out.print("      ");
		// ========== 보드 하단 출력 ============
		
		// ========== 보드 하단 번호 출력 ============
		for (int i = 0; i < size; i++)
			System.out.printf("%2d ",i+1);
		// ========== 보드 하단 번호 출력 ============
		
		System.out.println();
		System.out.println();
	}
}
