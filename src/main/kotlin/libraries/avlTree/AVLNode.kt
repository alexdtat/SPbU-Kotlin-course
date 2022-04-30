package libraries.avlTree

class AVLNode<K : Comparable<K>, V>(override val key: K, override var value: V) : MutableMap.MutableEntry<K, V> {
    private var height: Int = 0

    var leftChild: AVLNode<K, V>? = null
    
    var rightChild: AVLNode<K, V>? = null

    val maximum: AVLNode<K, V>
        get() = rightChild?.maximum ?: this

    private fun updateHeight() {
        height = maxOf(leftChild?.height ?: 0, rightChild?.height ?: 0) + 1
    }

    private fun getBalanceFactor() = (leftChild?.height ?: -1) - (rightChild?.height ?: -1)

    private fun leftRotate(): AVLNode<K, V> {
        val newRotationRoot: AVLNode<K, V> = rightChild ?: return this
        rightChild = newRotationRoot.leftChild
        newRotationRoot.rightChild = this

        this.updateHeight()
        newRotationRoot.updateHeight()

        return newRotationRoot
    }

    private fun rightRotate(): AVLNode<K, V> {
        val newRotationRoot: AVLNode<K, V> = leftChild ?: return this
        leftChild = newRotationRoot.rightChild
        newRotationRoot.leftChild = this

        this.updateHeight()
        newRotationRoot.updateHeight()

        return newRotationRoot
    }

    private fun leftRightRotate(): AVLNode<K, V> {
        val bufferRightChild = this.rightChild ?: return this
        this.rightChild = bufferRightChild.rightRotate()
        return this.leftRotate()
    }

    private fun rightLeftRotate(): AVLNode<K, V> {
        val bufferLeftChild = this.leftChild ?: return this
        this.leftChild = bufferLeftChild.leftRotate()
        return this.rightRotate()
    }

    fun balance(): AVLNode<K, V> {
        this.updateHeight()
        return when (this.getBalanceFactor()) {
            LEFT_GREAT_SUPERIOR -> {
                if (this.leftChild?.getBalanceFactor() == RIGHT_SUPERIOR) {
                    this.leftRightRotate()
                } else {
                    this.rightRotate()
                }
            }
            RIGHT_GREAT_SUPERIOR -> {
                if (this.rightChild?.getBalanceFactor() == LEFT_SUPERIOR) {
                    this.rightLeftRotate()
                } else {
                    this.leftRotate()
                }
            }
            else -> this
        }
    }

    companion object {
        private const val LEFT_GREAT_SUPERIOR = 2
        private const val RIGHT_GREAT_SUPERIOR = -2
        private const val LEFT_SUPERIOR = 1
        private const val RIGHT_SUPERIOR = -1
    }

    fun nodeIterator(): Iterator<AVLNode<K, V>> = iterator {
        leftChild?.nodeIterator()?.let { yieldAll(it) }
        yield(this@AVLNode)
        rightChild?.nodeIterator()?.let { yieldAll(it) }
    }

    override fun setValue(newValue: V): V {
        return this.value.also { this.value = newValue }
    }

    fun removeMaximum(): AVLNode<K, V>? {
        if (rightChild == null) {
            return leftChild
        }

        rightChild = rightChild?.removeMaximum().apply { updateHeight() }
        return this.balance()
    }

    fun printNode(indentSymbol: Char, indentStep: Int, indent: Int) {
        print(indentSymbol.toString().repeat(indent * indentStep))
        println("($key): $value [$height]")

        leftChild?.printNode(indentSymbol, indentStep, indent + 1)
        rightChild?.printNode(indentSymbol, indentStep, indent + 1)
    }
}
