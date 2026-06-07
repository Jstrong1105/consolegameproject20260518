package game.memorygame;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import common.ConsoleUtil;
import common.InputUtil;
import common.ThreadUtil;
import game.GameApp;

public class MemoryGame implements GameApp
{

	private static final String GAME_NAME = "메모리 게임";
	private static final String GAME_DESCRIPTION = "동일한 카드를 모두 맞추세요";
	
	@Override
	public String getGameName()
	{
		return GAME_NAME;
	}

	@Override
	public String getGameDescription()
	{
		return GAME_DESCRIPTION;
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
				render();
				choice();
				check();
			}
			
		} while (restart());
	}

	private boolean restart()
	{
		return InputUtil.readBool("다시 시작하시겠습니까?", "Y", "N");
	}
	
	private boolean run;
	
	private int count;
	private int group;
	
	private MemoryBoard board;
	
	private List<Integer> playerList;
	
	private Instant startTime;
	
	private void init()
	{
		 ConsoleUtil.clear();
		 
		 InputUtil.pause("메모리 게임입니다.");
		 
		 count = InputUtil.readInt("카드의 개수를 입력하세요", 4, 8);
		 group = InputUtil.readInt("그룹의 장수를 입력하세요", 2, 4);
		 
		 playerList = new ArrayList<>();
		 board = new MemoryBoard(count, group);
		 board.init();
		 
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
	
	private void render()
	{
		ConsoleUtil.clear();
		
		board.printBoard();
	}
	
	private void choice()
	{
		int choice;
		
		do
		{
			choice = InputUtil.readInt("카드를 선택",1,count*group)-1;

			if(board.isOpen(choice)) 
			{
				System.out.println("이미 오픈된 카드입니다.");
			}
			
		} while (board.isOpen(choice));
		
		board.openCard(choice);
		playerList.add(choice);
	}
	
	private void check()
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
			run = false;
			InputUtil.pause("클리어 타임 : " + Duration.between(startTime, Instant.now()).getSeconds() + "초");
		}
	}
}
