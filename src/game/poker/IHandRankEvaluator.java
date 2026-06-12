package game.poker;

import java.util.List;

import card.Card;

/**
 * 족보 판독 인터페이스
 */
interface IHandRankEvaluator
{
	HandRank eval(List<Card> handCards);
}
