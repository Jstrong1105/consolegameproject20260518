package game.minesweeper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


/**
 * 셀 보드 인터페이스 구현 클래스
 */
class CellBoard implements ICellBoard
{
	private Cell[][] board;
	private int size;
	private int mineCount;
	private int flagCount;
	private int openCount;
	private Random rd;
	
	private static final int[] D_ROW = {-1,-1,-1,0,0,1,1,1};
 	private static final int[] D_COL = {-1,0,1,-1,1,-1,0,1};
	
	@Override
	public void init(int size, int mineCount)
	{
		this.size = size;
		this.mineCount = mineCount;
		flagCount = 0;
		openCount = 0;
		rd = new Random();
		
		board = new Cell[size][size];
		
		// 보드판 초기화
		for(int row = 0; row < size; row++)
		{
			for(int col = 0; col < size; col++)
			{
				board[row][col] = new Cell();
			}
		}
		
		/*
		  지뢰 배치 로직
		  
		  ※ 50%를 기준으로 랜덤 배치 or 전체를 지뢰로 바꾸고 랜덤 지뢰 해제 
		 */
		
		boolean flag = true;
		int count = mineCount;
		
		if(size*size/2 < mineCount)
		{
			 flag = false;
			 count = size*size-mineCount;
			 Arrays.stream(board).flatMap(Arrays::stream).forEach(c->c.setMine(true));
		}

		for(int i = 0; i < count; i++)
		{
			while(true)
			{
				int row = rd.nextInt(size);
				int col = rd.nextInt(size);
				
				if(flag)
				{
					if(!board[row][col].isMine())
					{
						board[row][col].setMine(true);
						break;
					}
				}
				else
				{
					if(board[row][col].isMine())
					{
						board[row][col].setMine(false);
						break;
					}
				}
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
					addAdjacentMines(r, c);
			}
		}
	}
	
	// 지뢰 주변 8칸의 인접 지뢰 수를 처리하는 메소드
	private void addAdjacentMines(int row, int col)
	{
		for(int i = 0; i < D_ROW.length; i++)
		{
			int nRow = row + D_ROW[i];
			int nCol = col + D_COL[i];
			
			if(nRow < 0 || nCol < 0 || nRow >= size || nCol >= size)
				continue;
			
			board[nRow][nCol].addAdjacentMines();
		}
	}
	
	@Override
	public void openCell(int row, int col)
	{
		// 닫힌 셀이 아니면 열리지 않음
		if(!board[row][col].isClosed())
			return;
		
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
		// 사용자가 입력한 칸이 인접 지뢰가 0 인 경우
		Queue<int[]> temp = new LinkedList<int[]>();
		
		// 사용자 입력 칸을 Queue 에 추가
		temp.add(new int[] {row,col});
		
		// Queue 가 비어있지 않다면 반복 실행
		while(!temp.isEmpty())
		{
			// 하나의 데이터를 꺼내옴
			int[] current = temp.poll();
			
			// 해당 칸 주변 8칸을 오픈함
			for(int i = 0; i < D_ROW.length; i++)
			{
				int nRow = current[0] + D_ROW[i];
				int nCol = current[1] + D_COL[i];
				
				// 보드판의 범위를 벗어났다면 넘어감
				if(nRow < 0 || nCol < 0 || nRow >= size || nCol >= size)
					continue;
				
				// 이미 열려있거나 깃발 상태라면 넘어감
				if(board[nRow][nCol].isClosed())
				{
					board[nRow][nCol].open();
					openCount++;
					
					// 만약 해당 칸도 인접 지뢰가 0 이라면 해당 칸을 Queue 에 추가함
					if(board[nRow][nCol].getAdjacentMines() == 0)
						temp.add(new int[] {nRow, nCol});
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

	@Override
	public Cell[][] getBoard()
	{
		return board;
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
