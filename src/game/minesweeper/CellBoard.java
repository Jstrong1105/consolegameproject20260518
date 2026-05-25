package game.minesweeper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import common.ConsoleUtil;

/**
 * 셀 보드 인터페이스 구현 클래스
 */
public class CellBoard implements ICellBoard
{
	private Cell[][] board;
	private int size;
	private int mineCount;
	private int flagCount;
	private int openCount;

	private static int[] dRow = {-1,-1,-1,0,0,1,1,1};
 	private static int[] dCol = {-1,0,1,-1,1,-1,0,1};
	
	@Override
	public void init(int size, int mineCount)
	{
		this.size = size;
		this.mineCount = mineCount;
		flagCount = 0;
		openCount = 0;
		
		board = new Cell[size][size];
		
		for(int row = 0; row < size; row++)
		{
			for(int col = 0; col < size; col++)
			{
				board[row][col] = new Cell();
			}
		}
		
		int emp = 0;
		
		Random rd = new Random();
		
		while(emp < mineCount)
		{
			int row = rd.nextInt(size);
			int col = rd.nextInt(size);
			
			if(!board[row][col].isMine())
			{
				board[row][col].setMine(true);
				emp++;
			}
		}
	}

	@Override
	public boolean isMine(int row, int col)
	{
		return board[row][col].isMine();
	}

	@Override
	public void openFirst(int row, int col)
	{
		if(board[row][col].isMine())
		{
			Random rd = new Random();
			
			while(true)
			{
				int nRow = rd.nextInt(size);
				int nCol = rd.nextInt(size);
				
				if(!board[nRow][nCol].isMine())
				{
					board[row][col].setMine(false);
					board[nRow][nCol].setMine(true);
					break;
				}
			}
		}
		
		// 첫 오픈 전까지 인접 지뢰 수를 계산하지 않기 때문에
		// 첫 오픈 시 해당 메소드를 반드시 실행해야 한다 
		for(int r = 0; r < size; r++)
		{
			for(int c = 0; c < size; c++)
			{
				if(board[r][c].isMine())
				{
					addAdjacentMines(r, c);
				}
			}
		}
	}
	
	// 지뢰 주변 8칸의 인접 지뢰 수를 처리하는 메소드
	private void addAdjacentMines(int row, int col)
	{
		for(int i = 0; i < dRow.length; i++)
		{
			int nRow = row + dRow[i];
			int nCol = col + dCol[i];
			
			if(nRow < 0 || nCol < 0 || nRow >= size || nCol >= size)
			{
				continue;
			}
			
			board[nRow][nCol].addAdjacentMines();
		}
	}
	
	@Override
	public void openCell(int row, int col)
	{
		if(!board[row][col].isClosed())
		{
			return;
		}
		
		board[row][col].open();
		openCount++;
		
		if(board[row][col].getAdjacentMines() == 0)
		{
			openAdjacentMines(row, col);
		}
	}
	
	// 한칸을 오픈했을 때 인접 지뢰가 없는 칸이라면
	// 주변 8칸을 연쇄적으로 오픈하고 
	// 연쇄적으로 열린 칸도 인접 지뢰가 없는 칸이라면
	// 해당 칸 주변 8칸도 연쇄적으로 오픈됨
	private void openAdjacentMines(int row, int col)
	{
		Queue<int[]> temp = new LinkedList<int[]>();
		
		temp.add(new int[] {row,col});
		
		while(!temp.isEmpty())
		{
			int[] emp = temp.poll();
			
			for(int i = 0; i < dRow.length; i++)
			{
				int nRow = emp[0] + dRow[i];
				int nCol = emp[1] + dCol[i];
				
				if(nRow < 0 || nCol < 0 || nRow >= size || nCol >= size)
				{
					continue;
				}
				
				if(board[nRow][nCol].isClosed())
				{
					board[nRow][nCol].open();
					openCount++;
					
					if(board[nRow][nCol].getAdjacentMines() == 0)
					{
						temp.add(new int[] {nRow, nCol});
					}
				}
			}
		}
	}
	
	@Override
	public void toggleFlagCell(int row, int col)
	{
		if(board[row][col].isClosed())
		{
			board[row][col].toggleFlag();
			flagCount++;
		}
		else if(board[row][col].isFlag())
		{
			board[row][col].toggleFlag();
			flagCount--;
		}
	}

	@Override
	public boolean isClear()
	{
		return openCount >= (size * size - mineCount);
	}

	@Override
	public int getMineCount()
	{
		return mineCount;
	}

	@Override
	public int getFlagCount()
	{
		return flagCount;
	}
	
	private static final String[] OPEN_SHAPE = {" □ ","\033[92m ① \033[0m","\033[94m ② \033[0m","\033[91m ③ \033[0m","\033[91m ④ \033[0m","\033[91m ⑤ \033[0m","\033[91m ⑥ \033[0m","\033[91m ⑦ \033[0m","\033[91m ⑧ \033[0m"};
	private static final String CLOSED_SHAPE = " ■ ";
	private static final String MINE_SHAPE = " ※ ";
	private static final String FLAG_SHAPE = " P ";
	

	@Override
	public void print()
	{
		ConsoleUtil.clear();
		
		System.out.print("==".repeat(size));
		
		System.out.print("지뢰 찾기");
		
		System.out.println("==".repeat(size));
		
		System.out.println();
		
		System.out.print("      ");
		
		for (int i = 0; i < size; i++)
		{
			System.out.printf("%2d ",i+1);
		}
		
		System.out.println();
		
		System.out.print("      ");
		System.out.print("───".repeat(size));
		
		System.out.println();
		
		for(int row = 0; row < size; row++)
		{
			System.out.printf(" %2d │ ",row+1);
			
			for(int col = 0; col < size; col++)
			{
				String str = "";
				
				if(board[row][col].isClosed())
				{
					str = CLOSED_SHAPE;
				}
				else if(board[row][col].isFlag())
				{
					str = FLAG_SHAPE;
				}
				else if(board[row][col].isOpen())
				{
					if(board[row][col].isMine())
					{
						str = MINE_SHAPE;
					}
					else
					{
						str = OPEN_SHAPE[board[row][col].getAdjacentMines()];
					}
				}
				
				if(board[row][col].isChoice())
				{
					str = "\033[92;103m" + str + "\033[0m";
				}
				
				System.out.printf("%s",str);
			}
			
			System.out.printf(" │ %2d",row+1);
			
			System.out.println();
		}
		
		System.out.print("      ");
		System.out.print("───".repeat(size));
		
		System.out.println();
		
		System.out.print("      ");
		
		for (int i = 0; i < size; i++)
		{
			System.out.printf("%2d ",i+1);
		}
		
		System.out.println();
		System.out.println();
	}

	@Override
	public boolean isOpen(int row, int col)
	{
		return board[row][col].isOpen();
	}

	@Override
	public boolean isClosed(int row, int col)
	{
		return board[row][col].isClosed();
	}

	@Override
	public boolean isFlag(int row, int col)
	{
		return board[row][col].isFlag();
	}

	@Override
	public void choiceCell(int row, int col)
	{
		board[row][col].setChoice(true);
	}
	
	@Override
	public void cancelCell(int row, int col)
	{
		board[row][col].setChoice(false);
	}
	
	@Override
	public void finish()
	{
		// 깃발인 셀을 전부 닫힌 셀로 바꿈
		Arrays.stream(board)
		.flatMap(Arrays::stream)
		.filter(cell->cell.isFlag())
		.forEach(cell->cell.toggleFlag());
		
		// 지뢰인 셀을 전부 오픈함
		Arrays.stream(board)
		.flatMap(Arrays::stream)
		.filter(cell->cell.isMine())
		.forEach(cell->cell.open());
	}
}
