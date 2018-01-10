package de.tfr.research.deduplicator.sample.hash

import de.tfr.research.deduplicator.sample.tensorflow.TensorFlowGraph
import de.tfr.research.deduplicator.sample.tensorflow.TensorImage
import java.nio.file.Files
import java.nio.file.Paths

class HashImage(tensorFile: String, imageFolder: String) {

    init {
        val graph = TensorFlowGraph(Paths.get(tensorFile))
        val comparison = ImageComparison()

        Files.walk(Paths.get(imageFolder))
                .filter({ path -> Files.isRegularFile(path) }).map<TensorImage>({ TensorImage(it) }).map { image -> analyseImage(image, graph) }.forEach { analyzed ->
            comparison.addAnalysedImage(analyzed)
            println("Analyzing image" + analyzed.name)
            //System.out.println(analyzed.getName() + ":" + formatted);
            //println(analyzed.toString() + ":"+analyzed)
        }

        comparison.listAllMinimalDistances()

    }

    private fun analyseImage(imageFile: TensorImage, graph: TensorFlowGraph): AnalysedImage {
        val analysisData = graph.executeInceptionGraph(imageFile)
        return AnalysedImage(imageFile.imageFile.fileName.toString(), analysisData)
    }
}