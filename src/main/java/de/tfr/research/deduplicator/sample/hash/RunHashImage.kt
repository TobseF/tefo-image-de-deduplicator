package de.tfr.research.deduplicator.sample.hash


fun main(args: Array<String>) {
    val tensorFile = args[0]
    val imageFolder = args[1]
    HashImage(tensorFile, imageFolder)
}