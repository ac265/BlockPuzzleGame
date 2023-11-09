package com.example.BlockPuzzleGame

import android.content.Context
import android.view.View
import android.widget.Toast

class Block(context: Context?) : View(context) {

    var score: Int = 0
    var level: Int = 1
    val blockSize = 50 // Adjust the size as needed
    val gridWidth = 10
    val gridHeight = 20

    var grid = Array(gridWidth) { BooleanArray(gridHeight) }

    // Initialize the Tetris piece
    var currentPiece: TetrisPiece = TetrisPiece()

    init {
        initGame()
    }

    private fun initGame() {
        // Initialize the grid with empty cells
        for (x in 0 until gridWidth) {
            grid[x][y.toInt()] = false
        }

        // Initialize game variables, such as score, level, etc.
        score = 0
        level = 1

        // Start a new piece
        start()
    }

    fun start() {

        spawnPiece()
        moveDown()

        /*while (moveDown()) {
            // Continue moving the piece down until it can't go further
        }*/


        placePieceOnGrid()
        clearRows()
    }

    public fun spawnPiece() {
        // Create a new Tetris piece
        currentPiece = TetrisPiece()

        // Set the initial position of the piece at the top center
        currentPiece.x = gridWidth / 2
        currentPiece.y = 0

        // Check for collision with existing blocks
        if (checkCollision()) {
            // Game over condition: The new piece collides with existing blocks at the top
            gameOver()
            return
        }
    }

    private fun clearRows() {
        // Check and clear full rows
        // Implement logic to shift rows down when a row is cleared
        var row = height - 1

        while (row >= 0) {
            if (isRowFull(row)) {
                clearRow(row)
                shiftRowsDown(row)
            } else {
                row--
            }
        }
    }

    private fun isRowFull(row: Int): Boolean {
        for (cell in grid[row]) {
            if (!cell) {
                return false
            }
        }
        return true
    }

    private fun clearRow(row: Int) {
        for (i in grid[row].indices) {
            grid[row][i] = false
        }
    }

    private fun shiftRowsDown(startRow: Int) {
        for (row in startRow - 1 downTo 0) {
            for (col in grid[row].indices) {
                grid[row + 1][col] = grid[row][col]
            }
        }
    }

    private fun checkCollision(): Boolean {
        val pieceShape = currentPiece.shape
        val pieceX = currentPiece.x
        val pieceY = currentPiece.y

        // Check for collisions with existing blocks in the grid
        for (x in 0 until pieceShape.size) {
            for (y in 0 until pieceShape[0].size) {
                if (pieceShape[x][y]) {
                    val gridX = pieceX + x
                    val gridY = pieceY + y

                    // Check if the piece is outside the grid or collides with existing blocks
                    if (gridX < 0 || gridX >= gridWidth || gridY < 0 || gridY >= gridHeight || grid[gridX][gridY]) {
                        return true // Collision detected
                    }
                }
            }
        }

        return false // No collision
    }

    private fun gameOver() {
        // Display a game over message (you can replace this with your own UI or actions)
        Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT).show()
        // Reset the score and level
        resetGameState()
        // Restart the game
        initGame()
        // Redraw the view
        invalidate()
    }

    private fun resetGameState() {
        // Reset game state variables (score, level, etc.)
        score = 0
        level = 1
        // Add more variables as needed
    }

    fun moveLeft() {
        currentPiece?.let { piece ->
            piece.x -= 1
            if (checkCollision()) {
                piece.x += 1 // Undo the move if there's a collision
            }
        }
    }

    fun moveRight() {
        currentPiece?.let { piece ->
            piece.x += 1
            if (checkCollision()) {
                piece.x -= 1 // Undo the move if there's a collision
            }
        }
    }

    fun moveDown() {
        currentPiece?.let { piece ->
            piece.y += 1
            if (checkCollision()) {
                piece.y -= 1 // Undo the move if there's a collision
                placePieceOnGrid()
                spawnPiece()
            }
            redrawGrid()
        }
    }

    fun rotate() {
        // Rotate the current piece
        // Implement as needed
        currentPiece?.let { piece ->

            if (!checkCollision()) {
                piece.shape = currentPiece.shape
                redrawGrid()
            }
        }
    }

    private fun rotateClockwise() {

        currentPiece?.let { piece ->
            // Update the grid based on the current piece's position
            for (x in 0 until piece.shape.size) {
                for (y in 0 until piece.shape[0].size) {
                    if (piece.shape[x][y]) {
                        val gridX = piece.y - 1
                        val gridY = piece.x
                        grid[gridX][gridY] = true
                    }
                }
            }
        }
    }

    private fun placePieceOnGrid() {
        currentPiece?.let { piece ->
            // Update the grid based on the current piece's position
            for (x in 0 until piece.shape.size) {
                for (y in 0 until piece.shape[0].size) {
                    if (piece.shape[x][y]) {
                        val gridX = piece.x + x
                        val gridY = piece.y + y
                        grid[gridX][gridY] = true
                    }
                }
            }
        }
    }

    fun redrawGrid() {
        // Assuming you have a 2D array to represent the game grid
        for (row in grid) {
            for (cell in row) {
                // Update the visualization of each cell based on its state (occupied or not)
                if (cell) {
                    // Draw an occupied cell
                    print("X ")
                } else {
                    // Draw an empty cell
                    print(". ")
                }
            }
            println() // Move to the next row
        }
    }

    inner class TetrisPiece {

        // Define Tetris piece shapes
        private val pieces = arrayOf(
            arrayOf(booleanArrayOf(true, true, true, true)),  // I-piece
            arrayOf(booleanArrayOf(true, true, true), booleanArrayOf(false, true, false)),  // T-piece
            arrayOf(booleanArrayOf(true, true, true), booleanArrayOf(true, false, false)),  // L-piece
            arrayOf(booleanArrayOf(true, true, true), booleanArrayOf(false, false, true)),  // J-piece
            arrayOf(booleanArrayOf(true, true, false), booleanArrayOf(false, true, true)),  // S-piece
            arrayOf(booleanArrayOf(false, true, true), booleanArrayOf(true, true, false)),  // Z-piece
            arrayOf(booleanArrayOf(true, true, true, true)),  // Square-piece1
            arrayOf(booleanArrayOf(true)),  // Square-piece2
            arrayOf(booleanArrayOf(true, true)),  // Square-piece3
            arrayOf(booleanArrayOf(true, true, true)),  // Square-piece4
            arrayOf(booleanArrayOf(true, true, true, true, true)),  // Square-piece5
            // Add more piece shapes as needed
        )

        var shape: Array<BooleanArray> = pieces.random() // Randomly select a piece shape
        var x: Int = 0
        var y: Int = 0
    }
}
