package game.poker;

import java.util.List;
import java.util.function.Function;

/**
 * 족보 리스트
 */
public enum HandRankType
{
	ROYAL_FLUSH((kickers)->
	{
		return "로얄플러시";
	},12,1)
	, STRAIGHT_FLUSH((kickers)->
	{
		return kickers.get(0) + " 스트레이트플러시";
	},11,1)
	, FOUR_OF_A_KIND((kickers)->
	{
		return kickers.get(0) + " 포카드";
	},10,2)
	, FULL_HOUSE((kickers)->
	{
		return kickers.get(0) + "," + kickers.get(1) + " 풀하우스";
	},9,2)
	, FLUSH((kickers)->
	{
		return kickers.get(0) + " 플러시";
	},8,5)
	, MOUNTAIN((kickers)->
	{
		return "마운틴";
	},7,1)
	, STRAIGHT((kickers)->
	{
		return kickers.get(0) + " 스트레이트";
	},6,1)
	, BACK_STRAIGHT((kickers)->
	{
		return "백스트레이트";
	},5,1)
	, THREE_OF_A_KIND((kickers)->
	{
		return kickers.get(0) + " 트리플";
	},4,3)
	, TWO_PAIR((kickers)->
	{
		return kickers.get(0) + "," + kickers.get(1) + " 투페어"; 
	},3,3)
	, ONE_PAIR((kickers)->
	{
		return kickers.get(0) + " 원페어";
	},2,4)
	, HIGH_CARD((kickers)->
	{
		return kickers.get(0) + " 하이카드";
	},1,5)
	;
	
	HandRankType(Function<List<Integer>, String> name, int rank, int size)
	{
		this.name = name;
		this.rank = rank;
		this.size = size;
	}
	
	private final Function<List<Integer>, String> name;
	private final int rank;
	private final int size;
	
	public String getName(List<Integer> kickers)
	{
		return name.apply(kickers);
	}
	
	public int getRank()
	{
		return rank;
	}
	
	public int getSize()
	{
		return size;
	}
}
