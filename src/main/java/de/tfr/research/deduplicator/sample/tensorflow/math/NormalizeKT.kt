package de.tfr.research.deduplicator.sample.tensorflow.math

import de.tfr.research.deduplicator.sample.hash.AnalysedImage

fun normalize(f: Double): Long {
    return Math.round(f * 100)
}


private fun print(analyzed: AnalysedImage) {
    printMe(analyzed.analysisData)
}

fun printMe(analyzed: FloatArray): String {
    return analyzed.map { Normalizer.normalize(it) }.filter { it > 0 }.joinToString()
}
