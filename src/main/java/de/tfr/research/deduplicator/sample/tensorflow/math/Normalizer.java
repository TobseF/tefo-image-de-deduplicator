package de.tfr.research.deduplicator.sample.tensorflow.math;

public class Normalizer {
    public static long normalize(double f) {
        return Math.round(f * 100);
    }

    public static int normalize(float f) {
        return Math.round(f * 100);
    }
}
