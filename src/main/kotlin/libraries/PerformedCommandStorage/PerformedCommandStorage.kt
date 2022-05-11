package libraries.performedCommandStorage

interface Command {
    fun checkArgumentsException(processedList: MutableList<Int>): IllegalArgumentException? = null
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
        processedList.add(destination, processedList.removeAt(origin))
    }

    override fun reverseCommand(processedList: MutableList<Int>) {
        processedList.add(origin, processedList.removeAt(destination))
    }

    override fun checkArgumentsException(processedList: MutableList<Int>): IllegalArgumentException? {
        val upperBound = processedList.size - 1
        var resultException: IllegalArgumentException? = null
        if (upperBound < 0) {
            resultException = MoveInEmptyStorageException()
        } else if (!(origin in processedList.indices && destination in processedList.indices)) {
            resultException = MoveWithIllegalArgumentsException(processedList.size - 1)
        }
        return resultException
    }
}

class PerformedCommandStorage {
    private val commandList = mutableListOf<Command>()
    private val _processedList = mutableListOf<Int>()
    val processedList: List<Int>
        get() = _processedList

    fun execute(userCommand: Command) {
        val exception = userCommand.checkArgumentsException(_processedList)
        if (exception != null) {
            throw exception
        }
        userCommand.command(_processedList)
        commandList.add(userCommand)
    }

    fun undo() {
        if (commandList.size == 0) {
            throw UndoInEmptyStorageException()
        }
        commandList.removeLast().reverseCommand(_processedList)
    }
}

class UndoInEmptyStorageException : IllegalArgumentException("Cannot undo a command in the empty storage.")

class MoveInEmptyStorageException : IllegalArgumentException("Cannot move in the empty storage.")

class MoveWithIllegalArgumentsException(upperBound: Int) :
    IllegalArgumentException("Positions must be integers in range 0..$upperBound.")
