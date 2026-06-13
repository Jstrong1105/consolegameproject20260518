package domain;

/**
 * 게임 목록 인터페이스
 */
public interface GameInfo
{
	String getGameName();
	String getGameDescription();
	GameApp getGameApp();
}
