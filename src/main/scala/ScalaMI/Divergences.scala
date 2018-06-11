package ScalaMI

/**
  * Based on https://github.com/sameersingh/scala-utils
  */

import java.lang.StrictMath.log

object Divergences {

  private val loge2: Double = log(2)

  private def log2(x: Double): Double = log(x) / loge2

  def kullbackLeibler(ps: Seq[Double], qs: Seq[Double]): Double = {
    assert(ps.size == qs.size)
    ps.zip(qs).map { case (p, q) => if (p == 0.0) 0.0 else p * log2(p / q) }.sum
  }

  def symmetricKullbackLeibler(ps: Seq[Double], qs: Seq[Double]): Double = {
    assert(ps.size == qs.size)
    kullbackLeibler(ps, qs) + kullbackLeibler(qs, ps)
  }

  private def half(xs: Seq[Double]): Seq[Double] = xs.map(x => 0.5 * x)

  private def elemwiseAdd(ps: Seq[Double], qs: Seq[Double]): Seq[Double] =
    ps.zip(qs).map { case (p, q) => p + q }

  def jensenShannon(ps: Seq[Double], qs: Seq[Double]): Double = {
    val ms = half(elemwiseAdd(ps, qs))
    0.5 * (kullbackLeibler(ps, ms) + kullbackLeibler(qs, ms))
  }

}