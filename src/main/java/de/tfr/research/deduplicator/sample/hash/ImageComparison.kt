package de.tfr.research.deduplicator.sample.hash

import java.util.*
import kotlin.math.absoluteValue

class ImageComparison {
    private val analysedImages = ArrayList<AnalysedImage>()

    fun addAnalysedImage(a: AnalysedImage) {
        analysedImages.add(a)
    }

    fun listAllMinimalDistances() {
        analysedImages.forEach { println(minimalDistance(it).toString() + ": " + it.toString()) }
    }

    fun minimalDistance(image: AnalysedImage): Int {
        val collect: List<Int> = analysedImages.filter { it != image }.map { compareNormalized(it, image) }.sorted().toList()
        return collect.first()
    }

    private fun compareNormalized(a: AnalysedImage, b: AnalysedImage): Int {
        val dataA = a.getNormalizedData()
        val dataB = b.getNormalizedData()
        val distance = dataA.indices.sumBy { (dataA[it] - dataB[it]).absoluteValue }
        if (distance <= 5) {
            println("Equal: " + a.name + " and " + b.name)
        }
        return Math.abs(distance)
    }
}