package hw.alg.huffman.model

import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

object BitArray {
    var size = 0
    lateinit var bytes: ByteArray
    private val masks = byteArrayOf(
        1, 2, 4, 8,
        16, 32, 64, 128.toByte()
    )

    fun getArrayBySize(size: Int): BitArray {
        this.size = size
        var sizeInBytes = size / 8
        if (size % 8 > 0) {
            sizeInBytes += 1
        }
        this.bytes = ByteArray(sizeInBytes)
        return BitArray
    }

    operator fun get(index: Int): Int {
        val byteIndex = index / 8
        val bitIndex = index % 8
        val byte: Int = (bytes[byteIndex] and masks[bitIndex]).toInt()
        return if (byte != 0) 1 else 0
    }

    operator fun set(index: Int, value: Int) {
        val byteIndex = index / 8
        val bitIndex = index % 8
        if (value != 0) {
            bytes[byteIndex] = (bytes[byteIndex] or masks[bitIndex])
        } else {
            bytes[byteIndex] = (bytes[byteIndex] and masks[bitIndex].inv())
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (i in 0 until size) {
            sb.append(if (get(i) > 0) '1' else '0')
        }
        return sb.toString()
    }
}