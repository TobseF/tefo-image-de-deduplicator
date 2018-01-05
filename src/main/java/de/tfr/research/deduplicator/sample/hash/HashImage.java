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

package de.tfr.research.deduplicator.sample.hash;

import de.tfr.research.deduplicator.sample.tensorflow.TensorFlowGraph;
import de.tfr.research.deduplicator.sample.tensorflow.TensorImage;

import java.nio.file.Paths;
import java.util.Arrays;


/**
 * Sample use of the TensorFlow Java API to label images using a pre-trained model.
 */
public class HashImage {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.exit(1);
        }
        String tensorFile = args[0];
        String imageFile = args[1];

        TensorImage image = new TensorImage(Paths.get(imageFile));
        TensorFlowGraph graph = new TensorFlowGraph(Paths.get(tensorFile));
        float[] labelProbabilities = graph.executeInceptionGraph(image);
        System.out.println(Arrays.toString(labelProbabilities));
    }
}