package main.kotlin.homework2

fun main() {
    val myCommandStorage = main.libraries.PerformedCommandStorage.PerformedCommandStorage()
    var currentCommand: String = ""

    println(
        "Use 'addToBeginning x', 'addToEnd x', 'swap i j', 'undo' commands to process list of integers" +
                "and 'abort' to abort the session:"
    )
    while (currentCommand != "abort") {
        var input = listOf<String>()
        println("Enter your command:")
        input = readln().split(' ')
        currentCommand = input[0]
        try {
            when (input[0]) {
                "addToEnd" -> {
                    myCommandStorage.addToEnd(input[1].toInt())
                }
                "addToBeginning" -> {
                    myCommandStorage.addToBeginning(input[1].toInt())
                }
                "swap" -> {
                    try {
                        myCommandStorage.swap(input[1].toInt(), input[2].toInt())
                    } catch (e: IllegalArgumentException) {
                        println("Out of bounds positions.")
                    }
                }
                "undo" -> {
                    try {
                        myCommandStorage.undo()
                    } catch (exception: IllegalArgumentException) {
                        println("Command storage is empty.")
                    }
                }
                "abort" -> break
                else ->
                    println("Unknown command.")
            }
        } catch (exception: java.lang.NumberFormatException) {
            println("Cannot convert into Int.")
        }
    }
}