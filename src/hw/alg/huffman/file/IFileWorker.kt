package hw.alg.huffman.file

import hw.alg.huffman.model.BitArray
import hw.alg.huffman.module.HuffmanModule
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths

interface IFileWorker {

    fun read(path: String): String

    fun write(path: String, decoded: String)

    fun save(path: String, huffman: HuffmanModule)

    fun load(path: String, huffman: HuffmanModule)
}

class FileWorker() : IFileWorker {

    override fun read(path: String): String {
        return String(Files.readAllBytes(Paths.get(path)))
    }

    override fun write(path: String, decoded: String) {
        Files.write(Paths.get(path), decoded.toByteArray())
    }

    override fun save(path: String, huffman: HuffmanModule) {
        val os = DataOutputStream(FileOutputStream(File(path)))
        val frequencies = huffman.frequencies
        val bits = huffman.encoded.toString()

        os.writeInt(frequencies.size)
        for (character in frequencies.keys) {
            os.writeChar(character.code)
            os.writeInt(frequencies[character]!!)
        }
        val bitArray = BitArray.getArrayBySize(bits.length)
        for (i in bits.indices) {
            bitArray[i] = if (bits[i] != '0') 1 else 0
        }

        os.writeInt(bits.length)
        os.write(bitArray.bytes, 0, bitArray.bytes.size)
        os.flush()
        os.close()
    }

    override fun load(path: String, huffman: HuffmanModule) {
        val os = DataInputStream(FileInputStream(File(path)))
        val frequencies = huffman.frequencies
        val bits = huffman.encoded

        for (i in 0 until os.readInt()) {
            frequencies[os.readChar()] = os.readInt()
        }
        val bitArray = BitArray.getArrayBySize(os.readInt())
        os.read(bitArray.bytes, 0, bitArray.bytes.size)
        os.close()

        for (i in 0 until bitArray.size) {
            bits.append(if (bitArray[i] != 0) "1" else 0)
        }
    }
}