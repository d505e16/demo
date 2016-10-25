const row = 4;
const col = 5;

let board = [
	[],
	[],
	[],
	[],
	[],
];

let iterations = 0;

function minimax(board, depth) {
	iterations++;
	console.log(depth, board)
	// if s is terminal 

	// if player is max
	if (depth % 2 == 0) {
		
	}
  
	// if player is min
	else {

	}

	for (let i = 0; i < col; i++) {
		// make local clone of board
		let state = JSON.parse(JSON.stringify(board));
		// define active column
		let column = state[i];
		// if column is not full
		if (column.length < row) {
			// push 'piece' onto board
			column.push(depth % 2 == 0 ? 'X' : 'O');
			// run minimax with new board
			minimax(state, JSON.parse(JSON.stringify(depth + 1)));
		}
	}
}

minimax(board, 0);

console.log(iterations);