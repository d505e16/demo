package connectFour;

public class Board {
	
	private final int ROW = 4, COL = 5;
	private Character[][] board;
	
	Board(String s){
		board = new Character[this.ROW][this.COL];
		createBoard(s);
		
	}
	
	public Character[][] getBoard(){
		return board;
	}

	public int getRow(){
		return this.ROW;
	}
	
	public int getCol(){
		return this.COL;
	}
	
	private void createBoard(String s){
		Character player; 
		System.out.println(s);
		for(int i = 0; i < s.length(); i++){
			int col = Character.getNumericValue(s.charAt(i));
			int row = firstEmptyInCol(col);
			if(row != -1){
				if( i % 2 == 0){
					player = 'x'; 
				} else{
					player = 'o';
				}
				board[row][col] = player;
			} else {
				System.out.println("Too many tiles in col" + col);
			}
		}
	}//end createBoard

	private int firstEmptyInCol( int n ) {
		for(int i = 0; i < ROW; i++){
			if(board[i][n] == null){
				return i;
			}
		}
		return -1;
	}// end firstEmptyIncol

	
}// end board
