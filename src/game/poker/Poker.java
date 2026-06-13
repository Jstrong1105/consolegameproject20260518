package game.poker;

import card.Card;
import card.ICardDeck;
import card.ICardPrinter;
import common.ConsoleUtil;
import common.InputUtil;
import domain.RoundActionTemplate;

class Poker extends RoundActionTemplate
{
	private static final int TARGET_COIN = 10000;
	private static final int START_COIN = 1000;
	private static final int BASIC_BET_COIN = 100;
	
	private int mode;
	
	private final IHandCards playerCard;
	private final IHandCards cpuCard;
	private final IHandRankEvaluator evaluator;
	private final ICardPrinter printer;
	private final ICardDeck deck;
	
	private int playerCoin;
	private int betCoin;
	
	Poker(IHandCards playerCard, IHandCards cpuCard, IHandRankEvaluator evaluator, ICardPrinter printer, ICardDeck deck)
	{
		this.playerCard = playerCard;
		this.cpuCard = cpuCard;
		this.evaluator = evaluator;
		this.printer = printer;
		this.deck = deck;
	}
	
	@Override
	protected void init()
	{
		ConsoleUtil.clear();
		
		playerCoin = START_COIN;
		
		if(InputUtil.readBool("1. 5포커 / 2. 7포커", "1", "2"))
			mode = 5;
		else
			mode = 7;
	}

	@Override
	protected void roundInit()
	{
		ConsoleUtil.clear();
		
		playerCard.init();
		cpuCard.init();
		deck.init();
			
		draw();
		draw();
		
		betCoin = 0;
		
		// 기본 베팅
		if(playerCoin >= BASIC_BET_COIN)
		{
			betCoin += BASIC_BET_COIN;
			playerCoin -= BASIC_BET_COIN;
		}
		else
		{
			betCoin += playerCoin;
			playerCoin= 0;
		}
	}

	@Override
	protected void render()
	{
		ConsoleUtil.clear();
		printer.printCards(cpuCard.getCard());
		System.out.println("Cpu 카드");
		
		printer.printCards(playerCard.getCard());
		System.out.println("당신의 카드");
		System.out.println();
	}

	@Override
	protected void action()
	{
		if(betting())
		{
			if(playerCard.count() < mode)
				draw();
			else
				eval();
		}
	}

	@Override
	protected void endCheck()
	{
		if(playerCoin >= TARGET_COIN)
		{
			endGame();
			System.out.println("목표를 달성했습니다.");
			return;
		}
		else if(playerCoin <= 0)
		{
			endGame();
			System.out.println("코인을 전부 소진했습니다.");
			return;
		}
	}
	
	private boolean betting()
	{
		System.out.println("목표 코인 : " + TARGET_COIN);
		System.out.println("남은 코인 : " + playerCoin);
		System.out.println("베팅 코인 : " + betCoin);
	
		int coin = InputUtil.readInt("베팅할 코인을 입력",0,playerCoin);
	
		// 기권
		if(coin == 0 && playerCoin != 0)
		{
			InputUtil.pause("YOUR FOLD");
			endRound();
			return false;
		}
		
		betCoin += coin;
		playerCoin -= coin;
		return true;
	}
	
	private void draw()
	{
		Card card = deck.drawCard();
		card.open();
		playerCard.draw(card);
		
		cpuCard.draw(deck.drawCard());
	}
	
	private void eval()
	{
		for(int i = 0; i < mode; i++)
		{
			cpuCard.open(i);
		}
		
		ConsoleUtil.clear();
		HandRank cpuResult = evaluator.eval(cpuCard.getCard());
		HandRank playerResult = evaluator.eval(playerCard.getCard());
		
		printer.printCards(cpuCard.getCard());
		System.out.println(cpuResult.getName());
		
		printer.printCards(playerCard.getCard());
		System.out.println(playerResult.getName());
		
		
		// 승패 확인
		int result = playerResult.compareTo(cpuResult);
		
		if(result > 0)
		{
			InputUtil.pause("YOU WIN!");
			playerCoin += betCoin * 2;
		}
		else if(result < 0)
		{
			InputUtil.pause("YOU LOSE");
		}
		else
		{
			InputUtil.pause("DRAW");
			playerCoin += betCoin;
		}
		
		endRound();
	}
}
