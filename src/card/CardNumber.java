package card;

public enum CardNumber
{
	ACE(14,"A")
	, KING(13,"K")
	, QUEEN(12,"Q")
	, JACK(11,"J")
	, TEN(10,"T")
	, NINE(9,"9")
	, EIGHT(8,"8")
	, SEVEN(7,"7")
	, SIX(6,"6")
	, FIVE(5,"5")
	, FOUR(4,"4")
	, THREE(3,"3")
	, TWO(2,"2")
	;
	
	CardNumber(int number, String numShape)
	{
		this.number = number;
		this.numShape = numShape;
	}
	
	private final int number;
	private final String numShape;
	
	public int getNumber()
	{
		return number;
	}
	
	public String getShape()
	{
		return numShape;
	}
	
	public static CardNumber of(int number)
	{
		for(CardNumber item : values())
		{
			if(item.getNumber() == number)
				return item;
		}
		
		throw new IllegalArgumentException("유효하지 않은 숫자입니다.");
	}
}
