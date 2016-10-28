function printBoard(board) {
	console.log('---------------------------------------------------');
	for (let i = 0; i < board[0].length; i++) {
		let row = []
		for (let j = 0; j < board.length; j++) {
			let field = board[j][i];
			row.push(field ? '"' + field + '"' : '" "');
		}
		console.log(row.join('\t'));
	}
	console.log('---------------------------------------------------');
}

function generateBoard(rows, cols) {
	let board = [];
	
	for (let i = 0; i < cols; i++) {
		board[i] = [];
	
		for (let j = 0; j < rows; j++) {
			board[i][j] = null;
		}
	}

	return board;
}

function findFreeRow(board, col) {
	for (let i = board[col].length - 1; i >= 0; i--) {
		let field = board[col][i];
		if (field === null) {
			return i;
		}
	}
}

function insertPiece(board, col, piece) {
	for (let i = board.length - 1; i >= 0; i--) {
		if (board[col][0] !== null) {
			console.log('column is full')
			break;
		}
		if (board[col][i] === null) {
			board[col][i] = piece;
			break;
		}
	}
}

function vertical(board, col, piece) {
	let state = false;
	let row = findFreeRow(board, col);
	let inARow = 0;

	for (let j = row + 1; j < board[col].length; j++) {
		if (board[col][j] == piece) {
			inARow++;
		} else {
			return false;
		}
		if (inARow == 3) {
			return true;
		}
	}

	return state;
}

function horizontal(board, col, piece) {
	let row = findFreeRow(board, col);
	let leftInARow = 0;
	let rightInARow = 0;

	for (let i = col - 1; i >= 0; i--) { // left
		if (board[i][row] == piece) {
			leftInARow++;
		} else {
			break;
		}
	}

	for (let i = col + 1; i < board.length; i++) { // right
		if (board[i][row] == piece) {
			rightInARow++;
		} else {
			break;
		}
	}

	if (leftInARow + rightInARow >= 4) {
		return true;
	}
	return false;
}

function diagonal(board, col, piece) {
	//
}

function minimax(board, depth) {
	board.forEach((col, i) => {
		let state = JSON.parse(JSON.stringify(board));
		insertPiece(state, i, depth % 2 == 0 ? 'X' : 'O');
		minimax(state, JSON.parse(JSON.stringify(depth + 1)));
		printBoard(state);
	})
}

const board = generateBoard(6, 7);

minimax(board, 0);


//console.log(vertical(board, 3, 'X'));
//console.log(horizontal(board, 3, 'X'));
//console.log(diagonal(board, 3, 'X'));