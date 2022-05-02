package libraries.avlTree

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Named
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class AVLTreeTest {
    @Test
    fun `clear() test`() {
        val tree = avlTreeOf(
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 4,
            5 to 5,
            6 to 6,
            7 to 7,
            8 to 8,
            9 to 9,
            10 to 10
        )
        tree.clear()
        assertEquals(0, tree.size)
    }

    @Test
    fun `isEmpty() after clear()`() {
        val tree = avlTreeOf(
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 4,
            5 to 5,
            6 to 6,
            7 to 7,
            8 to 8,
            9 to 9,
            10 to 10
        )
        tree.clear()
        assertEquals(true, tree.isEmpty())
    }

    @Test
    fun `getEntries test`() {
        val tree = avlTreeOf('b' to 2, 'e' to 5, 'c' to 3, 'a' to 1, 'd' to 4)
        val expectedEntries = mapOf('a' to 1, 'b' to 2, 'c' to 3, 'd' to 4, 'e' to 5).entries
        assertEquals(expectedEntries, tree.entries)
    }

    @Test
    fun `getKeys() test`() {
        val tree = avlTreeOf('b' to 2, 'e' to 5, 'c' to 3, 'a' to 1, 'd' to 4)
        val expectedKeys = mutableSetOf('a', 'b', 'c', 'd', 'e')
        assertEquals(expectedKeys, tree.keys)
    }

    @Test
    fun `getValues() test`() {
        val tree = avlTreeOf('b' to 2, 'e' to 5, 'c' to 3, 'a' to 1, 'd' to 4)
        val expectedValues = mutableSetOf(1, 2, 3, 4, 5)
        assertEquals(expectedValues, tree.values)
    }

    @Test
    fun `getValues() with duplicates`() {
        val tree = avlTreeOf('b' to 2, 'e' to 5, 'c' to 3, 'a' to 1, 'd' to 4, 'e' to 100)
        val expectedValues = mutableSetOf(1, 2, 3, 4, 100)
        assertEquals(expectedValues, tree.values)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getSizeInput")
    fun <K : Comparable<K>, V> `getSize tests`(tree: AVLTree<K, V>, expected: Int) {
        assertEquals(expected, tree.size)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("isEmptyInput")
    fun <K : Comparable<K>, V> `isEmpty() tests`(tree: AVLTree<K, V>, expected: Boolean) {
        assertEquals(expected, tree.isEmpty())
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("containsKeyInput")
    fun <K : Comparable<K>, V> `containsKey() tests`(tree: AVLTree<K, V>, key: K, expected: Boolean) {
        assertEquals(expected, tree.containsKey(key))
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getInput")
    fun <K : Comparable<K>, V> `get() tests`(tree: AVLTree<K, V>, key: K, expected: V?) {
        assertEquals(expected, tree[key])
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("containsValueInput")
    fun <K : Comparable<K>, V> `containsValue() tests`(tree: AVLTree<K, V>, value: V, expected: Boolean) {
        assertEquals(expected, tree.containsValue(value))
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("putInput")
    fun <K : Comparable<K>, V> `put() tests`(
        tree: AVLTree<K, V>,
        expectedEntries: Set<Map.Entry<K, V>>,
        key: K,
        value: V,
        expected: V?
    ) {
        assertEquals(expected, tree.put(key, value))
        assertEquals(expectedEntries.size, tree.size)
        assertEquals(expectedEntries, tree.entries)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("removeInput")
    fun <K : Comparable<K>, V> `remove() tests`(
        tree: AVLTree<K, V>,
        expectedEntries: Set<Map.Entry<K, V>>,
        key: K,
        expected: V?
    ) {
        assertEquals(expected, tree.remove(key))
        assertEquals(expectedEntries.size, tree.size)
        assertEquals(expectedEntries, tree.entries)
    }

    companion object {
        @JvmStatic
        fun getSizeInput() = listOf(
            Arguments.of(
                Named.of(
                    "emptyTree",
                    avlTreeOf<Int, Any>()
                ),
                0
            ),
            Arguments.of(
                Named.of(
                    "putAll",
                    avlTreeOf(
                        'b' to 2,
                        'e' to 5,
                        'c' to 3,
                        'a' to 1,
                        'd' to 4
                    )
                ),
                5
            ),
            Arguments.of(
                Named.of(
                    "putAllAndChangeValue",
                    avlTreeOf(
                        'b' to 2,
                        'e' to 5,
                        'c' to 3,
                        'a' to 1,
                        'd' to 4
                    ).apply { this['c'] = 100 }
                ),
                5
            ),
            Arguments.of(
                Named.of(
                    "putAllAndRemove",
                    avlTreeOf(
                        'b' to 2,
                        'e' to 5,
                        'c' to 3,
                        'a' to 1,
                        'd' to 4
                    ).apply { this.remove('e') }
                ),
                4
            )
        )

        @JvmStatic
        fun containsKeyInput() = listOf(
            Arguments.of(
                Named.of(
                    "putAll",
                    avlTreeOf(
                        'b' to 2,
                        'e' to 5,
                        'c' to 3,
                        'a' to 1,
                        'd' to 4
                    )
                ),
                'f', false
            ),
            Arguments.of(
                Named.of(
                    "putAllAndChangeValue",
                    avlTreeOf(
                        'b' to 2,
                        'e' to 5,
                        'c' to 3,
                        'a' to 1,
                        'd' to 4
                    ).apply { this['c'] = 100 }
                ),
                'c', true
            ),
            Arguments.of(
                Named.of(
                    "putAllAndRemove",
                    avlTreeOf(
                        'b' to 2,
                        'e' to 5,
                        'c' to 3,
                        'a' to 1,
                        'd' to 4
                    ).apply { this.remove('e') }
                ),
                'e', false
            ),
            Arguments.of(
                Named.of(
                    "emptyTree",
                    avlTreeOf<Char, Int>()
                ),
                'd', false
            )
        )

        @JvmStatic
        fun isEmptyInput() = listOf(
            Arguments.of(
                Named.of(
                    "emptyTree",
                    avlTreeOf<Int, Any>()
                ),
                true
            ),
            Arguments.of(
                Named.of(
                    "putAll",
                    avlTreeOf(
                        'b' to 2,
                        'e' to 5,
                        'c' to 3,
                        'a' to 1,
                        'd' to 4
                    )
                ),
                false
            ),
            Arguments.of(
                Named.of(
                    "putAllAndRemove",
                    avlTreeOf(
                        'b' to 2,
                        'e' to 5,
                        'c' to 3,
                        'a' to 1,
                        'd' to 4
                    ).apply { this.remove('e') }
                ),
                false
            )
        )

        @JvmStatic
        fun containsValueInput() = listOf(
            Arguments.of(
                Named.of(
                    "putAll",
                    avlTreeOf(
                        'b' to 2,
                        'c' to 3,
                        'a' to 1,
                    )
                ),
                10, false
            ),
            Arguments.of(
                Named.of(
                    "putAllAndChangeValueNew",
                    avlTreeOf(
                        'b' to 2,
                        'e' to 5,
                        'c' to 3,
                        'a' to 1,
                        'd' to 4
                    ).apply { this['c'] = 100 }
                ),
                100, true
            ),
            Arguments.of(
                Named.of(
                    "putAllAndChangeValueOld",
                    avlTreeOf(
                        'b' to 2,
                        'e' to 5,
                        'c' to 3,
                        'a' to 1,
                        'd' to 4
                    ).apply { this['c'] = 100 }
                ),
                3, false
            ),
            Arguments.of(
                Named.of(
                    "putAllAndRemoveRemoved",
                    avlTreeOf(
                        'b' to 2,
                        'e' to 5,
                        'c' to 3,
                        'a' to 1,
                        'd' to 4
                    ).apply { this.remove('e') }
                ),
                5, false
            ),
            Arguments.of(
                Named.of(
                    "emptyTree",
                    avlTreeOf<Char, Int>()
                ),
                4, false
            )
        )

        @JvmStatic
        fun getInput() = listOf(
            Arguments.of(
                Named.of(
                    "putAll",
                    avlTreeOf(
                        'b' to 2,
                        'e' to 5,
                        'c' to 3,
                        'a' to 1,
                        'd' to 4
                    )
                ),
                'f', null
            ),
            Arguments.of(
                Named.of(
                    "putAllAndChangeValue",
                    avlTreeOf(
                        'b' to 2,
                        'e' to 5,
                        'c' to 3,
                        'a' to 1,
                        'd' to 4
                    ).apply { this['c'] = 100 }
                ),
                'c', 100
            ),
            Arguments.of(
                Named.of(
                    "putAllAndRemove",
                    avlTreeOf(
                        'b' to 2,
                        'e' to 5,
                        'c' to 3,
                        'a' to 1,
                        'd' to 4
                    ).apply { this.remove('e') }
                ),
                'e', null
            ),
            Arguments.of(
                Named.of(
                    "emptyTree",
                    avlTreeOf<Char, Int>()
                ),
                'd', null
            )
        )

        @JvmStatic
        fun putInput() = listOf(
            Arguments.of(
                Named.of(
                    "put",
                    avlTreeOf<Char, Int>().apply {
                        this['b'] = 2
                        this['e'] = 5
                        this['c'] = 3
                        this['a'] = 1
                        this['d'] = 4
                    }
                ),
                mapOf('a' to 1, 'b' to 2, 'c' to 3, 'd' to 4, 'e' to 5, 'f' to 6).entries, 'f', 6, null
            ),
            Arguments.of(
                Named.of(
                    "putAndChangeValue",
                    avlTreeOf<Char, Int>().apply {
                        this['b'] = 2
                        this['e'] = 5
                        this['c'] = 3
                        this['a'] = 1
                        this['d'] = 4
                        this['c'] = 100
                    }
                ),
                mapOf('a' to 1, 'b' to 2, 'c' to 25, 'd' to 4, 'e' to 5).entries, 'c', 25, 100
            )
        )

        @JvmStatic
        fun removeInput() = listOf(
            Arguments.of(
                Named.of(
                    "remove",
                    avlTreeOf(
                        'b' to 2,
                        'e' to 5,
                        'c' to 3,
                        'a' to 1,
                        'd' to 4
                    )
                ),
                mapOf('a' to 1, 'b' to 2, 'd' to 4, 'e' to 5).entries, 'c', 3
            ),
            Arguments.of(
                Named.of(
                    "removeNothing",
                    avlTreeOf<Char, Int>().apply {
                        this['b'] = 2
                        this['e'] = 5
                        this['c'] = 3
                        this['a'] = 1
                        this['d'] = 4
                        this['c'] = 100
                    }
                ),
                mapOf('a' to 1, 'b' to 2, 'c' to 100, 'd' to 4, 'e' to 5).entries, 'f', null
            )
        )
    }
}
