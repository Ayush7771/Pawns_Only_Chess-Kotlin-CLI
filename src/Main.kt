package chess
fun main(){
    println("Pawns-Only Chess")
}

object ChessBoard{
    val board = MutableList(8){
        MutableList(8){
            " "
        }
    }

    init {
        board[1] = MutableList(8){"B"}
        board[6] = MutableList(8){"W"}
    }

    fun printBoard(){
        println("  +---+---+---+---+---+---+---+---+")

    }
}