package common;

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
}
