package homework.homework1

enum class AllowedCommand(val stringCommand: String) {
    ADDTOBEGINNING("addToBeginning"),
    ADDTOEND("addToEnd"),
    MOVE("move"),
    UNDO("undo"),
    PRINT("print"),
    ABORT("abort")
}
