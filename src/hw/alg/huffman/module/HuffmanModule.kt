package hw.alg.huffman.module

import hw.alg.huffman.model.TreeNode
import java.lang.StringBuilder
import java.util.*

class HuffmanModule(
    private val treeNodes: MutableList<TreeNode> = mutableListOf(),
    val frequencies: TreeMap<Char, Int> = TreeMap<Char, Int>(),
    private val codes: TreeMap<Char, String?> = TreeMap<Char, String?>(),
    val encoded: StringBuilder = StringBuilder(),
) {

    fun huffmanCode(content: String) {
        countFrequency(content)
        generateLists()
        createHuffmanTreeAndCodes(content)
    }

    private fun countFrequency(content: String) =
        content.forEach { frequencies[it] = (frequencies[it]?.plus(1) ?: 1) }

    private fun generateLists() =
        frequencies.forEach { treeNodes.add(TreeNode(it.key, it.value)) }

    private fun createHuffmanTreeAndCodes(content: String) {
        huffmanCodeTree()

        val tree = treeNodes[0]

        frequencies.keys.forEach {
            codes[it] = tree.getCharCode(it, "")
        }

        content.forEach {
            encoded.append(codes[it])
        }
    }

    private fun huffmanCodeTree() {
        while (treeNodes.size > 1) {
            treeNodes.sort()
            val left = treeNodes.removeLast()
            val right = treeNodes.removeLast()

            val parent = TreeNode(weight = right.weight + left.weight, left = left, right = right)
            treeNodes.add(parent)
        }
    }

    fun huffmanDecode(): String {
        generateLists()
        huffmanCodeTree()
        val decoded = StringBuilder()
        var node = treeNodes[0]
        encoded.forEach {
            node = if (it == '0') node.left!! else node.right!!
            if (node.content != null) {
                decoded.append(node.content)
                node = treeNodes[0]
            }
        }
        return decoded.toString()
    }
}