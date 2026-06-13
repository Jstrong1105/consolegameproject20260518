package game.minesweeper;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

import common.ConsoleUtil;
import common.IMenu;
import common.InputUtil;
import common.MenuUtil;
import domain.DoubleActionTemplate;

/**
 * 지뢰 찾기 App 클래스
 * 출력 → 선택 → 출력 → 액션 
 */
class Minesweeper extends DoubleActionTemplate
{
	private final ICellBoard board;
	private final ICellPrinter printer;
	private int size;
	private int mineCount;
	
	private int playerRow;
	private int playerCol;
	private boolean isFirst;
	private Instant startTime;
	private int chance;
	
	Minesweeper(ICellBoard board, ICellPrinter printer)
	{
		this.board = board;
		this.printer = printer;
	}
	
	@Override
	protected void init()
	{
		ConsoleUtil.clear();
		startTime = Instant.now();
		
		playerRow = -1;
		playerCol = -1;
		isFirst = true;
		chance = 3;
		
		size = InputUtil.readInt("사이즈를 입력하세요",10,20);
		mineCount = InputUtil.readInt("지뢰 개수를 입력하세요",size,size*3);
		
		board.init(size, mineCount);
	}
	
	@Override
	protected void render()
	{
		printer.print(board.getBoard());
		System.out.println("지뢰의 수 : " + board.getMineCount());
		System.out.println("깃발의 수 : " + board.getFlagCount());
		System.out.println("남은 찬스 : " + chance);
	}
	
	@Override
	protected void firstAction()
	{
		do
		{
			playerRow = InputUtil.readInt("행을 선택", 1, size)-1;
			playerCol = InputUtil.readInt("열을 선택", 1, size)-1;
			
			if(board.isOpen(playerRow, playerCol))
				System.out.println("이미 열린 칸입니다.");
			
		} while (board.isOpen(playerRow, playerCol));
		
		board.choiceCell(playerRow, playerCol);
	}
	
	@Override
	protected void secondAction()
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
		else
		{
			InputUtil.pause("닫혀있는 셀만 열 수 있습니다.");
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
			InputUtil.pause("첫 오픈 전 깃발 사용 불가");
			cancel();
			return;
		}
		
		board.toggleFlagCell(playerRow, playerCol);
		cancel();
	}
	
	// 찬스
	private void chance()
	{
		if(isFirst)
		{
			InputUtil.pause("첫 오픈 전 찬스 사용 불가");
			cancel();
			return;
		}
		
		if(chance <= 0)
		{
			InputUtil.pause("찬스를 모두 사용했습니다.");
			cancel();
			return;
		}
		
		chance--;
		
		if(board.isMine(playerRow, playerCol))
		{	
			InputUtil.pause("해당 셀은 지뢰입니다.");
			
			if(!board.isFlag(playerRow, playerCol))
				toggleFlag();
			return;
		}
		else
		{
			InputUtil.pause("해당 셀은 지뢰가 아닙니다.");
			if(board.isFlag(playerRow, playerCol))
				toggleFlag();
			open();
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
		printer.print(board.getBoard());
		System.out.println(win ? "승리" : "패배");
		
		if(win)
			System.out.println("클리어 타임 : " + Duration.between(startTime, Instant.now()).getSeconds() + "초");
		
		endGame();
	}
}
