package graph

import org.jfree.chart.axis.LogAxis
import org.jfree.chart.{ChartFactory, ChartUtils}
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.xy.{XYSeries, XYSeriesCollection}

import java.io.File


object Experiments {
  def runExperiments(config: Config = Config()): Unit = {
    val ns = (50 to config.maxN by config.stepN).toArray
    val avgPathLengths = ns.map { n =>
      val p = calculateP(n, config.degree)
      val graph = Graph(n, p)
      val avgPathLen = graph.averagePathLength(config.t)
      println(f"Node count: $n, Average path length: $avgPathLen%.2f")
      avgPathLen
    }
    createAndSavePlots(ns, avgPathLengths)
  }


  private def calculateP(n: Int, constantDegree: Int): Double = { constantDegree.toDouble / n }


  private def createAndSavePlots(ns: Array[Int], avgPathLengths: Array[Double]): Unit = {
    val series = new XYSeries("Average Path Length")
    ns.zip(avgPathLengths).foreach { case (n, length) =>
      series.add(n, length)
    }
    val dataset = new XYSeriesCollection(series)
    // Linear plot
    val linearChart = ChartFactory.createXYLineChart(
      "Average Path Length vs Number of Nodes (Linear Scale)",
      "Number of Nodes (n)", "Average Path Length",
      dataset,
      PlotOrientation.VERTICAL,
      true, true, false
    )
    ChartUtils.saveChartAsPNG(new File("average_path_length_vs_n_linear.png"), linearChart, 800, 600)
    println("Linear plot saved as average_path_length_vs_n_linear.png")
    // Logarithmic plot
    val logChart = ChartFactory.createXYLineChart(
      "Average Path Length vs Number of Nodes (Log-Log Scale)",
      "Number of Nodes (n)", "Average Path Length",
      dataset,
      PlotOrientation.VERTICAL,
      true, true, false
    )
    val logPlot = logChart.getXYPlot
    logPlot.setDomainAxis(new LogAxis("Log(Number of Nodes (n))"))
    logPlot.setRangeAxis(new LogAxis("Log(Average Path Length)"))
    ChartUtils.saveChartAsPNG(new File("average_path_length_vs_n_loglog.png"), logChart, 800, 600)
    println("Logarithmic plot saved as average_path_length_vs_n_loglog.png")
  }
}
