# Companion code for [“RNA secondary structures: from ab initio prediction to better compression, and back”](https://arxiv.org/abs/2302.11669)


For data from the experiments in the paper and how to reproduce them,
please refer to the [Google Colab Notebook](https://colab.research.google.com/drive/1c6gxgr4Ud5t19kYGiqG5mXPUcPB8n3W9?usp=sharing).

## Installation

1. Clone the repository
2. Add the absolute path to your clone in `compression.LocalConfig`. (`src/compression/LocalConfig.java`).
3. Compile by running `ant`
4. Run with `java -cp dist/joint-rna-compression-with-dependencies.jar MAINCLASS`.

Relevant main classes are the following;
run them to see the command line options.

* `compression.Training` count rule applications in a given training
   dataset for the built-in grammars
* `compression.TrainingProbabilities` to turn the rule counts into
   probabilities as needed for compression or prediction
* `compression.Compressions` to run compression experiments
* `compression.EarleyParserHarness` to simulate compression to check 
   whether the [Java implementation of the Earley parser](https://github.com/digitalheir/java-probabilistic-earley-parser) 
   works correctly on the given datasets.
* `compression.PrintC` code to print C code for a given grammar and static
   rule probabilities (as produced by `TrainingProbabilities`) suitable for
   jackRIP, the stochastic Earley parser from [[Wild 2010]](https://www.wild-inter.net/publications/wild-2010) (written in C++).



