package main.libraries.PerformedCommandStorage

import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.collections.ArrayList

abstract class Command {
    abstract fun directCommand()
    abstract fun reverseCommand()
}

class AddToEnd(private val processedList: ArrayList<Int>, private val addedNumber: Int) : Command() {
    override fun directCommand() {
        processedList.add(addedNumber)
    }

    override fun reverseCommand() {
        processedList.removeLast()
    }
}

class AddToBeginning(private val processedList: ArrayList<Int>, private val addedNumber: Int) : Command() {
    override fun directCommand() {
        processedList.add(0, addedNumber)
    }

    override fun reverseCommand() {
        processedList.removeAt(0)
    }
}

class Swap(private val processedList: ArrayList<Int>, private val position1: Int, private val position2: Int) :
    Command() {
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
    private val processedList = ArrayList<Int>()

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
            "Positions must be integers in range 0..${upperBound}."
        }
        execute(Swap(processedList, position1, position2))
    }

    fun getListOfInts(): ArrayList<Int> {
        return processedList
    }

    fun printList() {
        println("\nThe processed Int ArrayList:")
        for (index in processedList.indices)
            print("${processedList[index]}\t")
    }
}