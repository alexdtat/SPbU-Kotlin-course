package homework.homework1

import libraries.performedCommandStorage.AddToBeginning
import libraries.performedCommandStorage.AddToEnd
import libraries.performedCommandStorage.Move
import libraries.performedCommandStorage.PerformedCommandStorage

fun PerformedCommandStorage.addToEnd(addedNumber: Int) {
    execute(AddToEnd(addedNumber))
}

fun PerformedCommandStorage.addToBeginning(addedNumber: Int) {
    execute(AddToBeginning(addedNumber))
}

fun PerformedCommandStorage.move(origin: Int, destination: Int) {
    execute(Move(origin, destination))
}

fun numberOfArgumentsIsCorrect(numberOfArguments: Int, command: String): Boolean {
    val argumentsNeeded = when (command) {
        PossibleCommands.ADD_TO_BEGINNING.stringCommand -> 1
        PossibleCommands.ADD_TO_END.stringCommand -> 1
        PossibleCommands.MOVE.stringCommand -> 2
        else -> 0
    }

    if (numberOfArguments < argumentsNeeded) {
        println("Arguments needed: $argumentsNeeded")
        return false
    }

    return true
}

fun processCommand(inputSplitted: List<String>, commandStorage: PerformedCommandStorage) {
    val numberOfArguments = inputSplitted.size - 1
    val stringCommand = inputSplitted[0]

    if (!numberOfArgumentsIsCorrect(numberOfArguments, stringCommand))
        return

    when (stringCommand) {
        PossibleCommands.EMPTY.stringCommand -> println("Null input. Try again:")
        PossibleCommands.ABORT.stringCommand -> {}
        PossibleCommands.UNDO.stringCommand -> commandStorage.undo()
        PossibleCommands.PRINT.stringCommand ->
            println("\nThe processed Int list:\n" + commandStorage.processedList.joinToString(", "))
        PossibleCommands.ADD_TO_END.stringCommand -> commandStorage.addToEnd(inputSplitted[1].toInt())
        PossibleCommands.ADD_TO_BEGINNING.stringCommand -> commandStorage.addToBeginning(inputSplitted[1].toInt())
        PossibleCommands.MOVE.stringCommand -> commandStorage.move(inputSplitted[1].toInt(), inputSplitted[2].toInt())
        else -> println("Unknown command.")
    }
}

fun main() {
    val commandStorage = PerformedCommandStorage()
    var currentCommand = ""

    println(
        """
            Use:
            '${PossibleCommands.ADD_TO_BEGINNING.stringCommand} x'
            '${PossibleCommands.ADD_TO_END.stringCommand} x'
            '${PossibleCommands.MOVE.stringCommand} i j'
            '${PossibleCommands.UNDO.stringCommand}' commands to process the list of integers;
            '${PossibleCommands.PRINT.stringCommand}' to print the list and
            '${PossibleCommands.ABORT.stringCommand}' to abort the session:
        """.trimIndent()
    )
    while (currentCommand != "abort") {
        var inputSplitted: List<String>
        try {
            println("Enter your command:")
            val input = readLine()
            if (input == null) {
                println("Null input. Please, try again:")
            } else {
                inputSplitted = input.split(' ')
                currentCommand = inputSplitted[0]
                processCommand(inputSplitted, commandStorage)
            }
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
