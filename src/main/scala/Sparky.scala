import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.{ReadConfig, WriteConfig}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SQLContext
import org.apache.commons.io.FileUtils
import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.sql.catalyst.expressions.GenericRow
import org.apache.spark.sql.types._

case class UserMovieRating(user_id: Int, movie_id: Int, rating: Double)

object Sparky {

  def main(args: Array[String]): Unit ={

    val path = "test-output.tfrecord" //write data to a HDFS storage
    //Set up Configurations
    val sc = getSparkContext()

    val sqlContext= SparkSession.builder().getOrCreate()

    val readConfig = ReadConfig(Map("uri" -> "mongodb://127.0.0.1/movies.movie_ratings?readPreference=primaryPreferred"))
    val writeConfig = WriteConfig(Map("uri" -> "mongodb://127.0.0.1/movies.user_recommendations"))

    val userId = 0

    val df : DataFrame = MongoSpark.load(sc,readConfig).toDF[UserMovieRating]


    //val df: DataFrame = sqlContext.createDataFrame(rdd,UserMovieRating).
    df.write.format("tfrecords").option("recordType", "Example").save(path)

}
/**
  * Gets or creates the Spark Context
  */
def getSparkContext(): SparkContext = {
  val conf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("Ipanera")

  val sc = SparkContext.getOrCreate(conf)
  sc.setCheckpointDir("/tmp/checkpoint/")
  sc //Return the Spark Context
}
}