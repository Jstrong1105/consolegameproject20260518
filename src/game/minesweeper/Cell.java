package game.minesweeper;

/**
 * 지뢰찾기 셀 객체
 */
class Cell
{
	private boolean mine;
	private boolean choice;
	private int adjacentMines;
	private CellStatus status;
	
	// 생성자 
	Cell()
	{
		// 초기화
		this.mine = false;
		this.choice = false;
		this.adjacentMines = 0;
		this.status = CellStatus.CLOSED;
	}
	
	// status
	boolean isOpen() {	return status == CellStatus.OPEN;}
	
	boolean isClosed() {return status == CellStatus.CLOSED;}
	
	boolean isFlag(){return status == CellStatus.FLAG;}
	
	// 닫힌 셀만 열 수 있음
	void open()
	{
		if(status == CellStatus.CLOSED)
			status = CellStatus.OPEN;
	}
	
	// 깃발 셀은 닫힌 셀로
	// 닫힌 셀은 일반 셀로
	// 열린 셀은 액션 없음
	void toggleFlag()
	{
		if(status == CellStatus.CLOSED)
			status = CellStatus.FLAG;
		else if(status == CellStatus.FLAG)
			status = CellStatus.CLOSED;
	}
	
	// setter / getter
	boolean isMine(){return mine;}

	void setMine(boolean mine){this.mine = mine;}

	boolean isChoice(){	return choice;}

	void setChoice(boolean choice){this.choice = choice;}

	int getAdjacentMines(){return adjacentMines;}

	// 인접 지뢰 계산 방식은
	// 한번의 숫자를 대입하는 방식이 아닌
	// 보드판 전체를 순회하면서 지뢰 발견 시
	// 주변 8칸의 인접 칸의 지뢰 개수를 증감 시킴
	void addAdjacentMines()
	{
		this.adjacentMines++;
	}
}
