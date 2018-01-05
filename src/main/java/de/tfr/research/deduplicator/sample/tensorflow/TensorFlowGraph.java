package de.tfr.research.deduplicator.sample.tensorflow;

import de.tfr.research.deduplicator.sample.tensorflow.io.IOUtils;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.nio.file.Path;
import java.util.Arrays;

public class TensorFlowGraph {

    private final byte[] graphDef;

    public TensorFlowGraph(Path graphFile) {
        this.graphDef = IOUtils.readAllBytesOrExit(graphFile);
    }

    public float[] executeInceptionGraph(TensorImage image) {
        return executeInceptionGraph(image.getNormalizedImage());
    }

    public float[] executeInceptionGraph(Tensor<Float> image) {
        try (Graph g = new Graph()) {
            g.importGraphDef(graphDef);
            try (Session s = new Session(g);
                 Tensor<Float> result =
                         s.runner().feed("input", image).fetch("output").run().get(0).expect(Float.class)) {
                final long[] rshape = result.shape();
                if (result.numDimensions() != 2 || rshape[0] != 1) {
                    throw new RuntimeException(
                            String.format(
                                    "Expected model to produce a [1 N] shaped tensor where N is the number of labels, instead it produced one with shape %s",
                                    Arrays.toString(rshape)));
                }
                int nlabels = (int) rshape[1];
                return result.copyTo(new float[1][nlabels])[0];
            }
        }
    }
}
