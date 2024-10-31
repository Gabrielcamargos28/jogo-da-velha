package com.example.jogo_da_velha

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var gameStatusTextView: TextView
    private lateinit var resetButton: Button
    private lateinit var boardButtons: Array<Button>
    private var currentPlayer = "X"
    private var board = Array(3) { arrayOfNulls<String>(3) }
    private var gameEnded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameStatusTextView = findViewById(R.id.gameStatusTextView)
        resetButton = findViewById(R.id.resetButton)
        boardButtons = arrayOf(
            findViewById(R.id.button1), findViewById(R.id.button2), findViewById(R.id.button3),
            findViewById(R.id.button4), findViewById(R.id.button5), findViewById(R.id.button6),
            findViewById(R.id.button7), findViewById(R.id.button8), findViewById(R.id.button9)
        )

        for ((index, button) in boardButtons.withIndex()) {
            button.setOnClickListener { onBoardButtonClick(it, index) }
        }

        resetButton.setOnClickListener { resetGame() }
        updateStatus("Player X's Turn")
    }

    private fun onBoardButtonClick(view: View, index: Int) {
        if (gameEnded) return
        val row = index / 3
        val col = index % 3

        if (board[row][col] == null) {
            (view as Button).text = currentPlayer
            board[row][col] = currentPlayer
            if (checkWinner()) {
                updateStatus("Player $currentPlayer Wins!")
                gameEnded = true
            } else if (isBoardFull()) {
                updateStatus("It's a Draw!")
                gameEnded = true
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                updateStatus("Player $currentPlayer's Turn")
            }
        }
    }

    private fun checkWinner(): Boolean {
        // Verificação de linhas, colunas e diagonais
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true
        return false
    }

    private fun isBoardFull(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == null) return false
            }
        }
        return true
    }

    private fun updateStatus(message: String) {
        gameStatusTextView.text = message
    }

    private fun resetGame() {
        board = Array(3) { arrayOfNulls<String>(3) }
        currentPlayer = "X"
        gameEnded = false
        updateStatus("Player X's Turn")

        for (button in boardButtons) {
            button.text = ""
        }
    }
}