/** *****************************************************************************
  * * MutualInformation.java
  * * Part of the Java Mutual Information toolbox
  * *
  * * Author: Adam Pocock
  * * Created: 20/1/2012
  * *
  * *  Copyright 2012-2016 Adam Pocock, The University Of Manchester
  * *  www.cs.manchester.ac.uk
  * *
  * *  This file is part of JavaMI.
  * *
  * *  JavaMI is free software: you can redistribute it and/or modify
  * *  it under the terms of the GNU Lesser General Public License as published by
  * *  the Free Software Foundation, either version 3 of the License, or
  * *  (at your option) any later version.
  * *
  * *  JavaMI is distributed in the hope that it will be useful,
  * *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  * *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * *  GNU Lesser General Public License for more details.
  * *
  * *  You should have received a copy of the GNU Lesser General Public License
  * *  along with JavaMI.  If not, see <http://www.gnu.org/licenses/>.
  * *
  * ******************************************************************************//*******************************************************************************
  * * MutualInformation.java
  * * Part of the Java Mutual Information toolbox
  * *
  * * Author: Adam Pocock
  * * Created: 20/1/2012
  * *
  * *  Copyright 2012-2016 Adam Pocock, The University Of Manchester
  * *  www.cs.manchester.ac.uk
  * *
  * *  This file is part of JavaMI.
  * *
  * *  JavaMI is free software: you can redistribute it and/or modify
  * *  it under the terms of the GNU Lesser General Public License as published by
  * *  the Free Software Foundation, either version 3 of the License, or
  * *  (at your option) any later version.
  * *
  * *  JavaMI is distributed in the hope that it will be useful,
  * *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  * *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * *  GNU Lesser General Public License for more details.
  * *
  * *  You should have received a copy of the GNU Lesser General Public License
  * *  along with JavaMI.  If not, see <http://www.gnu.org/licenses/>.
  * *
  * ******************************************************************************/
package ScalaMI


/**
  * Implements common discrete Mutual Information functions.
  * Provides: Mutual Information I(X;Y),
  * Conditional Mutual Information I(X,Y|Z).
  * Defaults to log_2, and so the entropy is calculated in bits.
  *
  * @author apocock
  */
object MutualInformation {
  /**
    * Calculates the Mutual Information I(X;Y) between two random variables.
    * Uses histograms to estimate the probability distributions, and thus the information.
    * The mutual information is bounded 0 &#8804; I(X;Y) &#8804; min(H(X),H(Y)). It is also symmetric,
    * so I(X;Y) = I(Y;X).
    *
    * @param  firstVector  Input vector (X). It is discretised to the floor of each value before calculation.
    * @param  secondVector Input vector (Y). It is discretised to the floor of each value before calculation.
    * @return The Mutual Information I(X;Y).
    */
  def calculateMutualInformation(firstVector: Array[Double], secondVector: Array[Double]): Double = {
    val state = new JointProbabilityState(firstVector, secondVector)

    state.jointPropMap.flatMap { case (jointKey, jointValue) =>
      val firstValue: Double = state.firstProbMap(jointKey._1)
      val secondValue: Double = state.secondProbMap(jointKey._2)
      if ((jointValue > 0) && (firstValue > 0) && (secondValue > 0)) Some(jointValue * Math.log((jointValue / firstValue) / secondValue))
      else None
    }.fold(0.0)(_ + _) / Math.log(Entropy.LOG_BASE)
  }

  //calculateMutualInformation(double [], double [])
  /**
    * Calculates the conditional Mutual Information I(X;Y|Z) between two random variables, conditioned on
    * a third.
    * Uses histograms to estimate the probability distributions, and thus the information.
    * The conditional mutual information is bounded 0 &#8804; I(X;Y) &#8804; min(H(X|Z),H(Y|Z)).
    * It is also symmetric, so I(X;Y|Z) = I(Y;X|Z).
    *
    * @param  firstVector     Input vector (X). It is discretised to the floor of each value before calculation.
    * @param  secondVector    Input vector (Y). It is discretised to the floor of each value before calculation.
    * @param  conditionVector Input vector (Z). It is discretised to the floor of each value before calculation.
    * @return The conditional Mutual Information I(X;Y|Z).
    */
  def calculateConditionalMutualInformation(firstVector: Array[Double], secondVector: Array[Double], conditionVector: Array[Double]): Double = { //first create the vector to hold *outputVector
    val (mergedVector: Array[Int], _) = ProbabilityState.mergeArrays(firstVector, conditionVector)
    val firstCondEnt: Double = Entropy.calculateConditionalEntropy(secondVector, conditionVector)
    val secondCondEnt: Double = Entropy.calculateConditionalEntropy(secondVector, mergedVector.map(_.toDouble))
    firstCondEnt - secondCondEnt
  }

  /**
    * TESTS
    */
  def main(args: Array[String]): Unit = {
    val a = Array[Double](3, 8, 15, 16, 3, 3, 1, 3, 3)
    val b = Array[Double](1, 4, 5, 7, 1, 4, 3, 4, 4)
    val c = calculateMutualInformation(a, b)
    System.out.println(c)
  }
}
