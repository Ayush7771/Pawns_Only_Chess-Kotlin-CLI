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
        println()
    }

    fun moveValidation(player: String): Boolean {
        println("$player's turn:")
        val input = readln()

        if (input == "exit") {
            exitProgram = true
            return true
        }

        try {
            if (input.length != 4 ||
                !horizontalLayer.containsKey("${input[0]}") ||
                !horizontalLayer.containsValue("${input[1]}".toInt() - 1) ||
                !horizontalLayer.containsKey("${input[2]}") ||
                !horizontalLayer.containsValue("${input[3]}".toInt() - 1)
            ) {
                println("Invalid Input")
                return false
            }
        } catch (e: Exception) {
            println("Invalid Input")
            return false
        }

        val c1 = horizontalLayer["${input[0]}"]!!
        val r1 = "${input[1]}".toInt() - 1
        val c2 = horizontalLayer["${input[2]}"]!!
        val r2 = "${input[3]}".toInt() - 1


        if (firstInGame) {
            when {
                (r1 == r2 && c1 == c2) && (board[r1][c1] == "B" || board[r1][c1] == " ") -> {
                    println("No white pawn at ${input[0]}${input[1]}")
                    return false
                }

                c1 != c2 && r1 == 1 && r2 == 2 && board[r1][c1] == "W" && board[r2][c2] == "B" -> {
                    board[r2][c2] = "W"
                    board[r1][c1] = " "
                    return true
                }

                c1 == c2 && r1 == 1 && (r2 == 2 || r2 == 3) && board[r2][c1] != "B" ->
                    when {
                        board[r1][c1] == "W" && board[r2][c2] == "B" -> {
                            board[r2][c2] = "W"
                            board[r1][c1] = " "
                            return true
                        }

                        board[r1][c1] == "W" -> {
                            board[r2][c2] = "W"
                            board[r1][c1] = " "
                            return true
                        }

                        board[r1][c1] == " " || board[r1][c1] == "B" -> {
                            println("No white pawn at ${input[0]}${input[1]}")
                            return false
                        }
                    }

                c1 != c2 && r1 in 2..<7 && r2 - r1 == 1 && board[r1][c1] == "W" && board[r2][c2] == "B" -> {
                    board[r2][c2] = "W"
                    board[r1][c1] = " "
                    return true
                }

                c1 == c2 && r1 in 2..<7 && r2 - r1 == 1 && board[r2][c2] != "B" ->
                    when {
                        board[r1][c1] == "W" -> {
                            board[r2][c2] = "W"
                            board[r1][c2] = " "
                            return true
                        }

                        board[r1][c1] == " " || board[r1][c1] == "B" -> {
                            println("No white pawn at ${input[0]}${input[1]}")
                            return false
                        }
                    }


            }
        }

        if (secondInGame) {
            when {

                (r1 == r2 && c1 == c2) && (board[r1][c1] == "W" || board[r1][c1] == " ") -> {
                    println("No black pawn at ${input[0]}${input[1]}")
                    return false
                }

                c1 != c2 && r1 == 6 && r2 == 5 && board[r1][c1] == "B" && board[r2][c2] == "W" -> {
                    board[r2][c2] = "B"
                    board[r1][c1] = " "
                    return true
                }

                c1 == c2 && r1 == 6 && (r2 == 5 || r2 == 4) && board[r2][c2] != "W" ->
                    when {
                        board[r1][c1] == "B" -> {
                            board[r2][c2] = "B"
                            board[r1][c2] = " "
                            return true
                        }

                        board[r1][c1] == " " || board[r1][c1] == "W" -> {
                            println("No black pawn at ${input[0]}${input[1]}")
                            return false
                        }
                    }

                c1 != c2 && r1 >= 1 && r1 <= 6 && r1 - r2 == 1 && board[r1][c1] == "B" && board[r2][c2] == "W" -> {
                    board[r2][c2] = "B"
                    board[r1][c1] = " "
                    return true
                }

                c1 == c2 && r1 >= 1 && r1 <= 6 && r1 - r2 == 1 && board[r2][c2] != "W" ->
                    when {
                        board[r1][c1] == "B" -> {
                            board[r2][c2] = "B"
                            board[r1][c2] = " "
                            return true
                        }

                        board[r1][c1] == " " || board[r1][c1] == "W" -> {
                            println("No black pawn at ${input[0]}${input[1]}")
                            return false
                        }
                    }
            }
        }

        println("Invalid Input")
        return false
    }
}