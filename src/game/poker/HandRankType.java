package game.poker;

/**
 * 족보 리스트
 */
public enum HandRankType
{
	ROYAL_FLUSH("로얄플러시",12)
	, STRAIGHT_FLUSH("스트레이트플러시",11)
	, FOUR_OF_A_KIND("포카드",10)
	, FULL_HOUSE("풀하우스",9)
	, FLUSH("플러시",8)
	, MOUNTAIN("마운틴",7)
	, STRAIGHT("스트레이트",6)
	, BACK_STRAIGHT("백스트레이트",5)
	, THREE_OF_A_KIND("트리플",4)
	, TWO_PAIR("투페어",3)
	, ONE_PAIR("원페어",2)
	, HIGH_CARD("하이카드",1)
	;
	
	HandRankType(String name, int rank)
	{
		this.name = name;
		this.rank = rank;
	}
	
	private final String name;
	private final int rank;
	
	public String getName()
	{
		return name;
	}
	
	public int getRank()
	{
		return rank;
	}
}
