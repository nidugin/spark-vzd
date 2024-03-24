package valuation

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions.{abs, col, desc}


object VZD {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("VZD")
      .master("local[*]")
      .getOrCreate()

    // Check arguments length
    if (args.length != 2) {
      println("Need input file and output file")
      println("Current arguments length is: ", args.length)
      System.exit(1)
    }

    // Read xml files
    val df = spark.read
      .format("com.databricks.spark.xml")
      .option("rootTag", "ValuationFullData")
      .option("rowTag", "ValuationItemData")
      .load(args(0))
    // Get the absolute difference value between PropertyCadastralValue and PropertyValuation
    val differenceDF = df.withColumn("ValueDifference", abs(col("PropertyCadastralValue") - col("PropertyValuation")))
    // Set rows in descending order
    val sortedDF = differenceDF.orderBy(desc("ValueDifference"))
    // Take the first row (with the biggest value) and drop ValueDifference column
    val finalDF = sortedDF.drop(col("ValueDifference")).limit(1)
    // Save the result as .json file
    finalDF.write
      .option("ignoreNullFields", "False")
      .mode(SaveMode.Overwrite)
      .json(args(1))
  }
}
