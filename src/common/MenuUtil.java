package common;

import java.util.List;

import game.GameApp;

/**
 * 메뉴를 화면에 출력하고 보여주는 유틸
 */
public final class MenuUtil
{
	private MenuUtil() {}
	
	public static <T, E extends Enum<E> & IMenu<T>> void showMenu(E[] menuList,T obj)
	{
		if(menuList == null || menuList.length <= 0)
		{
			throw new IllegalArgumentException("파라미터 오류");
		}
		
		for (E menu : menuList)
		{
			String menuPrompt = String.format("%d. %s : %s", menu.ordinal()+1, menu.getName(), menu.getDescription());
			
			System.out.println(menuPrompt);
		}
		
		int answer = InputUtil.readInt("메뉴를 선택해주세요",1,menuList.length);
		
		menuList[answer-1].order(obj);
	}
	
	public static void showGameList(List<GameApp> list)
	{
		boolean running = true;
		
		while(running)
		{
			ConsoleUtil.clear();
			
			int index = 1;
			
			System.out.println("======= 게임 런처 =======");
			
			for(GameApp app : list)
			{
				System.out.println(index + ". " + app.getGameName() + " : " + app.getGameDescription());
				index++;
			}
			
			System.out.println(index + ". 프로그램 종료");	
			int user = InputUtil.readInt("게임 번호 입력",1,list.size()+1);
			
			if(user == index)
			{
				running = false;
				System.out.println("프로그램을 종료합니다.");
			}
			else
			{
				list.get(user-1).run();
			}
		}
	}
}
