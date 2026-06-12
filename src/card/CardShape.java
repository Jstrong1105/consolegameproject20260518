package card;

/**
 * 카드의 모양 나열
 */
public enum CardShape
{
	SPADE("♠"),
	DIAMOND("◆"),
	HEART("♥"),
	CLUB("♣")
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
