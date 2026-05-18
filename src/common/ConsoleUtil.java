package common;

/**
 * 콘솔창 유틸리티
 */
public final class ConsoleUtil
{
	private ConsoleUtil() {}
	
	/**
	 * window 11 환경에서 정상 작동
	 */
	public static void clear()
	{
		System.out.print("\033[H\033[2J\033[3J");
		System.out.flush();
	}
}
