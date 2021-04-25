package ScalaMI

/** Implements common discrete Shannon Entropy functions.
  * Provides: univariate entropy H(X),
  * conditional entropy H(X|Y),
  * joint entropy H(X,Y).
  * Defaults to log_2, and so the entropy is calculated in bits.
  *
  * @author apocock
  */
object Entropy {
  val LOG_BASE = 2.0

  /** Calculates the univariate entropy H(X) from a vector.
    * Uses histograms to estimate the probability distributions, and thus the entropy.
    * The entropy is bounded 0 &#8804; H(X) &#8804; log |X|, where log |X| is the log of the number
    * of states in the random variable X.
    *
    * @param  dataVector Input vector (X). It is discretised to the floor of each value before calculation.
    * @return The entropy H(X).
    */
  def calculateEntropy(dataVector: Array[Double]): Double = {
    val state = new ProbabilityState(dataVector)

    state.propMap.values
      .map(prob => -prob * Math.log(prob))
      .fold(0.0)(_ + _) / Math.log(LOG_BASE)

  }

  /** Calculates the conditional entropy H(X|Y) from two vectors.
    * X = dataVector, Y = conditionVector.
    * Uses histograms to estimate the probability distributions, and thus the entropy.
    * The conditional entropy is bounded 0 &#8804; H(X|Y) &#8804; H(X).
    *
    * @param  dataVector      Input vector (X). It is discretised to the floor of each value before calculation.
    * @param  conditionVector Input vector (Y). It is discretised to the floor of each value before calculation.
    * @return The conditional entropy H(X|Y).
    */
  def calculateConditionalEntropy(
      dataVector: Array[Double],
      conditionVector: Array[Double]
  ): Double = {
    val state = new JointProbabilityState(dataVector, conditionVector)

    state.jointPropMap
      .flatMap { case (jointKey, jointValue) =>
        val condValue: Double = state.secondProbMap(jointKey._2)
        if ((jointValue > 0) && (condValue > 0))
          Some(-jointValue * Math.log(jointValue / condValue))
        else None
      }
      .fold(0.0)(_ + _) / Math.log(LOG_BASE)
  }

  /** Calculates the joint entropy H(X,Y) from two vectors.
    * The order of the input vectors is irrelevant.
    * Uses histograms to estimate the probability distributions, and thus the entropy.
    * The joint entropy is bounded 0 &#8804; H(X,Y) &#8804; log |XY|, where log |XY| is the log of
    * the number of states in the joint random variable XY.
    *
    * @param  firstVector  Input vector. It is discretised to the floor of each value before calculation.
    * @param  secondVector Input vector. It is discretised to the floor of each value before calculation.
    * @return The joint entropy H(X,Y).
    */
  def calculateJointEntropy(
      firstVector: Array[Double],
      secondVector: Array[Double]
  ): Double = {
    val state = new JointProbabilityState(firstVector, secondVector)

    state.jointPropMap.values
      .map(prob => -prob * Math.log(prob))
      .fold(0.0)(_ + _) / Math.log(LOG_BASE)
  }

}
