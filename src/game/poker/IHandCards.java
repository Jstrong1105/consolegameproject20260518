package game.poker;

import game.trump.Card;

/**
 * HandCards 인터페이스
 */
public interface IHandCards
{
	// 초기화
	void init();
	
	// 카드 추가
	void draw(Card card);
	
	// 카드 개수 반환
	int count();
	
	// 카드 출력
	void print();
	
	// 카드 오픈
	void open(int index);
	
	// 결과 확인
	HandRank getResult();
}
