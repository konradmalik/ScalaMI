/** *****************************************************************************
  * * ProbabilityState.java
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

/** Calculates the probabilities of each state in a random variable.
  * Provides the base for all functions of one variable. Additional functions
  * include the normaliseArrays function which converts all inputs so they start
  * at 0, and the mergeArrays function which creates an array of the joint state of
  * the two input arrays.
  *
  * @author apocock
  */
class ProbabilityState(val dataVector: Array[Double]) {

  /** Constructor for the ProbabilityState class. Takes a data vector and calculates
    * the marginal probability of each state, storing each state/probability pair in a HashMap.
    *
    * dataVector Input vector. It is discretised to the floor of each value.
    */
  private val doubleLength: Double = dataVector.length.toDouble
  //round input to integers
  private val norm: (Array[Int], Int) =
    ProbabilityState.normaliseArray(dataVector)
  private val normalisedVector: Array[Int] = norm._1
  val maxState: Int = norm._2
  private val countMap: Map[Int, Int] =
    normalisedVector.zipWithIndex
      .groupBy(_._1)
      .view
      .view
      .map { case (k, v) => (k, v.length) }
      .toMap
  val propMap: Map[Int, Double] =
    countMap.view.map { case (k, v) => (k, v / doubleLength) }.toMap

}

object ProbabilityState {

  /** Takes an input vector and writes an output vector
    * which is a normalised version of the input, and returns the maximum state.
    * A normalised array has min value = 0, max value = old max value - min value
    * and all values are integers
    *
    * The length of the vectors must be the same, and outputVector must be
    * instantiated before calling this function.
    *
    * @param inputVector The vector to normalise.
    * @return The normalised vector. Must be instantiated to length inputVector.length.
    */
  def normaliseArray(inputVector: Array[Double]): (Array[Int], Int) = {

    val inputVectorInt: Array[Int] = inputVector.map(v => Math.round(v).toInt)
    val minInt: Int = inputVectorInt.min
    val maxInt: Int = inputVectorInt.max

    // normalise
    (inputVectorInt.map(v => v - minInt), maxInt + 1)
  } //normaliseArray(double[],double[])
  /** Takes in two arrays and writes the joint state of those arrays
    * to the output vector, returning the output vector
    *
    * The length of all vectors must be equal to firstVector.length
    * outputVector must be instantiated before calling this function.
    *
    * @param firstVector  The first vector.
    * @param secondVector The second vector.
    * @return the output vector
    */
  def mergeArrays(
      firstVector: Array[Double],
      secondVector: Array[Double]
  ): (Array[Int], Int) = {
    assert(firstVector.length == secondVector.length)
    val (firstNormalisedVector: Array[Int], _) = normaliseArray(firstVector)
    val (secondNormalisedVector: Array[Int], _) = normaliseArray(secondVector)

    val joinedVectors: Array[(Int, Int)] =
      firstNormalisedVector.zip(secondNormalisedVector)

    val joinedStatesMap: Map[(Int, Int), Int] =
      joinedVectors.distinct.zipWithIndex.map(a => (a._1, a._2 + 1)).toMap

    (joinedVectors.map(joinedStatesMap), joinedStatesMap.values.max + 1)
  }

  /** A helper function which prints out any given double vector.
    * Mainly used to help debug the rest of the toolbox.
    *
    * @param vector The vector to print out.
    */
  def printVector[T](vector: Array[T]): Unit = {
    vector.zipWithIndex.foreach(p => "Val at i=" + p._2 + ", is " + p._1)
  }

}
