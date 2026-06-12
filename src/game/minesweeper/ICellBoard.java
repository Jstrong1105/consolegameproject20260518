package game.minesweeper;

/**
 * 지뢰찾기 보드 인터페이스
 */
interface ICellBoard
{
	void init(int size, int mineCount);
	// 보드 초기화
	
	boolean isMine(int row, int col);
	// 지뢰 여부 확인
	
	boolean isOpen(int row, int col);
	// 열림 여부 확인
	
	boolean isClosed(int row, int col);
	// 닫힘 여부 확인
	
	boolean isFlag(int row, int col);
	// 깃발 여부 확인
	
	void choiceCell(int row, int col);
	// 한칸 선택하기
	
	void cancelCell(int row, int col);
	// 선택 취소하기
	
	void openFirst(int row, int col);
	// 첫 오픈 시 지뢰 여부 확인 및 지뢰 재배치
	
	void openCell(int row, int col);
	// 한칸 열기
	
	void toggleFlagCell(int row, int col);
	// 닫힌 셀은 일반 셀로
	// 일반 셀은 닫힌 셀로
	
	boolean isClear();
	// 성공 여부
	
	int getMineCount();
	// 지뢰 개수
	
	int getFlagCount();
	// 깃발 개수
	
	Cell[][] getBoard();
	// 보드 반환
	
	void finish();
	// 게임 종료 시 지뢰 전부 공개
}
