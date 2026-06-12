package common;


/**
 * 스레드 관련 유틸리티
 */
public final class ThreadUtil
{
	private ThreadUtil() {}
	
	/**
	 * 입력받은 밀리초만큼 프로그램을 정지 시키는 메소드
	 * @param milliSecond : 멈출 시간
	 */
	public static void timeStop(long milliSecond)
	{
		try
		{
			Thread.sleep(milliSecond);
		} 
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
	
	/**
	 * 입력받은 시간만큼 프로그램을 정지시키고 1초마다 남은 시간을 출력하는 메소드
	 * @param second : 멈출 시간 초
	 */
	public static void countDown(long second)
	{
		try
		{
			for(long i = second; i >= 0; i--)
			{
				System.out.printf("\r%d 초 남았습니다.         ",i);
				Thread.sleep(1000);
			}
		} 
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
}
