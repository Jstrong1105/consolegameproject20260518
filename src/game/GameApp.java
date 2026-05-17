package game;

/**
 * 모든 게임이 구현해야하는 게임 인터페이스
 */
public interface GameApp
{ 
	public String getGameName();
	// 게임 이름
	
	public String getGameDescription();
	// 게임 설명
	
	public void run();
	// 게임 실행
}
