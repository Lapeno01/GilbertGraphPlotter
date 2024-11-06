package graph

import scala.util.Random
import scala.collection.mutable


case class Graph(n: Int, p: Double) {
  private val random = new Random()
  private val adjacencyMatrix: Array[Array[Boolean]] = Array.fill(n, n)(false)
  for {
    i <- 0 until n
    j <- i + 1 until n
  } if (random.nextDouble() < p) {
    adjacencyMatrix(i)(j) = true
    adjacencyMatrix(j)(i) = true
  }


  def averagePathLength(sampleSize: Int): Double = {
    val sampledPairs = (1 to sampleSize).map(_ => (random.nextInt(n), random.nextInt(n)))
    val pathLengths = sampledPairs.map { case (src, dest) => shortestPathLength(src, dest) }
    pathLengths.filter(_ < n).sum.toDouble / pathLengths.count(_ < n)
  }


  // BFS based
  private def shortestPathLength(src: Int, dest: Int): Int = {
    if (src == dest) return 0

    val visited = Array.fill(n)(false)
    // node + destination to itself
    val queue = mutable.Queue((src, 0))
    visited(src) = true

    while (queue.nonEmpty) {
      val (currentNode, currentDist) = queue.dequeue()
      for (i <- 0 until n if adjacencyMatrix(currentNode)(i) && !visited(i)) {
        if (i == dest) return currentDist + 1
        visited(i) = true
        queue.enqueue((i, currentDist + 1))
      }
    }
    n // Return n if no path is found
  }
}

