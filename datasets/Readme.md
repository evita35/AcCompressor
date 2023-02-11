`friemel-modified`: same as `friemel` except that RNAs with empty hairpin loops have had the innermost bond broken, i.e., `()` in their secondary structure are replaced with `..`

`dowell-mixed80`: the mixed80 dataset from Dowell&Eddy 2004, but all unknown bases have been randomly replaced with actual bases A,C,G,U compatible with the ambiguous base symbol

`dowell-benchmark`: the benchmark dataset from Dowell&Eddy 2004 with `()` in their secondary structure are replaced with `..`

`dowell.zip`: Original datasets from Dowell Eddy 2004, contains benchmark, mixed80 and rfam5 datasets
`friemel.zip`: Original dataset as Jonas Friemel provided it to us;  he already randomly replaced ambiguous bases and non-canonical bonds; the data is taken from Cannone et al. 2002

`JackRIP-SecStrucPrediction-BenchMarkDataSet-rule-probs-dowell-mixed80-GXBound-withNCR-true` (for `X` in 3..8): secondary structure prediction output from jackRIP for the `dowell-benchmark` data set, using the Dowell&Eddy 2004 grammar `GXB` grammar allowing noncanonical bonds; training data for the static rule probability model is dowell-mixed80
`Earley-SecStrucPrediction-BenchMarkDataSet-rule-probs-dowell-mixed80-G6Bound-withNCR-true`: Like `JackRIP-SecStrucPrediction-BenchMarkDataSet-rule-probs-dowell-mixed80-G6Bound-withNCR-true`, but using the digitalheir Earley parser in Java; PREDICTIONS ARE INCORRECT due to bugs in the parser

`Benchmark_without_Empty_Hair_pin.zip`: `dowell-benchmark` with `()` in their secondary structure are replaced with `..`

`LiuResearchProbabilitiesNoEpsilon`: dummy dataset just to have the folder; the corresponding probabilities are taken from Liu et al. 2008.

`TestDataSet`: This dataset is created for unit tests. It contains 6 RNAs, 2 from Benchmark("AGR.TUM", "Agro.tume"), 2 from mixed80("AB012589", "AB013269") and 2 from Friemel-modified ("9_1480_c", "10_552_c").

`TestTrainingData`: This dataset is created for unit tests which require training dataset. it contains 6 RNAs, 2 each from Friemel, Benchmark and Mixed80 