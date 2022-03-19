package libraries.performedCommandStorage

import kotlin.collections.ArrayList

interface Command {
    fun directCommand()
    fun reverseCommand()
}

class AddToEnd(private val processedList: ArrayList<Int>, private val addedNumber: Int) : Command {
    override fun directCommand() {
        processedList.add(addedNumber)
    }

    override fun reverseCommand() {
        processedList.removeLast()
    }
}

class AddToBeginning(private val processedList: ArrayList<Int>, private val addedNumber: Int) : Command {
    override fun directCommand() {
        processedList.add(0, addedNumber)
    }

    override fun reverseCommand() {
        processedList.removeAt(0)
    }
}

class Swap(private val processedList: ArrayList<Int>, private val position1: Int, private val position2: Int) :
    Command {
    override fun directCommand() {
        processedList[position1] = processedList[position2].also {
            processedList[position2] = processedList[position1]
        }
    }

    override fun reverseCommand() {
        directCommand()
    }
}

class PerformedCommandStorage {
    private val commandList = ArrayList<Command>()
    val processedList = ArrayList<Int>()

    private fun execute(userCommand: Command) {
        commandList.add(userCommand)
        userCommand.directCommand()
    }

    fun undo() {
        require(commandList.size > 0) { "You cannot undo a command in the empty storage." }
        commandList.removeLast().reverseCommand()
    }

    fun addToEnd(addedNumber: Int) {
        execute(AddToEnd(processedList, addedNumber))
    }

    fun addToBeginning(addedNumber: Int) {
        execute(AddToBeginning(processedList, addedNumber))
    }

    fun swap(position1: Int, position2: Int) {
        val upperBound = processedList.size - 1
        require(position1 in 0..upperBound && position2 in 0..upperBound) {
            if (upperBound < 0)
                "Cannot swap in the empty storage."
            else
                "Positions must be integers in range 0..$upperBound."
        }
        execute(Swap(processedList, position1, position2))
    }
}
