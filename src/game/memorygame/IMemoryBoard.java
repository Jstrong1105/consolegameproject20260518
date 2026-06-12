package game.memorygame;

import java.util.List;

import card.Card;

interface IMemoryBoard
{
	void init(int count, int group);
	
	boolean isOpen(int index);
	
	void openCard(int index);
	
	void hiddenCard(List<Integer> emp);
	
	boolean isSameCard(List<Integer> emp);
	
	List<Card> getBoard();
	
	boolean isClear();
}
