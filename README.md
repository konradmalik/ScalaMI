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

## Installation

Scala 2.11.x, 2.12.x and 2.13.x is supported.

You can either just clone this code or install package from GitHub's maven repo.

To install from Github packages (maven):

- use [SBT Github Packages plugin](https://github.com/djspiewak/sbt-github-packages)
- install the plugin as specified on its repo. Then just this to your `build.sbt`:

```scala
resolvers += Resolver.githubPackages("konradmalik", "ScalaMI")
```

- after the resolvers are provided, package installation is easy:

```scala
libraryDependencies += "konradmalik" %% "scala-mutual-information" % "0.0.2"
```

- IMPORTANT: currently, there is a limitation on github's maven repo - only authorized users can download packages.
  What This means is you will need to have a PAT (Personal Access Token) available in `GITHUB_TOKEN` env variable at the time
  when you execute anything related to `sbt`. If your PAT is invalid, then any attempt on package installation will fail with
  status 400 or 401. Managing this env variable is a separate issue, but I recommend [direnv](https://github.com/direnv/direnv)

Exemplary usage:

```scala
import konradmalik.ScalaMI

val entropy = ScalaMI.Entropy.calculateEntropy(Array(2.3, 4.1, 54.3))
```

## TODO

- Tests
- Actions

## Credits

This package is a rewrite of the JavaMI (https://github.com/Craigacp/JavaMI) from Java to Scala.
Credits to Craigacp (JavaMI) and MIToolbox authors.
New features might be added in the future, independently of JavaMI.
