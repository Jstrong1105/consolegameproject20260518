package game.trump;

import java.util.Objects;

/**
 * 해당 클래스는 외부에서 직접 생성하지 못하고
 * 패키지 내에 있는 CardDeck 클래스로만 생성하도록 구현
 */
public class Card
{
	private final CardShape shape;
	private final int number;
	private boolean open;
	
	// 패키지 프라이빗
	Card(CardShape shape, int number)
	{
		this.shape = shape;
		this.number = number;
		open = false;
	}
	
	public String getShape()
	{
		return shape.getShape();
	}
	
	public boolean isSpade()
	{
		return shape == CardShape.SPADE;
	}
	
	public boolean isDiamond()
	{
		return shape == CardShape.DIAMOND;
	}
	
	public boolean isHeart()
	{
		return shape == CardShape.HEART;
	}
	
	public boolean isClub()
	{
		return shape == CardShape.CLUB;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public boolean isOpen()
	{
		return open;
	}
	
	public void open()
	{
		this.open = true;
	}
	
	public void hidden()
	{
		this.open = false;
	}
	
	// 카드 복사하기
	// 상태는 복사하지 않음
	public Card copyCard()
	{
		return new Card(this.shape,this.number);
	}
	
	// 카드 비교하기
	// 상태와 상관없이 숫자와 모양이 같으면 동일한 카드로 인식
	@Override
	public boolean equals(Object o)
	{
		if(o == null){return false;}
		if(this == o){return true;}
		if(o.getClass() != this.getClass()){return false;}
		
		Card card = (Card)o;
		
		return this.shape == card.shape && this.number == card.number;
		
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(shape,number);
	}
}
