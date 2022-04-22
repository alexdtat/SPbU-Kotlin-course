package libraries.performedCommandStorage

fun moveArgumentsCheck(origin: Int, destination: Int, processedList: MutableList<Int>) {
    val upperBound = processedList.size - 1
    require(origin in processedList.indices && destination in processedList.indices) {
        if (upperBound < 0)
            "Cannot swap in the empty storage."
        else
            "Positions must be integers in range 0..$upperBound."
    }
}

interface Command {
    fun command(processedList: MutableList<Int>)
    fun reverseCommand(processedList: MutableList<Int>)
}

class AddToEnd(private val addedNumber: Int) : Command {
    override fun command(processedList: MutableList<Int>) {
        processedList.add(addedNumber)
    }

    override fun reverseCommand(processedList: MutableList<Int>) {
        processedList.removeLast()
    }
}

class AddToBeginning(private val addedNumber: Int) : Command {
    override fun command(processedList: MutableList<Int>) {
        processedList.add(0, addedNumber)
    }

    override fun reverseCommand(processedList: MutableList<Int>) {
        processedList.removeAt(0)
    }
}

class Move(private val origin: Int, private val destination: Int) : Command {
    override fun command(processedList: MutableList<Int>) {
        moveArgumentsCheck(origin, destination, processedList)
        processedList.add(destination, processedList.removeAt(origin))
    }

    override fun reverseCommand(processedList: MutableList<Int>) {
        processedList.add(origin, processedList.removeAt(destination))
    }
}

class PerformedCommandStorage {
    private val commandList = mutableListOf<Command>()
    private val _processedList = mutableListOf<Int>()
    val processedList: List<Int>
        get() = _processedList

    fun execute(userCommand: Command) {
        commandList.add(userCommand)
        userCommand.command(_processedList)
    }

    fun undo() {
        require(commandList.size > 0) { "You cannot undo a command in the empty storage." }
        commandList.removeLast().reverseCommand(_processedList)
    }
}
