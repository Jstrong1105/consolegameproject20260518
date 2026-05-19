package common;

/**
 * 메뉴 인터페이스
 */
public interface IMenu<T>
{
	String getName();
	String getDescription();
	void order(T obj);
}
