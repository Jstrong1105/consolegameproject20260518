package domain;

/**
 * 초기화 - 출력 - 액션 - 체크 - 재시작 루프의 기본 템플릿
 */
public abstract class SimpleActionTemplate extends GameTemplate
{
	@Override
	public void run()
	{
		do
		{
			init();
			running = true;
			
			while(running)
			{
				render();
				action();
				endCheck();
			}
			
		} while (restart());
	}
	
	protected abstract void action();
	protected abstract void endCheck();
}
