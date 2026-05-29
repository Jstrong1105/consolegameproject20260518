package game.poker;

import common.ConsoleUtil;
import common.InputUtil;
import game.GameApp;
import game.trump.Card;
import game.trump.CardDeck;

public class Poker implements GameApp
{
	private static final String GAME_DESCRIPTION = "포커를 승리해 목표를 달성하세요";
	private static final String GAME_NAME = "포커";
	
	@Override
	public String getGameDescription()
	{
		return GAME_DESCRIPTION;
	}
	
	@Override
	public String getGameName()
	{
		return GAME_NAME;
	}
	
	@Override
	public void run()
	{
		do
		{
			init();
			run = true;
			
			while(run)
			{
				roundInit();
				
				while(round)
				{
					draw();
					print();
					betting();
					eval();
				}
				
				clearCheck();
			}
			
		} while (restart());
	}

	private static final int TARGET_COIN = 10000;
	private static final int START_COIN = 1000;
	private static final int BASIC_BET_COIN = 100;
	
	private int mode;
	
	private boolean run;
	private boolean round;
	
	private HandCards playerCard;
	private HandCards cpuCard;
	private CardDeck deck;
	
	private int playerCoin;
	private int betCoin;
	
	private void init()
	{
		ConsoleUtil.clear();
	
		deck = new CardDeck();
		playerCard = new HandCards(new HandRankEvaluator());
		cpuCard = new HandCards(new HandRankEvaluator());
		
		playerCoin = START_COIN;
		
		if(InputUtil.readBool("1. 5포커 / 2. 7포커", "1", "2"))
		{
			mode = 5;
		}
		else
		{
			mode = 7;
		}
	}
	
	private void roundInit()
	{
		ConsoleUtil.clear();
		
		playerCard.init();
		cpuCard.init();
		deck.init();
		
		draw();
		
		round = true;
		
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
	
	private void print()
	{
		ConsoleUtil.clear();
		cpuCard.print();
		System.out.println("Cpu 카드");
		playerCard.print();
		System.out.println("당신의 카드");
		System.out.println();
	}
	
	private void betting()
	{
		System.out.println("목표 코인 : " + TARGET_COIN);
		System.out.println("남은 코인 : " + playerCoin);
		System.out.println("베팅 코인 : " + betCoin);
		
		int coin = 0;
	
		coin = InputUtil.readInt("베팅할 코인을 입력",0,playerCoin);
	
		// 기권
		if(coin == 0 && playerCoin != 0)
		{
			InputUtil.pause("YOUR FOLD");
			round = false;
			return;
		}
		
		betCoin += coin;
		playerCoin -= coin;
	}
	
	private void draw()
	{
		// 플레이어 카드는 공개하고
		Card card = deck.drawCard();
		card.open();
		playerCard.draw(card);
		
		// CPU 카드는 비공개
		cpuCard.draw(deck.drawCard());
	}
	
	private void eval()
	{
		if(playerCard.count() >= mode)
		{
			for(int i = 0; i < mode; i++)
			{
				cpuCard.open(i);
			}
			
			ConsoleUtil.clear();
			HandRank cpuResult = cpuCard.getResult();
			HandRank playerResult = playerCard.getResult();
			
			cpuCard.print();
			System.out.println(cpuResult.getName());
			
			playerCard.print();
			System.out.println(playerResult.getName());
			
			
			// 승패 확인
			int result = playerResult.compareTo(cpuResult);
			
			if(result > 0)
			{
				InputUtil.pause("YOU WIN!");
				playerCoin += betCoin * 2;
				round = false;
			}
			else if(result < 0)
			{
				InputUtil.pause("YOU LOOSE");
				round = false;
			}
			else
			{
				InputUtil.pause("DRAW");
				playerCoin += betCoin;
				round = false;
			}
		}
	}
	
	private void clearCheck()
	{
		if(playerCoin >= TARGET_COIN)
		{
			run = false;
			System.out.println("목표를 달성했습니다.");
			return;
		}
		
		if(playerCoin <= 0)
		{
			run = false;
			System.out.println("코인을 전부 소진했습니다.");
			return;
		}
	}
	
	private boolean restart()
	{
		return InputUtil.readBool("다시 시작하시겠습니까?", "Y", "N");
	}
}
