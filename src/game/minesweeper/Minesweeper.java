package game.minesweeper;

import java.util.function.Consumer;

import common.ConsoleUtil;
import common.IMenu;
import common.InputUtil;
import common.MenuUtil;
import game.GameApp;

public class Minesweeper implements GameApp
{
	private static final String GAME_NAME = "지뢰찾기";
	private static final String GAME_DESCRIPTION = "지뢰를 피하세요!";
	
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
			initialize();
			
			run = true;
			
			while(run)
			{
				print();
				choice();
				print();
				action();
			}
			
		} while (restart());
	}
	
	// 다시시작
	private boolean restart()
	{
		return InputUtil.readBool("다시 시작하시겠습니까", "Y", "N");
	}
	
	private boolean run;
	private ICellBoard board;
	private int size;
	private int mineCount;
	
	private int playerRow;
	private int playerCol;
	private boolean isFirst;
	
	// 초기화
	private void initialize()
	{
		ConsoleUtil.clear();
		
		playerRow = -1;
		playerCol = -1;
		isFirst = true;
		
		size = InputUtil.readInt("사이즈를 입력하세요",10,20);
		mineCount = InputUtil.readInt("지뢰 개수를 입력하세요",size,size*3);
		
		board = new CellBoard();
		
		board.init(size, mineCount);
	}
	
	// 화면 출력
	private void print()
	{
		board.print();
	}
	
	// 사용자 선택
	private void choice()
	{
		do
		{
			playerRow = InputUtil.readInt("행을 선택", 1, size)-1;
			playerCol = InputUtil.readInt("열을 선택", 1, size)-1;
			
			if(board.isOpen(playerRow, playerCol))
			{
				System.out.println("이미 열린 칸입니다.");
			}
			
		} while (board.isOpen(playerRow, playerCol));
		
		board.choiceCell(playerRow, playerCol);
	}
	
	// 액션 처리
	private void action()
	{
		MenuUtil.showMenu(Action.values(), this);
	}
	
	private enum Action implements IMenu<Minesweeper>
	{
		OPEN("오픈","셀을 오픈합니다",(minesweeper)->
		{
			minesweeper.open();
		}),
		FLAG("깃발","깃발을 설치하거나 회수합니다",(minesweeper)->
		{
			minesweeper.toggleFlag();
		}),
		CHANCE("찬스","찬스 횟수를 소모해 해당 셀을 확인합니다",(minesweeper)->
		{
			minesweeper.chance();
		}),
		CANCEL("취소","선택을 취소합니다",(minesweeper)->
		{
			minesweeper.cancel();
		})
		;

		Action(String actionName, String actionDescription, Consumer<Minesweeper> order)
		{
			this.actionName = actionName;
			this.actionDescription = actionDescription;
			this.order = order;
		}
		
		private final String actionName;
		private final String actionDescription;
		private final Consumer<Minesweeper> order;
		
		@Override
		public String getName()
		{
			return actionName;
		}

		@Override
		public String getDescription()
		{
			return actionDescription;
		}

		@Override
		public void order(Minesweeper obj)
		{
			order.accept(obj);
		}
	}
	
	// 열기
	private void open()
	{
		if(isFirst)
		{
			isFirst = false;
			board.openFirst(playerRow, playerCol);
		}
		
		if(board.isClosed(playerRow, playerCol))
		{
			if(board.isMine(playerRow, playerCol))
			{
				finish(false);
				return;
			}
			else
			{
				board.openCell(playerRow, playerCol);
			}
		}
		
		cancel();
		
		if(board.isClear())
		{
			finish(true);
		}
	}
	
	// 깃발
	private void toggleFlag()
	{
		if(isFirst)
		{
			System.out.println("첫 오픈 전에는 깃발 설치 불가");
		}
		
		board.toggleFlagCell(playerRow, playerCol);
		cancel();
	}
	
	// 찬스
	private void chance()
	{
		if(isFirst)
		{
			System.out.println("첫 오픈 전에는 찬스 불가");
		}
	}
	
	// 선택 취소
	private void cancel()
	{
		board.cancelCell(playerRow, playerCol);
	}
	
	// 게임 종료
	private void finish(boolean win)
	{
		board.finish();
		board.print();
		System.out.println(win ? "승리" : "패배");
		run = false;
	}
}
