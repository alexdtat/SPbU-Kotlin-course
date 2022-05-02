package libraries.avlTree

class AVLTree<K : Comparable<K>, V> : MutableMap<K, V> {
    private var root: AVLNode<K, V>? = null

    override var size: Int = 0
        private set

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = iterator().asSequence().toMutableSet()

    override val keys: MutableSet<K>
        get() = iterator().asSequence().map { it.key }.toMutableSet()

    override val values: MutableCollection<V>
        get() = iterator().asSequence().map { it.value }.toMutableSet()

    override fun clear() {
        root = null
        size = 0
    }

    override fun containsKey(key: K): Boolean = findNode(root, key) != null

    override fun isEmpty(): Boolean = size == 0

    override fun containsValue(value: V): Boolean = value in values

    override fun get(key: K): V? = findNode(root, key)?.value

    override fun put(key: K, value: V): V? {
        if (!containsKey(key)) {
            size++
        }
        val oldValue = get(key)
        root = putNode(root, key, value)
        return oldValue
    }

    override fun putAll(from: Map<out K, V>) = from.forEach { put(it.key, it.value) }

    override fun remove(key: K): V? {
        val removedValue = get(key)
        if (containsKey(key)) {
            root = removeNode(root, key)
            size--
        }

        return removedValue
    }

    fun printTree() = root?.printNode(INDENT_SYMBOL, INDENT_STEP, 0)

    companion object {
        const val INDENT_SYMBOL = '~'
        const val INDENT_STEP = 2
        fun <K : Comparable<K>, V> putNode(node: AVLNode<K, V>?, insertionKey: K, insertionValue: V): AVLNode<K, V> {
            node ?: return AVLNode(
                insertionKey,
                insertionValue
            )

            when {
                insertionKey < node.key -> node.leftChild = putNode(node.leftChild, insertionKey, insertionValue)
                insertionKey > node.key -> node.rightChild = putNode(node.rightChild, insertionKey, insertionValue)
                insertionKey == node.key -> node.setValue(insertionValue)
            }

            return node.balance()
        }

        fun <K : Comparable<K>, V> findNode(node: AVLNode<K, V>?, searchedKey: K): AVLNode<K, V>? {
            return when {
                node == null -> null
                searchedKey < node.key -> findNode(node.leftChild, searchedKey)
                searchedKey > node.key -> findNode(node.rightChild, searchedKey)
                else -> node
            }
        }

        private fun <K : Comparable<K>, V> removeNode(node: AVLNode<K, V>?, removalKey: K): AVLNode<K, V>? {
            return when {
                node == null -> null
                removalKey < node.key -> {
                    node.leftChild = removeNode(node.leftChild, removalKey)
                    node.balance()
                }
                removalKey > node.key -> {
                    node.rightChild = removeNode(node.rightChild, removalKey)
                    node.balance()
                }
                removalKey == node.key -> {
                    val maximumOfLeftSubtree = node.leftChild?.maximum
                    maximumOfLeftSubtree ?: return node.rightChild

                    maximumOfLeftSubtree.leftChild = node.leftChild?.removeMaximum()
                    maximumOfLeftSubtree.rightChild = node.rightChild

                    maximumOfLeftSubtree.balance()
                }
                else -> node.balance()
            }
        }
    }

    private fun iterator(): Iterator<AVLNode<K, V>> = iterator {
        root?.let { yieldAll(it.nodeIterator()) }
    }
}

fun <K : Comparable<K>, V> avlTreeOf(vararg keyValuePairs: Pair<K, V>): AVLTree<K, V> =
    AVLTree<K, V>().apply { this.putAll(keyValuePairs) }
