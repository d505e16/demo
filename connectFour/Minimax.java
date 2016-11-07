package connectFour;

public class Minimax {
	private final String boardString;
	private Character[][] board;
	private final int row, col;
	private int depth; 
	private Character player;
	
	Minimax(String s){
		this.boardString = s;
		Board b = new Board(s);
		this.board = b.getBoard();
		this.row = b.getRow();
		this.col = b.getCol();
		this.depth = 1;		
		this.player = getPlayer();
	}
	
	private Character[][] getboard(){
		return this.board;
	}
	
	private Character getPlayer(){
		Character player;
		if( boardString.length() % 2 == 0 ){
			player = 'x';
		} else {
			player = 'o';
		}
		return player;
	}

	public void bestMove(){
		double[] bestMoves = new double[col];
		int tempDepth = depth;
		Character[][] tempBoard = copyBoard(this.board);
		for(int i = 0; i < col; i++){//den der skal bruges
		//for(int i = 1; i < 2; i++){//tester
			int row = firstEmptyInCol(tempBoard, i);
			if(row != -1){
				tempBoard[row][i] = player;
				//display(tempBoard);
				if(isTerminal(tempBoard, row, i)){
					//System.out.println("terminal");
					bestMoves[i] = terminalValue(tempDepth) / tempDepth;
				} else {
					//System.out.println("not terminal");
					bestMoves[i] = 1.0; //tester
					bestMoves[i] = getBoardValue(tempBoard, tempDepth); 
				}
			}
			tempBoard[row][i] = null;
			//tempBoard = copyBoard(this.board); //reset
		}
		for(int i = 0; i < bestMoves.length; i++){
			System.out.println("If player '" + player + "' places a tile at " + i + " the value is:" + bestMoves[i]);
		}
	}//end bestMove
	
	
	//finder nodes værdien i forhod til hvis tur det er og hvor dybt vil er ned i træret
	private double getBoardValue(Character[][] board, int depth) {
		int tempDepth = depth + 1;
		//double result = 0.0;
		double[] moves = new double[col];
		for(int i = 0; i < col; i++){
		//for(int i = 0; i < 1; i++){//tester
			Character[][] tempBoard = copyBoard(board);
			int row = firstEmptyInCol(board, i);
			if(row != -1){
				tempBoard[row][i] = findTurnInDepth(tempDepth);
				//display(tempBoard);
				if(isTerminal(tempBoard, row, i)){
					moves[i] = terminalValue(tempDepth) / tempDepth;
					//System.out.println("terminal at depth " + tempDepth + " with value: " + moves[i]);
				} else {
					moves[i] = getBoardValue(tempBoard, tempDepth); 
					//System.out.println("not terminal");
				}
			}
			tempBoard = copyBoard(board);
		}
		if(tempDepth % 2 == 0){
			return findMin(moves);
		} else {
			return findMax(moves);
		}
		//return result;
	}
	
	
	private int terminalValue(int depth){
		if(player == findTurnInDepth(depth)){
			return 100;
		} else {
			return -100; 
		}
	}
	
	private Character findTurnInDepth(int depth) {
		if(depth % 2 == 1){//the depth starts at 1 - change nothing
			return this.player;
		} else {
			if(player.equals('x')){
				return 'o';
			} else {
				return 'x';
			}
		}
	}

	private Character[][] copyBoard(Character[][] board){//copy not refrence, one loop might be enough
		Character[][] newBoard = new Character[row][col];
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				newBoard[i][j] = board[i][j];
			}
		}
		return newBoard;
	}
	
	private double findMin(double[] moves) {
		double result = 0.0;
		System.out.println("finding MIN in: ");
		for(int i = 0; i < moves.length; i++){
			System.out.print("(" + i + " - " + moves[i] + ") ,");
			if(result > moves[i]){
				result = moves[i];
			}
		}
		System.out.println("\n" + result + " is the smallest");
		return result;
	}
	
	private double findMax(double[] moves) {
		double result = 0.0;
		System.out.println("finding MAX in: ");
		for(int i = 0; i < moves.length; i++){
			System.out.print("(" + i + " - " + moves[i] + ") ,");
			if(result < moves[i]){
				result = moves[i];
			}
		}
		System.out.println("\n" + result + " is the greatest");
		return result;
	}
	
	private int firstEmptyInCol( Character[][] board, int n ) {
		for(int i = 0; i < row; i++){
			if(board[i][n] == null){
				return i;
			}
		}
		return -1;
	}// end firstEmptyIncol
	
	public Boolean isTerminal(Character[][] board, int row, int col){
		boolean winnerFound = false;
		if(horizontalWinCheck(board, row, col)){
			winnerFound = true;
		} else if(vertiaclWinCheck(board, row, col)){
			winnerFound = true;
		} else if(diagonalLeftToRight(board, row, col)){
			winnerFound = true;
		} else if(diagonalRightToLeft(board, row, col)){
			winnerFound = true;
		}
		return winnerFound;
	}

	private boolean horizontalWinCheck(Character[][] board, int row, int col) {
		int connectedTiles = 0;
		int iteratorDown = col;
		int iteratorUp = col + 1;
		Character player = board[row][col];
		//System.out.println("player: " + player);
		System.out.println("Start: (" + row + ", " + col + "): ");
		while(iteratorDown >= 0 && board[row][iteratorDown] != null && board[row][iteratorDown].equals(player) ){
			connectedTiles++;
			//System.out.println("+: (" + row + ", " + iteratorDown + "): "+ connectedTiles);
			iteratorDown--;
		}
		while(iteratorUp < this.col && board[row][iteratorUp] != null && board[row][iteratorUp].equals(player)){
			connectedTiles++;
			//System.out.println("+: (" + row + ", " + iteratorUp + "): "+ connectedTiles);
			iteratorUp++;
		}
		
		System.out.println("horizontal: " + connectedTiles);
		if(connectedTiles >= 4){
			return true;
		}
		return false;
	}//end horizontalWinCheck
	
	private boolean vertiaclWinCheck(Character[][] board, int row, int col) {
		int connectedTiles = 0;
		int iteratorDown = row;
		int iteratorUp = row + 1;
		Character player = board[row][col];
		//System.out.println("player: " + player);
		//System.out.println("Start: (" + row + ", " + col + "): ");
		while(iteratorDown >= 0 && board[iteratorDown][col] != null && board[iteratorDown][col].equals(player) ){
			connectedTiles++;
			//System.out.println("+: (" + iteratorDown + ", " + col + "): "+ connectedTiles);
			iteratorDown--;
		}
		while(iteratorUp < this.row && board[iteratorUp][col] != null && board[iteratorUp][col].equals(player)){
			connectedTiles++;
			//System.out.println("+: (" + iteratorUp + ", " + col + "): "+ connectedTiles);
			iteratorUp++;
		}
		
		System.out.println("vertical: " + connectedTiles);
		if(connectedTiles >= 4){
			return true;
		}
		return false;
	}//end vertiaclWinCheck	
	

	private boolean diagonalLeftToRight(Character[][] board, int row, int col) {
		int connectedTiles = 0;
		int iteratorDownRow = row;
		int iteratorDownCol = col;
		
		int iteratorUpRow = row + 1;
		int iteratorUpCol = col + 1;
		
		Character player = board[row][col];
		//System.out.println("player: " + player);
		//System.out.println("Start: (" + row + ", " + col + "): ");
		while(iteratorDownRow >= 0 && iteratorDownCol >= 0 && board[iteratorDownRow][iteratorDownCol] != null && board[iteratorDownRow][iteratorDownCol].equals(player) ){
			connectedTiles++;
			//System.out.println("+: (" + iteratorDownRow + ", " + iteratorDownCol + "): "+ connectedTiles);
			iteratorDownRow--;
			iteratorDownCol--;
		}
		while(iteratorUpRow < this.row && iteratorUpCol < this.col && board[iteratorUpRow][iteratorUpCol] != null && board[iteratorUpRow][iteratorUpCol].equals(player)){
			connectedTiles++;
			//System.out.println("+: (" + iteratorUpRow + ", " + iteratorUpCol + "): "+ connectedTiles);
			iteratorUpRow++;
			iteratorUpCol++;
		}
		
		System.out.println("Diagonal Left To Right: " + connectedTiles);
		if(connectedTiles >= 4){
			return true;
		}
		return false;
	}
	
	private boolean diagonalRightToLeft(Character[][] board, int row, int col) {
		int connectedTiles = 0;
		int iteratorDownRow = row;
		int iteratorDownCol = col;
		
		int iteratorUpRow = row + 1;
		int iteratorUpCol = col - 1;
		
		Character player = board[row][col];
		//System.out.println("player: " + player);
		//System.out.println("Start: (" + row + ", " + col + "): ");
		while(iteratorDownRow >= 0 && iteratorDownCol < this.col && board[iteratorDownRow][iteratorDownCol] != null && board[iteratorDownRow][iteratorDownCol].equals(player) ){
			connectedTiles++;
			//System.out.println("+: (" + iteratorDownRow + ", " + iteratorDownCol + "): "+ connectedTiles);
			iteratorDownRow--;
			iteratorDownCol++;
		}
		while(iteratorUpRow < this.row && iteratorUpCol >= 0 && board[iteratorUpRow][iteratorUpCol] != null && board[iteratorUpRow][iteratorUpCol].equals(player)){
			connectedTiles++;
			//System.out.println("+: (" + iteratorUpRow + ", " + iteratorUpCol + "): "+ connectedTiles);
			iteratorUpRow++;
			iteratorUpCol--;
		}
		
		System.out.println("Diagonal Left To Right: " + connectedTiles);
		if(connectedTiles >= 4){
			return true;
		}
		return false;
	}//end diagonalRightToLeft
	
	public static void display(Character array[][]){
		for(int row = 0; row < array.length; row++){
			for(int column = 0; column < array[row].length; column++){
				System.out.print(array[row][column]+ "\t");
			}
			System.out.println();
		}
	}//end display
	
	public static void main(String[] args) {
		//String s = "0101";
		String s = "010101344322324";
		//Board board = new Board(s);
		//System.out.println();
		Minimax mm = new Minimax(s);
		display(mm.getboard());
		//System.out.println(mm.isTerminal(mm.getboard(), 2, 1));
		mm.bestMove();
	}

	
}//end Minimax
