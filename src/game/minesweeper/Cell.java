package game.minesweeper;

/**
 * 지뢰찾기 셀 객체
 */
public class Cell
{
	private boolean mine;
	private boolean choice;
	private int adjacentMines;
	private CellStatus status;
	
	// 생성자 
	public Cell()
	{
		// 초기화
		this.mine = false;
		this.choice = false;
		this.adjacentMines = 0;
		this.status = CellStatus.CLOSED;
	}
	
	// status
	public boolean isOpen()
	{
		return status == CellStatus.OPEN;
	}
	
	public boolean isClosed()
	{
		return status == CellStatus.CLOSED;
	}
	
	public boolean isFlag()
	{
		return status == CellStatus.FLAG;
	}
	
	// 닫힌 셀만 열 수 있음
	public void open()
	{
		if(status == CellStatus.CLOSED)
		{
			status = CellStatus.OPEN;
		}
	}
	
	// 깃발 셀은 닫힌 셀로
	// 닫힌 셀은 일반 셀로
	// 열린 셀은 액션 없음
	public void toggleFlag()
	{
		if(status == CellStatus.CLOSED)
		{
			status = CellStatus.FLAG;
		}
		else if(status == CellStatus.FLAG)
		{
			status = CellStatus.CLOSED;
		}
	}
	
	
	// setter / getter
	public boolean isMine()
	{
		return mine;
	}

	public void setMine(boolean mine)
	{
		this.mine = mine;
	}

	public boolean isChoice()
	{
		return choice;
	}

	public void setChoice(boolean choice)
	{
		this.choice = choice;
	}

	public int getAdjacentMines()
	{
		return adjacentMines;
	}

	// 인접 지뢰 계산 방식만 조금 다름
	public void addAdjacentMines()
	{
		this.adjacentMines++;
	}
}
