package main;

import common.ConsoleUtil;

import common.InputUtil;
import domain.GameInfo;

/**
 * 메인 메뉴 출력 유틸 
 */
public final class MainMenu
{
	private MainMenu() {}
	
	public static <E extends GameInfo> void showGameList(E[] list)
	{
		if(list == null || list.length <= 0)
			return;
		
		boolean running = true;
		
		while(running)
		{
			ConsoleUtil.clear();
			
			int index = 1;
			
			System.out.println("======= 게임 런처 =======");
			
			for(E item : list)
			{
				System.out.println(index + ". " + item.getGameName() + " : " + item.getGameDescription());
				index++;
			}
			
			System.out.println(index + ". 프로그램 종료");	
			int user = InputUtil.readInt("게임 번호 입력",1,index);
			
			if(user == index)
			{
				running = false;
				System.out.println("프로그램을 종료합니다.");
			}
			else
			{
				list[user-1].getGameApp().run();
			}
		}
	}
}
