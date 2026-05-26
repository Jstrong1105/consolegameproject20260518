package game.trump;

/**
 * 카드의 모양 나열
 */
public enum CardShape
{
	SPADE("\033[94m ♠\033[0m"),
	DIAMOND("\033[91m ◆\033[0m"),
	HEART("\033[91m ♥\033[0m"),
	CLUB("\033[94m ♣\033[0m")
	;
	
	CardShape(String shape)
	{
		this.shape = shape;
	}
	
	private final String shape;
	
	public String getShape()
	{
		return shape;
	}
}
