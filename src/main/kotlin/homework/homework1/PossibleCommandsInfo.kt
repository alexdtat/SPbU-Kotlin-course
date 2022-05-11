package homework.homework1

enum class PossibleCommandsInfo(val stringCommand: String, val numberOfArguments: Int, val help: String) {
    ADD_TO_BEGINNING(
        "addToBeginning", 1,
        "Use 'addToBeginning x' to add integer x to the beginning of the processed list."
    ),
    ADD_TO_END(
        "addToEnd", 1,
        "Use 'addToEnd x' to add integer x to the end of the processed list."
    ),
    MOVE(
        "move", 2,
        "Use 'move i j' to move element from i position to j position in the processed list."
    ),
    UNDO(
        "undo", 0,
        "Use 'undo' to undo the last command, applied to the processed list"
    ),
    PRINT(
        "print", 0,
        "Use 'print' to print the processed list."
    ),
    ABORT(
        "abort", 0,
        "Use 'abort' to abort the session."
    ),
    EMPTY("", 0, "")
}
