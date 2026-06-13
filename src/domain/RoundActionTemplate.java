package domain;

/**
 * 한 루프의 같은 액션이 여러번 일어나는 템플릿
 */
public abstract class RoundActionTemplate extends GameTemplate
{
	private boolean round;
	
	@Override
	public void run()
	{
		do
		{
			init();
			
			running = true;
			
			while(running)
			{
				roundInit();
				
				round = true;
				
				while(round)
				{
					render();
					action();
				}
				
				endCheck();
			}
			
		} while (restart());
	}
	
	protected void endRound()
	{
		round = false;
	}
	
	protected abstract void roundInit();
	protected abstract void action();
	protected abstract void endCheck();
}
