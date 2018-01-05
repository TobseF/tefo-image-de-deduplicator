package de.tfr.research.deduplicator.sample.tensorflow;

import de.tfr.research.deduplicator.sample.tensorflow.image.ImageNormalizer;
import org.tensorflow.Tensor;

import java.nio.file.Path;

import static de.tfr.research.deduplicator.sample.tensorflow.io.IOUtils.readAllBytesOrExit;

public class TensorImage {

    private Path imageFile;

    public TensorImage(Path imageFile) {
        this.imageFile = imageFile;
    }

    public Tensor<Float> getNormalizedImage() {
        byte[] imageBytes = readAllBytesOrExit(imageFile);
        Tensor<Float> normalizeImage = ImageNormalizer.constructAndExecuteGraphToNormalizeImage(imageBytes);
        return normalizeImage;
    }

}
