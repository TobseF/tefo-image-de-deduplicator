/* Copyright 2016 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package de.tfr.research.deduplicator.sample.label;

import de.tfr.research.deduplicator.sample.tensorflow.TensorFlowGraph;
import de.tfr.research.deduplicator.sample.tensorflow.TensorImage;
import org.tensorflow.TensorFlow;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Sample use of the TensorFlow Java API to label images using a pre-trained model.
 */
public class LabelImage {
    private static void printUsage(PrintStream s) {
        final String url =
                "https://storage.googleapis.com/download.tensorflow.org/models/inception5h.zip";
        s.println(
                "Java program that uses a pre-trained Inception model (http://arxiv.org/abs/1512.00567)");
        s.println("to label JPEG images.");
        s.println("TensorFlow version: " + TensorFlow.version());
        s.println();
        s.println("Usage: label_image <model dir> <image file>");
        s.println();
        s.println("Where:");
        s.println("<model dir> is a directory containing the unzipped contents of the inception model");
        s.println("            (from " + url + ")");
        s.println("<image file> is the path to a JPEG image file");
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            printUsage(System.err);
            System.exit(1);
        }
        String tensorFile = args[0];
        String labelFile = args[1];
        String imageFile = args[2];

        List<String> labels = readAllLinesOrExit(Paths.get(labelFile));

        TensorImage image = new TensorImage(Paths.get(imageFile));
        TensorFlowGraph graph = new TensorFlowGraph(Paths.get(tensorFile));
        float[] labelProbabilities = graph.executeInceptionGraph(image);
        System.out.println(Arrays.toString(labelProbabilities));
        int bestLabelIdx = maxIndex(labelProbabilities);
        System.out.println(
                String.format("BEST MATCH: %s (%.2f%% likely)",
                        labels.get(bestLabelIdx),
                        labelProbabilities[bestLabelIdx] * 100f));

    }


    private static int maxIndex(float[] probabilities) {
        int best = 0;
        for (int i = 1; i < probabilities.length; ++i) {
            if (probabilities[i] > probabilities[best]) {
                best = i;
            }
        }
        return best;
    }


    private static List<String> readAllLinesOrExit(Path path) {
        try {
            return Files.readAllLines(path, Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.err.println("Failed to read [" + path + "]: " + e.getMessage());
            System.exit(0);
        }
        return null;
    }

}