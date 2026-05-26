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
	
	public HandRank(HandRankType type, List<Integer> kickers)
	{
		this.type = type;
		this.kickers = kickers; 
	}
	
	@Override
	public int compareTo(HandRank o)
	{
		int result = this.type.getRank() - o.type.getRank();
		
		if(result != 0)
		{
			return result;
		}
		
		if(this.kickers.size() != o.kickers.size())
		{
			throw new IllegalArgumentException("같은 족보의 키커 길이가 다름");
		}
		
		for(int i = 0; i < this.kickers.size(); i++)
		{
			result = this.kickers.get(i) - o.kickers.get(i);
			
			if(result != 0)
			{
				return result;
			}
		}
		
		return 0;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null)
		{
			return false;
		}
		
		if(this == o)
		{
			return true;
		}
		
		if(this.getClass() != o.getClass())
		{
			return false;
		}
		
		HandRank emp = (HandRank)o;
		
		if(this.type != emp.type)
		{
			return false;
		}
		
		if(this.kickers.size() != emp.kickers.size())
		{
			return false;
		}
		
		for(int i = 0; i < this.kickers.size(); i++)
		{
			int result = this.kickers.get(i) - emp.kickers.get(i);
			
			if(result != 0)
			{
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(type,kickers);
	}
}
