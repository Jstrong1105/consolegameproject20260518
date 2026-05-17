package persistence;

import domain.PlayerDTO;

/**
 * 사용자 DAO 인터페이스
 */
public interface IPlayerDAO
{
	// 중복 아이디 확인
	boolean existsById(String id);
	
	// 회원가입 
	int addPlayer(PlayerDTO player);
}
