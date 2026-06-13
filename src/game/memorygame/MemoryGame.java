package game.memorygame;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import card.Card;
import card.ICardPrinter;
import common.ConsoleUtil;
import common.InputUtil;
import common.ThreadUtil;
import domain.SimpleActionTemplate;

class MemoryGame extends SimpleActionTemplate
{
	private final ICardPrinter printer;
	private final IMemoryBoard board;
	
	private int count;
	private int group;
	
	private List<Integer> playerList;
	
	private Instant startTime;
	
	MemoryGame(ICardPrinter printer, IMemoryBoard board)
	{
		this.printer = printer;
		this.board = board;
	}
	
	@Override
	protected void init()
	{
		ConsoleUtil.clear();
		 
		 count = InputUtil.readInt("카드의 개수를 입력하세요", 4, 8);
		 group = InputUtil.readInt("그룹의 장수를 입력하세요", 2, 4);
		 
		 playerList = new ArrayList<>();
		 board.init(count, group);
		 
		 List<Integer> emp = new ArrayList<Integer>();
		 
		 for(int i = 0; i < count*group; i++)
		 {
			 emp.add(i);
			 board.openCard(i);
		 }
		 
		 InputUtil.pause("다음 보여지는 카드를 외우세요!");
		 
		 render();
		 
		 ThreadUtil.countDown(15);
		 
		 board.hiddenCard(emp);
		 
		 startTime = Instant.now();
	}
	
	@Override
	protected void render()
	{
		ConsoleUtil.clear();
		
		List<Card> boardCard = board.getBoard();
		
		List<Card> emp = new ArrayList<>();
		
		for(int i = 0; i < group; i++)
		{
			emp.clear();
			
			for(int j = 0; j < count; j++)
			{
				emp.add(boardCard.get(i*count + j));
			}
			
			printer.printCards(emp);
			
			System.out.println();
		}
	}
	
	@Override
	protected void action()
	{
		int choice;
		
		do
		{
			choice = InputUtil.readInt("카드를 선택",1,count*group)-1;

			if(board.isOpen(choice)) 
				System.out.println("이미 오픈된 카드입니다.");
			
		} while (board.isOpen(choice));
		
		board.openCard(choice);
		playerList.add(choice);
	}
	
	@Override
	protected void endCheck()
	{
		if(playerList.size() >= group)
		{
			render();
			
			if(board.isSameCard(playerList))
			{
				InputUtil.pause("같은 카드입니다");
			}
			else
			{
				System.out.println("다른 카드입니다.");
				ThreadUtil.countDown(3);
				
				board.hiddenCard(playerList);
			}
			
			playerList.clear();
		}
		
		if(board.isClear())
		{
			endGame();
			InputUtil.pause("클리어 타임 : " + Duration.between(startTime, Instant.now()).getSeconds() + "초");
		}
	}
}
