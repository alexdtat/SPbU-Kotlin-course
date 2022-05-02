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
        val newRotationRoot: AVLNode<K, V> = rightChild ?: return this
        rightChild = newRotationRoot.leftChild
        newRotationRoot.leftChild = this

        this.updateHeight()
        newRotationRoot.updateHeight()

        return newRotationRoot
    }

    private fun rightRotate(): AVLNode<K, V> {
        val newRotationRoot: AVLNode<K, V> = leftChild ?: return this
        leftChild = newRotationRoot.rightChild
        newRotationRoot.rightChild = this

        this.updateHeight()
        newRotationRoot.updateHeight()

        return newRotationRoot
    }

    private fun leftRightRotate(): AVLNode<K, V> {
        val bufferLeftChild = this.leftChild ?: this
        this.leftChild = bufferLeftChild.leftRotate()
        return this.rightRotate()
    }

    private fun rightLeftRotate(): AVLNode<K, V> {
        val bufferRightChild = this.rightChild/* ?: this*/
        this.rightChild = bufferRightChild?.rightRotate()
        return this.leftRotate()
    }

    fun balance(): AVLNode<K, V> {
        this.updateHeight()

        return when (this.balanceFactor) {
            LEFT_GREAT_SUPERIOR -> {
                if ((this.leftChild?.balanceFactor ?: 0) == RIGHT_SUPERIOR) {
                    this.leftRightRotate()
                } else {
                    this.rightRotate()
                }
            }
            RIGHT_GREAT_SUPERIOR -> {
                if ((this.rightChild?.balanceFactor ?: 0) == LEFT_SUPERIOR) {
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

    override fun setValue(newValue: V): V = this.value.also { this.value = newValue }

    fun removeMaximum(): AVLNode<K, V>? {
        rightChild ?: return leftChild

        rightChild = rightChild?.removeMaximum()
        return this.balance()
    }

    fun printNode(indentSymbol: Char, indentStep: Int, indent: Int) {
        print(indentSymbol.toString().repeat(indent * indentStep))
        println("($key): $value [$height]")

        if (leftChild != null) {
            print("l")
        }
        leftChild?.printNode(indentSymbol, indentStep, indent + 1)
        if (rightChild != null) {
            print("r")
        }
        rightChild?.printNode(indentSymbol, indentStep, indent + 1)
    }
}
