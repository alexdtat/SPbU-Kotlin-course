package libraries.avlTree

class AVLNode<K : Comparable<K>, V>(override val key: K, override var value: V) : MutableMap.MutableEntry<K, V> {
    private var height: Int = 0

    var leftChild: AVLNode<K, V>? = null

    var rightChild: AVLNode<K, V>? = null

    private val balanceFactor: Int
        get() = (leftChild?.height ?: -1) - (rightChild?.height ?: -1)

    val maximum: AVLNode<K, V>
        get() = rightChild?.maximum ?: this

    private fun updateHeight() {
        height = maxOf(leftChild?.height ?: -1, rightChild?.height ?: -1) + 1
    }

    private fun leftRotate(): AVLNode<K, V> {
        val newRotationRoot = rightChild ?: return this
        rightChild = newRotationRoot.leftChild
        newRotationRoot.leftChild = this

        updateHeight()
        newRotationRoot.updateHeight()

        return newRotationRoot
    }

    private fun rightRotate(): AVLNode<K, V> {
        val newRotationRoot = leftChild ?: return this
        leftChild = newRotationRoot.rightChild
        newRotationRoot.rightChild = this

        updateHeight()
        newRotationRoot.updateHeight()

        return newRotationRoot
    }

    private fun leftRightRotate(): AVLNode<K, V> {
        val bufferLeftChild = leftChild
        leftChild = bufferLeftChild?.leftRotate()
        return rightRotate()
    }

    private fun rightLeftRotate(): AVLNode<K, V> {
        val bufferRightChild = rightChild
        rightChild = bufferRightChild?.rightRotate()
        return leftRotate()
    }

    fun balance(): AVLNode<K, V> = when (apply { updateHeight() }.balanceFactor) {
        LEFT_GREAT_SUPERIOR -> {
            if ((leftChild?.balanceFactor ?: 0) == RIGHT_SUPERIOR) {
                leftRightRotate()
            } else {
                rightRotate()
            }
        }
        RIGHT_GREAT_SUPERIOR -> {
            if ((rightChild?.balanceFactor ?: 0) == LEFT_SUPERIOR) {
                rightLeftRotate()
            } else {
                leftRotate()
            }
        }
        else -> this
    }

    companion object {
        private const val LEFT_GREAT_SUPERIOR = 2
        private const val RIGHT_GREAT_SUPERIOR = -2
        private const val LEFT_SUPERIOR = 1
        private const val RIGHT_SUPERIOR = -1
        private const val INDENT_SYMBOL = "~"
        private const val INDENT_STEP = 2
    }

    fun nodeIterator(): Iterator<AVLNode<K, V>> = iterator {
        leftChild?.nodeIterator()?.let { yieldAll(it) }
        yield(this@AVLNode)
        rightChild?.nodeIterator()?.let { yieldAll(it) }
    }

    override fun setValue(newValue: V): V = value.also { value = newValue }

    fun removeMaximum(): AVLNode<K, V>? {
        rightChild ?: return leftChild

        rightChild = rightChild?.removeMaximum()
        return balance()
    }

    fun getStringNodeRecursive(indent: Int): String {
        var nodeAsString = INDENT_SYMBOL.repeat(indent * INDENT_STEP)
        nodeAsString += "($key): $value [$height]\n"

        leftChild?.getStringNodeRecursive(indent + 1)?.let { nodeAsString += "l$it" }
        rightChild?.getStringNodeRecursive(indent + 1)?.let { nodeAsString += "r$it" }

        return nodeAsString
    }
}
