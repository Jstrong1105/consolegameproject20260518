package game.poker;

import java.util.List;

import game.trump.Card;

/**
 * 족보 판독 인터페이스
 */
public interface IHandRankEvaluator
{
	HandRank eval(List<Card> handCards);
}
