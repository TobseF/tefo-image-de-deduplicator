package de.tfr.research.deduplicator.sample.hash

import de.tfr.research.deduplicator.sample.tensorflow.math.Normalizer
import de.tfr.research.deduplicator.sample.tensorflow.math.printMe

class AnalysedImage(val name: String, val analysisData: FloatArray) {

    override fun toString(): String {
        return "$name, ${printMe(analysisData)}"
    }

    fun getNormalizedData() = Array(analysisData.size, { Normalizer.normalize(analysisData[it]) })
}
