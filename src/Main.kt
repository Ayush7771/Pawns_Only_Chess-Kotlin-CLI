package chess

fun main() {
    println("Pawns-Only Chess")
    println("First Player's name:")
    val firstPlayer = readln()
    println("Second Player's name:")
    val secondPlayer = readln()
    ChessBoard.printBoard()

    main@ while (true) {
        while (true) {
            ChessBoard.firstInGame = true
            val result = ChessBoard.moveValidation(firstPlayer)
            if (ChessBoard.exitProgram) {
                println("Bye!")
                break@main
            }
            if (result) {
                ChessBoard.firstInGame = false
                ChessBoard.printBoard()
                break
            }
        }
        while (true) {
            ChessBoard.secondInGame = true
            val result = ChessBoard.moveValidation(secondPlayer)
            if (ChessBoard.exitProgram) {
                println("Bye!")
                break@main
            }
            if (result) {
                ChessBoard.secondInGame = false
                ChessBoard.printBoard()
                break
            }
        }
    }
}

object ChessBoard {
    val board = MutableList(8) {
        MutableList(8) {
            " "
        }
    }

    val horizontalLayer = mutableMapOf(
        "a" to 0,
        "b" to 1,
        "c" to 2,
        "d" to 3,
        "e" to 4,
        "f" to 5,
        "g" to 6,
        "h" to 7
    )

    var exitProgram = false

    var firstInGame = false
    var secondInGame = false

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
        print("  ")
        for (i in horizontalLayer.keys) {
            print("  $i ")
        }
        println(" ")
    }

    fun moveValidation(player: String): Boolean {
        println("$player's turn:")
        val input = readln()

        if (input == "exit") {
            exitProgram = true
            return true
        }

        if (!horizontalLayer.containsKey("${input[0]}") ||
            !horizontalLayer.containsValue("${input[1]}".toInt() - 1) ||
            !horizontalLayer.containsKey("${input[2]}") ||
            !horizontalLayer.containsValue("${input[3]}".toInt() - 1) ||
            input.length != 4
        ) {
            println("Invalid Input")
            return false
        }

        if (firstInGame) {
            /*
            if ("${input[1]}".toInt() - 1 == 1 && board[1][horizontalLayer["${input[0]}"]!!] == "W"){
                if ("${input[0]}" == "${input[2]}" && )
            }

            if (board["${input[1]}".toInt() - 1][horizontalLayer["${input[0]}"]!!] == "W"){
                if ((board["${input[3]}".toInt() - 1][horizontalLayer["${input[2]}"]!!] == " ")){}
            } */

            when {
                "${input[1]}".toInt() - 1 == 1 && board[1][horizontalLayer["${input[0]}"]!!] == "W" ->
                    when {
                            "${input[1]}".toInt() - 1 == 1 && "${input[3]}".toInt() - 1 == 2 ->
                    }
            }
        }


        return true
    }
}