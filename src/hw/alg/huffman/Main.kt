package hw.alg.huffman

import hw.alg.huffman.file.FileWorker
import hw.alg.huffman.module.HuffmanModule

fun main() {
    val fileWorker = FileWorker()
    val huffmanModuleEncode = HuffmanModule()

    huffmanModuleEncode.huffmanCode(fileWorker.read("./inputFiles/ww.txt"))
    fileWorker.save(path = "./outputFiles/compressed.bin", huffman = huffmanModuleEncode)

    val huffmanModuleDecode = HuffmanModule()
    fileWorker.load(path = "./outputFiles/compressed.bin", huffmanModuleDecode)

    val huffmanDecode = huffmanModuleDecode.huffmanDecode()
    fileWorker.write(path = "./outputFiles/decompressed.txt", huffmanDecode)
}
