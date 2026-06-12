package domain;

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
