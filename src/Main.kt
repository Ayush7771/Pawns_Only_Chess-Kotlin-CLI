package chess

import kotlin.math.abs

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

    var whitePawns = 8
    var blackPawns = 8

    var isEnPassant = false

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

    fun whiteWinsCheck() {
        //Win conditions for White
        if (blackPawns == 0 || board[7].contains("W")) {
            println("White Wins!")
            exitProgram = true
        }
    }

    fun blackWinsCheck() {
        //Win conditions for Blacl
        if (whitePawns == 0 || board[0].contains("B")) {
            println("Black Wins!")
            exitProgram = true
        }
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
                //No White pawn at spot
                board[r1][c1] != "W" -> {
                    println("No white pawn at ${input[0]}${input[1]}")
                    return false
                }

                //Adjacent Lines (2 no. row) first moves without captur
                c1 == c2 && r1 == 1 -> {
                    when {
                        //first vertical single move
                        r2 == 2 && board[r2][c2] == " " -> {
                            board[r2][c2] = "W"
                            board[r1][c1] = " "
                            return true
                        }
                        //first vertical double move
                        r2 == 3 && board[r2][c2] == " " && board[r2 - 1][c2] == " " -> {
                            board[r2][c2] = "W"
                            board[r1][c1] = " "
                            return true
                        }
                    }
                }

                //Adjacent Lines (2 no. row) first moves with capture
                abs(c2 - c1) == 1 && r1 == 1 && r2 == 2 && board[r2][c2] == "B" -> {
                    board[r2][c2] = "W"
                    board[r1][c1] = " "
                    blackPawns--
                    whiteWinsCheck()
                    return true
                }

                //Other Lines Moves excluding Adjacent Line (2 no.)
                r2 - r1 == 1 -> {
                    when {
                        //move only
                        c1 == c2 && board[r2][c2] == " " -> {
                            board[r2][c2] = "W"
                            board[r1][c1] = " "
                            whiteWinsCheck()
                            return true
                        }
                        //move diagonally && captured
                        abs(c2 - c1) == 1 && board[r2][c2] == "B" -> {
                            board[r2][c2] = "W"
                            board[r1][c1] = " "
                            blackPawns--
                            whiteWinsCheck()
                            return true
                        }
                    }
                }
            }


        }

        if (secondInGame) {
            when {
                //No Black pawn at spot
                board[r1][c1] != "B" -> {
                    println("No black pawn at ${input[0]}${input[1]}")
                    return false
                }

                //Adjacent Lines (7 no. row) first moves without captur
                c1 == c2 && r1 == 6 -> {
                    when {
                        //first vertical single move
                        r2 == 5 && board[r2][c2] == " " -> {
                            board[r2][c2] = "B"
                            board[r1][c1] = " "
                            return true
                        }
                        //first vertical double move
                        r2 == 4 && board[r2][c2] == " " && board[r2 + 1][c2] == " " -> {
                            board[r2][c2] = "B"
                            board[r1][c1] = " "
                            return true
                        }
                    }
                }

                //Adjacent Lines (7 no. row) first moves with capture
                abs(c2 - c1) == 1 && r1 == 6 && r2 == 5 && board[r2][c2] == "W" -> {
                    board[r2][c2] = "B"
                    board[r1][c1] = " "
                    whitePawns--
                    blackWinsCheck()
                    return true
                }

                //Other Lines Moves excluding Adjacent Line (7 no.)
                r1 - r2 == 1 -> {
                    when {
                        //move only
                        c1 == c2 && board[r2][c2] == " " -> {
                            board[r2][c2] = "B"
                            board[r1][c1] = " "
                            blackWinsCheck()
                            return true
                        }
                        //move diagonally && captured
                        abs(c2 - c1) == 1 && board[r2][c2] == "W" -> {
                            board[r2][c2] = "B"
                            board[r1][c1] = " "
                            whitePawns--
                            blackWinsCheck()
                            return true
                        }
                    }
                }
            }

        }

        println("Invalid Input")
        return false
    }
}