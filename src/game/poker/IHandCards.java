package game.poker;

import java.util.List;

import card.Card;

/**
 * HandCards 인터페이스
 */
interface IHandCards
{
	// 초기화
	void init();
	
	// 카드 추가
	void draw(Card card);
	
	// 카드 개수 반환
	int count();
	
	// 카드 반환
	List<Card> getCard();
	
	// 카드 오픈
	void open(int index);
}
