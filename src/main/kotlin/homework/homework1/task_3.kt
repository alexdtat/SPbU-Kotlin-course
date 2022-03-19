package homework.homework1

import libraries.performedCommandStorage.PerformedCommandStorage

fun processCommand(inputSplitted: List<String>, commandStorage: PerformedCommandStorage) {
    val numberOfArguments = inputSplitted.size - 1

    when (inputSplitted[0]) {
        "" -> println("Null input. Try again:")
        "abort" -> {}
        "undo" -> commandStorage.undo()
        "print" -> println("\nThe processed Int list:\n" + commandStorage.processedList.joinToString(", "))
        "addToEnd" -> {
            if (numberOfArguments < 1)
                println("One argument needed.")
            else
                commandStorage.addToEnd(inputSplitted[1].toInt())
        }
        "addToBeginning" -> {
            if (numberOfArguments < 1)
                println("One argument needed.")
            else
                commandStorage.addToBeginning(inputSplitted[1].toInt())
        }
        "swap" -> {
            if (numberOfArguments < 2)
                println("Two arguments needed.")
            else
                commandStorage.swap(inputSplitted[1].toInt(), inputSplitted[2].toInt())
        }
        else -> println("Unknown command.")
    }
}

fun main() {
    val commandStorage = PerformedCommandStorage()
    var currentCommand = ""

    println(
        "Use 'addToBeginning x', 'addToEnd x', 'swap i j', 'undo' commands to process the list of integers, " +
            "'print' to print the list and 'abort' to abort the session:"
    )
    while (currentCommand != "abort") {
        var inputSplitted: List<String> = emptyList()
        try {
            println("Enter your command:")
            val input = readLine()
            if (input == null) {
                println("Null input. Please, try again:")
            } else {
                inputSplitted = input.split(' ')
                currentCommand = inputSplitted[0]
            }

            processCommand(inputSplitted, commandStorage)
        } catch (exceptionFormat: java.lang.NumberFormatException) {
            println(exceptionFormat.message + " Cannot convert into Int.")
        } catch (exceptionIllegal: IllegalArgumentException) {
            println(exceptionIllegal.message)
            when (exceptionIllegal.message) {
                "You cannot undo a command in the empty storage." -> println("Command storage is empty.")
                "Cannot swap in the empty storage." -> println("Add some elements.")
                "Positions must be integers in range 0..${commandStorage.processedList.size - 1}." ->
                    println("Out of bounds positions.")
                else -> throw exceptionIllegal
            }
        }
    }
}
