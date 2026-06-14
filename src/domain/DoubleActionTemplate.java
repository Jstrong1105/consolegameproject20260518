package domain;

/**
 * 한 루프의 두개의 다른 액션이 일어나는 템플릿
 */
public abstract class DoubleActionTemplate extends GameTemplate
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
				firstAction();
				render();
				secondAction();
			}
			
		} while (restart());
	}
	
	protected abstract void firstAction();
	protected abstract void secondAction();
}
