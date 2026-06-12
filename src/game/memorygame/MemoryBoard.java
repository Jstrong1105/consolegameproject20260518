package game.memorygame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import card.Card;
import card.CardDeck;
import card.CardPrinter;
import card.ICardDeck;

/*
 * 메모리 게임 보드판
 */
class MemoryBoard implements IMemoryBoard
{
	private List<Card> board;
	
	private final ICardDeck cardDeck;
	private int count;
	private int group;
	
	MemoryBoard(ICardDeck cardDeck)
	{
		this.cardDeck = cardDeck; 
	}
	
	@Override
	public void init(int count, int group)
	{
		this.count = count;
		this.group = group;
		
		cardDeck.init();
		
		board = new ArrayList<Card>();
		
		for(int i = 0; i < this.count; i++)
		{
			Card card = cardDeck.drawCard();
			
			for(int j = 0; j < this.group; j++)
			{
				board.add(card.copyCard());
			}
		}
		
		Collections.shuffle(board);
	}
	
	@Override
	public boolean isOpen(int index)
	{
		if(index < 0 || index >= board.size())
		{
			throw new IllegalArgumentException("보드 크기 벗어남");
		}
		
		return board.get(index).isOpen();
	}
	
	@Override
	public void openCard(int index)
	{
		if(index < 0 || index >= board.size())
		{
			throw new IllegalArgumentException("보드 크기 벗어남");
		}
		
		board.get(index).open();
	}
	
	@Override
	public void hiddenCard(List<Integer> emp)
	{
		for(int i : emp)
		{
			board.get(i).hidden();
		}
	}
	
	@Override
	public boolean isSameCard(List<Integer> emp)
	{
		for(int i = 0; i < emp.size() - 1; i++)
		{
			if(!board.get(emp.get(i)).equals(board.get(emp.get(i+1))))
			{
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public List<Card> getBoard()
	{
		return board;
	}
	
	@Override
	public boolean isClear()
	{
		return board.stream().allMatch(card->card.isOpen());
	}
}
