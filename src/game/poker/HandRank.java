package game.poker;

import java.util.List;
import java.util.Objects;

/**
 * 족보 결과
 */
public class HandRank implements Comparable<HandRank>
{
	private final HandRankType type;
	private final List<Integer> kickers;
	
	// 생성자
	public HandRank(HandRankType type, List<Integer> kickers)
	{
		if(kickers.size() != type.getSize())
		{
			throw new IllegalArgumentException("키커 수 오류");
		}
		
		this.type = type;
		this.kickers = List.copyOf(kickers);
	}
	
	// 족보 이름 반환
	public String getName()
	{
		return type.getName(kickers);
	}
	
	// 족보 비교
	@Override
	public int compareTo(HandRank o)
	{
		int result = this.type.getRank() - o.type.getRank();
		
		if(result != 0){return result;}
		
		for(int i = 0; i < this.kickers.size(); i++)
		{
			result = this.kickers.get(i) - o.kickers.get(i);
			
			if(result != 0){return result;}
		}
		
		return 0;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null){return false;}
		if(this == o){return true;}
		if(this.getClass() != o.getClass()){return false;}
		
		HandRank emp = (HandRank)o;
		
		if(this.type != emp.type){return false;}
		
		for(int i = 0; i < this.kickers.size(); i++)
		{
			int result = this.kickers.get(i) - emp.kickers.get(i);
			
			if(result != 0){return false;}
		}
		
		return true;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(type,kickers);
	}
}
