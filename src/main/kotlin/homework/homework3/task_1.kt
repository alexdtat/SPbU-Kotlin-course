package homework.homework3

import libraries.avlTree.avlTreeOf

@Suppress("MagicNumber")
fun main() {
    val thoughtsTree = avlTreeOf(
        "Jamais Vu (Derealization)" to 3.25,
        "Some kind of Superstar" to 1.10,
        "Searchlight Division" to 3.15,
        "Waste Land of Reality" to 20,
        "Wompty-Dompty Dom Centre" to 0.42,
        "Regular Law Official" to 1.20,
        "The Fifteenth Indotribe" to 5.55,
        "Hardcore Aesthetic" to 1.50,
        "Cleaning Out the Rooms" to 5.35,
        "Bankruptcy Sequence" to 5.15,
        "Apricot Chewing Gum Scented One" to 5.05,
        "Indirect Modes of Taxation" to 1.45,
        "Date of Birth Generator" to 7.15,
        "Kingdom of Conscience" to 1.25,
        "Arno van Eyck" to 0.50,
        "Overproductive Honour Glands" to 0.20,
        "Anti-Object Task Force" to 2.15,
        "Fairweather T-500" to 3.30,
        "Lonesome Long Way Home" to 6.05,
        "The Precarious World" to 4,
        "One More Door" to 0.45,
        "Caustic Echo" to 10,
        "Remote Viewers Division" to 6,
        "Remote Viewers Division" to 8.10,
        "Magnesium-Based Lifeform" to 1.15,
        "Detective Costeau" to 2.30,
        "The Bow Collector" to 6.10,
        "The Insulindian Miracle" to 7.45,
        "Guillaume le Million" to 4.30
    )

    println("Current thoughts tree:")
    thoughtsTree.printTree()

    thoughtsTree.remove("Anti-Object Task Force")
    thoughtsTree["Remote Viewers Division"] = 6
    thoughtsTree.remove("Hardcore Aesthetic")
    thoughtsTree.remove("Arno van Eyck")
    thoughtsTree.remove("Some kind of Superstar")
    thoughtsTree.remove("Idk how it got here")
    thoughtsTree.put("White Mourning", 5)
    thoughtsTree.put("Bringing of the Law (Law-Jaw)", 2.55)
    thoughtsTree.put("Boiadeiro", 6.30)

    println(
        "\n" +
            "\n" +
            "\nThoughts tree after a few changes:"
    )
    thoughtsTree.printTree()

    println(
        "\n" +
            "\n" +
            "\n\"Jamais Vu (Derealization)\" value is: ${thoughtsTree.get("Jamais Vu (Derealization)")}"
    )
}
