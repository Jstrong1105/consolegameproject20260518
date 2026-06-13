package domain;

import common.InputUtil;

/**
 * 최상위 게임 템플릿
 */
public abstract class GameTemplate implements GameApp
{
	protected boolean running;
	
	protected boolean restart()
	{
		return InputUtil.readBool("다시 시작하시겠습니까?", "Y", "N");
	}
	
	protected void endGame()
	{
		running = false;
	}
	
	protected abstract void init();
	protected abstract void render();
}
