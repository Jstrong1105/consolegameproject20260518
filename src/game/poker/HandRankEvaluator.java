package game.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import card.Card;

/**
 * 포커 족보 계산기
 */
class HandRankEvaluator implements IHandRankEvaluator
{
	@Override
	public HandRank eval(List<Card> handCards)
	{
		if(handCards == null || handCards.size() < 5)
			throw new IllegalArgumentException("잘못된 카드 목록입니다.");
		
		prepareData(handCards);
		
		HandRank result = null;
		
		for(Supplier<HandRank> item : items)
		{
			result = item.get();
			
			if(result != null)
				return result;
		}
		
		throw new IllegalStateException("족보 판독 오류 발생");
	}
	
	private List<Supplier<HandRank>> items = List.of
			(
				this::straightFlush,
				this::fourOfAKind,
				this::fullHouse,
				this::flush,
				this::straight,
				this::threeOfAKind,
				this::twoPair,
				this::onePair,
				this::highCard
			);
	

	private HashMap<String, Integer> shapeCount;
	/*
	 * 모양 : 개수
	 * ex)
	 * ♠ : 1
	 * ◆ : 1
	 * ♥ : 3
	 * ♣ : 2
	 * 
	 * 특정 모양이 한개도 없다면 value 가 0 이 아니라 해당 모양의 키 자체가 없다.
	 */
	
	private HashMap<Integer, Integer> numberCount;
	/*
	 * 숫자 : 개수
	 * ex)
	 * 14 : 1
	 * 13 : 1
	 * 12 : 1
	 *  5 : 2
	 *  2 : 3
	 *  
	 * 특정 숫자가 한개도 없다면 해당 숫자의 키 자체가 없다.
	 */
	
	private HashMap<Integer, Integer> groupCount;
	/*
	 * 페어 : 개수
	 * ex)
	 * 4 : 1 → 4개 페어를 이룬 숫자가 한개 
	 * 3 : 1 → 3개 페어를 이룬 숫자가 한개
	 * 2 : 2 → 2개 페어를 이룬 숫자가 2개
	 * 1 : 3 → 1개 를 이룬 숫자가 3개 
	 * 
	 * 특정 페어가 한개도 없다면 해당 페어의 키 자체가 없다.
	 */
	
	private List<Integer> numberOrder;
	/*
	 * 카드 뭉치에 있는 숫자만 전부 꺼내서 내림차순 정렬한 것
	 * ex)
	 * 14 14 10 7 6 5 2 
	 */
	
	private boolean isFlush;
	/*
	 * 특정 모양이 5개 이상인지 유무 
	 */
	
	private List<Integer> flushNumberOrder;
	/*
	 * 플러시를 만족하는 경우
	 * 해당 모양을 이룬 카드의 숫자만 전부 꺼내서 내림차순 정렬한 것
	 * ex)
	 * 14 7 6 5 3
	 * 
	 * 플러시를 만족하지 않는 경우 이 데이터는 비어있음
	 */
	
	private HandRankType rank;
	private List<Integer> kicker;
	
	private void prepareData(List<Card> handCards)
	{
		// 데이터 초기화
		shapeCount = new HashMap<>();
		numberCount = new HashMap<>();
		groupCount = new HashMap<>();
		numberOrder = new ArrayList<>();
		isFlush = false;
		flushNumberOrder = new ArrayList<>();
		
		rank = null;
		kicker = new ArrayList<>();
		
		for(Card card : handCards)
		{
			shapeCount.put(card.getShape().getShape(), shapeCount.getOrDefault(card.getShape(), 0) + 1);
			numberCount.put(card.getNumber().getNumber(), numberCount.getOrDefault(card.getNumber().getNumber(), 0) + 1);
			numberOrder.add(card.getNumber().getNumber());
		}
		
		Collections.sort(numberOrder,Collections.reverseOrder());
		
		for(int group : numberCount.values())
		{
			groupCount.put(group, groupCount.getOrDefault(group, 0) + 1);
		}
		
		for(String shape : shapeCount.keySet())
		{
			if(shapeCount.get(shape) >= 5)
			{
				isFlush = true;
				
				for(Card card : handCards)
				{
					if(card.getShape().equals(shape))
					{
						flushNumberOrder.add(card.getNumber().getNumber());
					}
				}
				
				Collections.sort(flushNumberOrder,Collections.reverseOrder());
				
				// 플러시를 이루는 모양이 2개 이상인 경우는 제외함
				// 5 / 7 포커에서 플러시를 이루는 모양이 2개 이상일 수 없기 때문
				break;
			}
		}
	}
	
	/**
	 * 스트레이트 여부를 판독해서 
	 * 스트레이트라면 스트레이트를 이룬 가장 큰 숫자를
	 * 스트레이트가 아니라면 -1을 반환 하는 메소드
	 *  
	 * @param numbers : 판독할 숫자들
	 * @return 스트레이트를 이룬 가장 큰 숫자 
	 */
	private int straightNumber(List<Integer> numbers)
	{
		// 중복 제거 및 내림차순 정렬
		List<Integer> data = numbers.stream()
									.distinct()
									.sorted(Comparator.reverseOrder())
									.toList();
		
		int n = 0;
		
		/*
		 * i    :  0  1  2  3  4  5  6  7  8 
		 * data : 14 13 12 11  9  8  7  6  5
		 * n    :  1  2  3  0  1  2  3  4
		 *                              ↑ 9 스트레이트 확인
		 */
		
		for(int i = 0; i < data.size()-1; i++)
		{
			if(data.get(i) - data.get(i+1) == 1)
				n++;
			else
				n = 0;
			if(n == 4)
				return data.get(i-3);
		}
		
		// 백스트레이트 검사
		if(data.contains(14) && data.contains(2) && data.contains(3) && data.contains(4) && data.contains(5))
			return 5;
		
		return -1;
	}
	
	private HandRank straightFlush()
	{
		// 플러시 조건을 만족하고
		if(isFlush)
		{
			int num = straightNumber(flushNumberOrder);
			
			// 플러시를 이루는 숫자가 스트레이트를 만족한다면
			if(num != -1)
			{
				if(num == 14)
					rank = HandRankType.ROYAL_FLUSH;
				else
					rank = HandRankType.STRAIGHT_FLUSH;
				
				kicker.add(num);
				
				return new HandRank(rank,kicker);
			}
		}
		
		return null;
	}
	
	private HandRank fourOfAKind()
	{
		// 4장 그룹이 하나라도 있다며
		if(groupCount.getOrDefault(4, 0) >= 1)
		{
			rank = HandRankType.FOUR_OF_A_KIND;
			
			for(int four : numberOrder)
			{
				if(numberCount.get(four) >= 4)
				{
					kicker.add(four);
					
					// 4장 그룹을 이룬 숫자를 제외한 가장 큰 숫자 1개를 키커에 추가
					numberOrder.stream()
							   .filter(num -> num != four)
							   .limit(1)
							   .forEach(num -> kicker.add(num));
					
					return new HandRank(rank,kicker);
				}
			}
		}
		
		return null;
	}
	
	private HandRank fullHouse()
	{
		int two = groupCount.getOrDefault(2, 0);
		int three = groupCount.getOrDefault(3, 0);
		
		// 3장 그룹이 2개 이상 있거나 
		// 3장 그룹이 1개 , 2장 그룹이 1개 이상이라면
		if(three >= 2 || (three >= 1 && two >= 1))
		{
			rank = HandRankType.FULL_HOUSE;
			
			for(int triple : numberOrder)
			{
				if(numberCount.get(triple) >= 3)
				{
					kicker.add(triple);
					
					for(int pair : numberOrder)
					{
						if(pair != triple && numberCount.get(pair) >= 2)
						{
							kicker.add(pair);
							
							return new HandRank(rank,kicker);
						}
					}
				}
			}
		}
		
		return null;
	}
	
	private HandRank flush()
	{
		// 플러시 조건을 만족한다면
		if(isFlush)
		{
			rank = HandRankType.FLUSH;
			
			// 플러시를 이루는 숫자들 중 가장 큰 5개를 키커에 추가함
			flushNumberOrder.stream()
							.limit(5)
							.forEach(num -> kicker.add(num));
			
			return new HandRank(rank,kicker);
		}
		
		return null;
	}
	
	private HandRank straight()
	{
		int num = straightNumber(numberOrder);
		
		// 스트레이트 조건을 만족한다면
		if(num != -1)
		{
			if(num == 14)
				rank = HandRankType.MOUNTAIN;
			else if(num == 5)
				rank = HandRankType.BACK_STRAIGHT;
			else
				rank = HandRankType.STRAIGHT;
			
			kicker.add(num);
			
			return new HandRank(rank, kicker);
		}
		
		return null;
	}
	
	private HandRank threeOfAKind()
	{
		// 3장 그룹이 하나라도 존재한다면
		if(groupCount.getOrDefault(3, 0) >= 1)
		{
			rank = HandRankType.THREE_OF_A_KIND;
			
			for(int triple : numberOrder)
			{
				if(numberCount.get(triple) >= 3)
				{
					kicker.add(triple);
					
					// 3장 그룹을 이룬 숫자를 제외한
					// 가장 큰 숫자 2개를 키커에 추가함
					numberOrder.stream()
							   .filter(num -> num != triple)
							   .limit(2)
							   .forEach(num -> kicker.add(num));
					
					return new HandRank(rank, kicker);
				}
			}
		}
		
		return null;
	}
	
	private HandRank twoPair()
	{
		// 2장 그룹이 2개 이상 존재한다며
		if(groupCount.getOrDefault(2, 0) >= 2)
		{
			rank = HandRankType.TWO_PAIR;
			
			for(int highPair : numberOrder)
			{
				if(numberCount.get(highPair) >= 2)
				{
					// 2장 그룹을 이룬 숫자 중 첫번째로 큰 숫자
					kicker.add(highPair);
					
					for(int lowPair : numberOrder)
					{
						// 2장 그룹을 이룬 숫자 중 두번째로 큰 숫자
						if(lowPair != highPair && numberCount.get(lowPair) >= 2)
						{
							kicker.add(lowPair);
							
							// 2장 그룹을 이룬 숫자 중 첫번쨰,두번째로 큰 숫자를 제외한
							// 가장 큰 숫자 하나를 키커에 추가함
							numberOrder.stream()
									   .filter(num -> num != highPair && num != lowPair)
									   .limit(1)
									   .forEach(num -> kicker.add(num));
							
							return new HandRank(rank,kicker);
						}
					}
				}
			}
		}
		
		return null;
	}
	
	private HandRank onePair()
	{
		// 2장 그룹이 하나라도 존재한다면
		if(groupCount.getOrDefault(2, 0) >= 1)
		{
			rank = HandRankType.ONE_PAIR;
			
			for(int pair : numberOrder)
			{
				if(numberCount.get(pair) >= 2)
				{
					// 2장 그룹을 이룬 숫자
					kicker.add(pair);
					
					// 2장 그룹을 이룬 숫자를 제외한 가장 큰 숫자 3개를 키커에 추가함
					numberOrder.stream()
							   .filter(num -> num != pair)
							   .limit(3)
							   .forEach(num -> kicker.add(num));
					
					return new HandRank(rank, kicker);
				}
			}
		}
		
		return null;
	}
	
	private HandRank highCard()
	{
		rank = HandRankType.HIGH_CARD;
		
		// 가장 큰 숫자 5개를 키커에 추가함
		numberOrder.stream()
				   .limit(5)
				   .forEach(num -> kicker.add(num));
		
		return new HandRank(rank, kicker);
	}
}
