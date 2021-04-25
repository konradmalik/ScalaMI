/** *****************************************************************************
  * * JointProbabilityState.java
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
  * *****************************************************************************
  */
package konradmalik.ScalaMI

/** Calculates the probabilities of each state in a joint random variable.
  * Provides the base for all functions of two variables.
  *
  * @author apocock
  */
class JointProbabilityState(
    val firstVector: Array[Double],
    val secondVector: Array[Double]
) {

  /** Constructor for the JointProbabilityState class. Takes two data vectors and calculates
    * the joint and marginal probabilities, before storing them in HashMaps.
    *
    * firstVector  Input vector. It is discretised to the floor of each value.
    * secondVector Input vector. It is discretised to the floor of each value.
    */

  private val doubleLength: Double = firstVector.length.toDouble
  assert(doubleLength == secondVector.length.toDouble)

  private val normFirst: (Array[Int], Int) =
    ProbabilityState.normaliseArray(firstVector)
  private val normSecond: (Array[Int], Int) =
    ProbabilityState.normaliseArray(secondVector)
  private val firstNormalisedVector: Array[Int] = normFirst._1
  private val secondNormalisedVector: Array[Int] = normSecond._1
  val firstMaxVal: Int = normFirst._2
  val secondMaxVal: Int = normSecond._2

  private val jointCountMap: Map[(Int, Int), Int] =
    firstNormalisedVector
      .zip(secondNormalisedVector)
      .zipWithIndex
      .groupBy(_._1)
      .view
      .map { case (k, v) => (k, v.length) }
      .toMap
  val jointPropMap: Map[(Int, Int), Double] =
    jointCountMap.view.map { case (k, v) => (k, v / doubleLength) }.toMap

  private val firstCountMap: Map[Int, Int] =
    firstNormalisedVector.zipWithIndex
      .groupBy(_._1)
      .view
      .map { case (k, v) => (k, v.length) }
      .toMap
  val firstProbMap: Map[Int, Double] =
    firstCountMap.view.map { case (k, v) => (k, v / doubleLength) }.toMap

  private val secondCountMap: Map[Int, Int] =
    secondNormalisedVector.zipWithIndex
      .groupBy(_._1)
      .view
      .map { case (k, v) => (k, v.length) }
      .toMap
  val secondProbMap: Map[Int, Double] =
    secondCountMap.view.map { case (k, v) => (k, v / doubleLength) }.toMap
}
