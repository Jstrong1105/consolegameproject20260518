package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 콘솔창에서 사용자에게 입력을 받는 유틸리티
 */
public final class InputUtil
{
	private InputUtil() {}
	// 유틸리티 특성상 생성자 private 
	
	private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	// 콘솔 프로그램 특성상 프로그램 종료시 까지 입력 스트림이 유지되기 때문에
	// 특별히 입력 객체를 닫는 동작은 생성하지 않음
	// 프로그램 종료시 자동으로 닫힘 처리
	
	/**
	 * 
	 * @param prompt : 사용자 안내 메시지
	 * 사용자에게 안내 메시지를 보여주고 엔터를 입력할때 까지 대기하는 메소드
	 */
	public static void pause(String prompt)
	{
		System.out.println(prompt);
		
		try
		{
			br.readLine();
		} 
		catch (Exception e)
		{
			throw new RuntimeException("입력 스트림 에러 발생");
		}
	}
	
	/**
	 * 
	 * @param prompt : 사용자 안내 메시지
	 * @return       : 사용자 입력 값
	 * exit 입력 시 프로그램 강제 종료 → 테스트 목적
	 */
	public static String readString(String prompt)
	{
		try
		{
			System.out.print(prompt + " : ");

			String answer = br.readLine();
			
			if(answer == null)
			{
				answer = "";
			}
			
			answer = answer.trim();
			
			// 테스트 과정에서 필요한 중단 코드
			if(answer.equals("exit"))
			{
				System.exit(0);
			}
			
			return answer;
		}
		catch (IOException e)
		{
			throw new RuntimeException("입력 스트림 에러 발생");
		}
	}
	
	/**
	 * 
	 * @param prompt : 사용자 안내 메시지
	 * @return       : 사용자 입력 숫자
	 */
	public static int readInt(String prompt)
	{
		while (true)
		{
			try
			{
				String answer = readString(prompt);
				
				return Integer.parseInt(answer);
			}
			catch (NumberFormatException e)
			{
				System.out.println("숫자만 입력해주세요.");
			}
		}
	}
	
	/**
	 * 
	 * @param prompt : 사용자 안내 메시지
	 * @param min    : 최소 입력 범위
	 * @param max    : 최대 입력 범위
	 * @return       : 사용자 입력 숫자
	 */
	public static int readInt(String prompt, int min, int max)
	{
		prompt = String.format(prompt + " (%d ~ %d)", min,max);
		
		while(true)
		{
			try
			{
				String answer = readString(prompt);
				
				int result = Integer.parseInt(answer);
				
				if(result < min || result > max)
				{
					System.out.printf("%d ~ %d 사이로 입력해주세요.%n",min,max);
				}
				
				else
				{
					return result;
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println("숫자만 입력해주세요.");
			}
		}
	}
	
	/**
	 * 
	 * @param prompt	  : 사용자 안내 메시지
	 * @param trueAnswer  : true 반환 문자열
	 * @param falseAnswer : false 반환 문자열
	 * @return true / false
	 */
	public static boolean readBool(String prompt, String trueAnswer, String falseAnswer)
	{
		prompt = String.format(prompt + " (%s/%s)", trueAnswer,falseAnswer);
		
		while(true)
		{
			String answer = readString(prompt);
			
			if(answer.equalsIgnoreCase(trueAnswer))
			{
				return true;
			}
			else if(answer.equalsIgnoreCase(falseAnswer))
			{
				return false;
			}
			else
			{
				System.out.printf("%s 또는 %s 를 입력해주세요.%n",trueAnswer,falseAnswer);
			}
		}
	}
}
