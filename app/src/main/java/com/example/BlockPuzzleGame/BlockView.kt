package com.example.BlockPuzzleGame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BlockView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val block: Block = Block(context)

    private val paint = Paint().apply {
        color = Color.BLUE // Set the color to blue (adjust as needed)
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the Tetris grid
        for (x in 0 until block.gridWidth) {
            for (y in 0 until block.gridHeight) {
                if (block.grid[x][y]) {
                    paint.color = Color.GRAY
                    canvas.drawRect(x * block.blockSize.toFloat(), y * block.blockSize.toFloat(), (x + 1) * block.blockSize.toFloat(), (y + 1) * block.blockSize.toFloat(), paint)
                }
            }
        }

        // Draw the current Tetris piece
        block.currentPiece?.let { piece ->
            paint.color = Color.BLUE
            for (x in 0 until piece.shape.size) {
                for (y in 0 until piece.shape[0].size) {
                    if (piece.shape[x][y]) {
                        canvas.drawRect((piece.x + x) * block.blockSize.toFloat(), (piece.y + y) * block.blockSize.toFloat(), (piece.x + x + 1) * block.blockSize.toFloat(), (piece.y + y + 1) * block.blockSize.toFloat(), paint)
                    }
                }
            }
        }
    }

    // Handle user input for left movement
    fun moveLeft() {
        block.moveLeft()
        invalidate()
    }

    // Handle user input for right movement
    fun moveRight() {
        block.moveRight()
        invalidate()
    }

    // Handle user input for down movement
    fun moveDown() {
        //tetris.moveDown()
        invalidate()
    }

    // Handle user input for rotation
    fun rotate() {
        block.rotate()
        invalidate()
    }
}
