package homework.homework1

import libraries.performedCommandStorage.AddToBeginning
import libraries.performedCommandStorage.AddToEnd
import libraries.performedCommandStorage.Move
import libraries.performedCommandStorage.MoveInEmptyStorageException
import libraries.performedCommandStorage.MoveWithIllegalArgumentsException
import libraries.performedCommandStorage.PerformedCommandStorage
import libraries.performedCommandStorage.UndoInEmptyStorageException

fun PerformedCommandStorage.addToEnd(addedNumber: Int) {
    execute(AddToEnd(addedNumber))
}

fun PerformedCommandStorage.addToBeginning(addedNumber: Int) {
    execute(AddToBeginning(addedNumber))
}

fun PerformedCommandStorage.move(origin: Int, destination: Int) {
    execute(Move(origin, destination))
}

fun numberOfArgumentsIsCorrect(numberOfArguments: Int, command: PossibleCommandsInfo): Boolean {
    if (numberOfArguments < command.numberOfArguments) {
        println("Arguments needed: ${command.numberOfArguments}")
        return false
    }

    return true
}

fun PerformedCommandStorage.processCommand(command: PossibleCommandsInfo?, inputSplitted: List<String>) {
    val numberOfArguments = inputSplitted.size - 1

    if (command != null && !numberOfArgumentsIsCorrect(numberOfArguments, command))
        return

    when (command) {
        PossibleCommandsInfo.EMPTY -> println("Null input. Try again:")
        PossibleCommandsInfo.ABORT -> {}
        PossibleCommandsInfo.UNDO -> this.undo()
        PossibleCommandsInfo.PRINT -> {
            val listForPrint = this.processedList.joinToString(", ")
            println("\nThe processed Int list:\n$listForPrint")
        }
        PossibleCommandsInfo.ADD_TO_END -> this.addToEnd(inputSplitted[1].toInt())
        PossibleCommandsInfo.ADD_TO_BEGINNING -> this.addToBeginning(inputSplitted[1].toInt())
        PossibleCommandsInfo.MOVE -> this.move(inputSplitted[1].toInt(), inputSplitted[2].toInt())
        else -> println("Unknown command.")
    }
}

fun main() {
    val commandStorage = PerformedCommandStorage()
    var currentCommand: PossibleCommandsInfo? = PossibleCommandsInfo.EMPTY

    PossibleCommandsInfo.values().forEach { println(it.help) }
    while (currentCommand != PossibleCommandsInfo.ABORT) {
        try {
            println("Enter your command:")
            val input: String? = readLine()
            if (input == null) {
                println("Null input. Please, try again:")
            } else {
                val inputSplitted = input.split(' ')
                PossibleCommandsInfo.values().find { it.stringCommand == inputSplitted[0] }.also { currentCommand = it }
                commandStorage.processCommand(currentCommand, inputSplitted)
            }
        } catch (exceptionFormat: NumberFormatException) {
            println(exceptionFormat.message + " Command's arguments must be integers.")
        } catch (exceptionUndo: UndoInEmptyStorageException) {
            println(exceptionUndo.message + " Execute some commands at first.")
        } catch (exceptionEmptyMove: MoveInEmptyStorageException) {
            println(exceptionEmptyMove.message + " Add some elements at first.")
        } catch (exceptionIllegalPositionMove: MoveWithIllegalArgumentsException) {
            println(exceptionIllegalPositionMove.message + " Please, move within bounds.")
        }
    }
}
