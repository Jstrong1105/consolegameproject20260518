package persistence;

import domain.PlayerDTO;

/**
 * 플레이어 DAO
 */
public class PlayerDAO implements IPlayerDAO
{

	@Override
	public boolean existsById(String id)
	{
		return false;
	}

	@Override
	public int addPlayer(PlayerDTO player)
	{
		return 0;
	}
}
