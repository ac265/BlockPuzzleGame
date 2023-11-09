package com.example.BlockPuzzleGame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var blockView: BlockView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blockView = BlockView(this)
        setContentView(blockView)
    }
}