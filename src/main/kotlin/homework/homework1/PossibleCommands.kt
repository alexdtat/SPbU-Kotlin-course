package homework.homework1

enum class PossibleCommands(val stringCommand: String) {
    ADD_TO_BEGINNING("addToBeginning"),
    ADD_TO_END("addToEnd"),
    MOVE("move"),
    UNDO("undo"),
    PRINT("print"),
    ABORT("abort"),
    EMPTY("")
}
