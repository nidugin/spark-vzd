package valuation

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DateType, LongType, StringType, StructField, StructType}
import org.scalatest.funsuite.AnyFunSuite

import java.nio.file.{Files, Paths}

class VZDTest extends AnyFunSuite {
  private val spark = SparkSession.builder()
    .appName("VZDTest")
    .master("local[*]")
    .getOrCreate()

  private val schema = StructType(Seq(
    StructField("ObjectCadastralValue", LongType, true),
    StructField("ObjectCadastralValueDate", DateType, true),
    StructField("ObjectForestValue", LongType, true),
    StructField("ObjectForestValueDate", DateType, true),
    StructField("ObjectRelation", StructType(Seq(
      StructField("ObjectCadastreNr", LongType, true),
      StructField("ObjectType", StringType, true)
    )), true),
    StructField("PropertyCadastralValue", LongType, true),
    StructField("PropertyCadastralValueDate", DateType, true),
    StructField("PropertyValuation", LongType, true),
    StructField("PropertyValuationDate", DateType, true),
  ))

  // Check if source files exist in the folder
  test("files exist") {
    assert(Files.exists(Paths.get("spark-cluster/data/valuation/")))
  }

  // Check DataFrame schema
  test("validate schema") {
    val path = "spark-cluster/data/valuation/*/*.xml"
    val df = spark.read
      .format("com.databricks.spark.xml")
      .option("rootTag", "ValuationFullData")
      .option("rowTag", "ValuationItemData")
      .load(path)
    assert(df.schema === schema)
  }
}
