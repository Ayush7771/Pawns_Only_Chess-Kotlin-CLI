package chess

fun main() {
    println("Pawns-Only Chess")
    ChessBoard.printBoard()
}

object ChessBoard {
    val board = MutableList(8) {
        MutableList(8) {
            " "
        }
    }

    init {
        board[1] = MutableList(8) { "W" }
        board[6] = MutableList(8) { "B" }
    }

    fun printBoard() {
        println("  +---+---+---+---+---+---+---+---+")
        for (i in board.lastIndex downTo 0) {
            print("${i + 1} |")

            for (j in board[i].indices) {
                print(" ${board[i][j]} |")
            }

            println()
            println("  +---+---+---+---+---+---+---+---+")
        }
        println("    a   b   c   d   e   f   g   h")


    }
}