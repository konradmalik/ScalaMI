# ScalaMI

This is ScalaMI, an implementation of MIToolbox in Scala.

It provides a series of functions for working with information theory. It also
contains some variable manipulation functions to preprocess discrete/categorical
variables to generate information theoretic values from the variables.

These functions are targeted for use with feature selection algorithms rather
than communication channels and so expect all the data to be available before
execution and sample their own probability distributions from the data.

Functions contained:

- Entropy
- Conditional Entropy
- Mutual Information
- Conditional Mutual Information
- generating a joint variable
- generating a probability distribution from a discrete random variable

## TODO

- Tests
- Actions

## Credits

This package is a rewrite of the JavaMI (https://github.com/Craigacp/JavaMI) from Java to Scala.
Credits to Craigacp (JavaMI) and MIToolbox authors.
New features might be added in the future, independently of JavaMI.
