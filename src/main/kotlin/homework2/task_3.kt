package main.kotlin.homework2

fun main() {
    val myCommandStorage = main.libraries.PerformedCommandStorage.PerformedCommandStorage()
    var currentCommand: String = ""

    println(
        "Use 'addToBeginning x', 'addToEnd x', 'swap i j', 'undo' commands to process list of integers" +
                "and 'abort' to abort the session:"
    )
    while (currentCommand != "abort") {
        try {
            println("Enter your command:")
            val input = readLine() ?: throw NullPointerException("Null input.")
            val inputSplitted = input.split(' ')
            currentCommand = inputSplitted[0]

            when (currentCommand) {
                "abort" -> {}
                "undo" -> {}
                else -> if (inputSplitted.size <= 1) throw IllegalArgumentException("Lack of arguments.")
            }

            when (currentCommand) {
                "abort" -> {}
                "undo" -> myCommandStorage.undo()
                "addToEnd" -> myCommandStorage.addToEnd(inputSplitted[1].toInt())
                "addToBeginning" -> myCommandStorage.addToBeginning(inputSplitted[1].toInt())
                "swap" -> {
                    if (inputSplitted.size <= 2) throw IllegalArgumentException("One argument instead of two.")
                    myCommandStorage.swap(inputSplitted[1].toInt(), inputSplitted[2].toInt())
                }
                else ->
                    println("Unknown command.")
            }
        } catch (exception: Exception) {
            when (exception) {
                is java.lang.NumberFormatException -> println("Cannot convert into Int.")
                is IllegalArgumentException -> {
                    when (exception.message) {
                        "You cannot undo a command in the empty storage." -> println("Command storage is empty.")
                        "Positions must be integers in range 0..${myCommandStorage.getListOfInts().size - 1}." ->
                            println("Out of bounds positions.")
                        "Lack of arguments." -> println("Arguments needed.")
                        "One argument instead of two." -> println("Swap command needs 2 arguments.")
                        else -> throw exception
                    }
                }
                else -> throw exception
            }
        }
    }
}