package game.memorygame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.trump.Card;
import game.trump.CardDeck;
import game.trump.CardPrinter;

/*
 * 메모리 게임 보드판
 */
public class MemoryBoard
{
	private List<Card> board;
	
	private CardDeck cardDeck;
	
	private final int count;
	private final int group;
	
	MemoryBoard(int count, int group)
	{
		cardDeck = new CardDeck();
		this.count = count;
		this.group = group;
	}
	
	void init()
	{
		cardDeck.init();
		
		board = new ArrayList<Card>();
		
		for(int i = 0; i < count; i++)
		{
			Card card = cardDeck.drawCard();
			
			for(int j = 0; j < group; j++)
			{
				board.add(card.copyCard());
			}
		}
		
		Collections.shuffle(board);
	}
	
	boolean isOpen(int index)
	{
		if(index < 0 || index >= board.size())
		{
			throw new IllegalArgumentException("보드 크기 벗어남");
		}
		
		return board.get(index).isOpen();
	}
	
	void openCard(int index)
	{
		if(index < 0 || index >= board.size())
		{
			throw new IllegalArgumentException("보드 크기 벗어남");
		}
		
		board.get(index).open();
	}
	
	void hiddenCard(List<Integer> emp)
	{
		for(int i : emp)
		{
			board.get(i).hidden();
		}
	}
	
	boolean isSameCard(List<Integer> emp)
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
	
	void printBoard()
	{
		List<Card> emp = new ArrayList<>();
		
		for(int i = 0; i < group; i++)
		{
			emp.clear();
			
			for(int j = 0; j < count; j++)
			{
				emp.add(board.get(i*count + j));
			}
			
			CardPrinter.printCards(emp);
			
			System.out.println();
		}
	}
	
	boolean isClear()
	{
		return board.stream().allMatch(card->card.isOpen());
	}
}
