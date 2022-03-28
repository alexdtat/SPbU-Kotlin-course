package libraries.performedCommandStorage

interface Command {
    fun command()
    fun reverseCommand()
}

class AddToEnd(private val processedList: MutableList<Int>, private val addedNumber: Int) : Command {
    override fun command() {
        processedList.add(addedNumber)
    }

    override fun reverseCommand() {
        processedList.removeLast()
    }
}

class AddToBeginning(private val processedList: MutableList<Int>, private val addedNumber: Int) : Command {
    override fun command() {
        processedList.add(0, addedNumber)
    }

    override fun reverseCommand() {
        processedList.removeAt(0)
    }
}

class Move(private val processedList: MutableList<Int>, private val origin: Int, private val destination: Int) :
    Command {
    override fun command() {
        processedList.add(destination, processedList.removeAt(origin))
    }

    override fun reverseCommand() {
        command()
    }
}

interface CommandStorage {
    val processedList: List<Int>
}

class PerformedCommandStorage : CommandStorage {
    private val commandList = mutableListOf<Command>()
    override val processedList = mutableListOf<Int>()

    fun execute(userCommand: Command) {
        commandList.add(userCommand)
        userCommand.command()
    }

    /*fun getList(): List<Int> {
        return processedList.toList()
    }*/

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

    fun move(origin: Int, destination: Int) {
        val upperBound = processedList.size - 1
        require(origin in processedList.indices && destination in processedList.indices) {
            if (upperBound < 0)
                "Cannot swap in the empty storage."
            else
                "Positions must be integers in range 0..$upperBound."
        }
        execute(Move(processedList, origin, destination))
    }
}
