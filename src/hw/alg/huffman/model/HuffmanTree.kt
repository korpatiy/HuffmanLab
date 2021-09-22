package hw.alg.huffman.model

/**
 * Структура дерева
 */
class TreeNode(
    val content: Char? = null,
    val weight: Int,
    val left: TreeNode? = null,
    val right: TreeNode? = null
) : Comparable<TreeNode> {
    override fun compareTo(other: TreeNode): Int {
        return other.weight - weight
    }

    fun getCharCode(ch: Char, parentPath: String): String? {
        if (content == ch) {
            return parentPath
        } else {
            if (left != null) {
                val path = left.getCharCode(ch, parentPath + 0)
                if (path != null)
                    return path
            }
            if (right != null) {
                val path = right.getCharCode(ch, parentPath + 1)
                if (path != null)
                    return path
            }
        }
        return null
    }
}