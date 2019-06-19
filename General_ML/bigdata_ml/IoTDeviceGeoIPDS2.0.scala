// Databricks notebook source
// MAGIC %md
// MAGIC **Click on ![](http://training.databricks.com/databricks_guide/ImportNotebookIcon3.png) at the top if you want to import this notebook and run the code in your Databricks account.** 

// COMMAND ----------

// MAGIC %md ## How to Process IoT Device JSON Data Using Dataset

// COMMAND ----------

// MAGIC %md Datasets in Apache Spark 2.0 provide high-level domain specific APIs as well as provide structure and compile-time type-safety. You can read your
// MAGIC JSON data file into a DataFrame, a generic row of JVM objects, and convert them into type-specific collection of JVM objects. 
// MAGIC 
// MAGIC In this notebook, we show you how you read a JSON file, convert your semi-structured JSON data into a collection of Datasets[T], and introduce some high-level Spark 2.0 Dataset APIs.

// COMMAND ----------

// MAGIC %md ####Reading JSON as a Dataset

// COMMAND ----------

// MAGIC %md Use the Scala case class *DeviceIoTData* to convert the JSON device data into a Scala object. Of note here is GeoIP information for each device entry:
// MAGIC * IP address
// MAGIC * ISO-3166-1 two and three letter codes
// MAGIC * Country Name
// MAGIC * Latitude and longitude
// MAGIC 
// MAGIC With these attributes as part of the device data, we can map and visualize them as needed. For each IP associated with a *device_id*, I optained the above attributes from a webservice at http://freegeoip.net/csv/ip
// MAGIC 
// MAGIC *{"device_id": 198164, "device_name": "sensor-pad-198164owomcJZ", "ip": "80.55.20.25", "cca2": "PL", "cca3": "POL", "cn": "Poland", "latitude": 53.080000, "longitude": 18.620000, "scale": "Celsius", "temp": 21, "humidity": 65, "battery_level": 8, "c02_level": 1408, "lcd": "red", "timestamp" :1458081226051 }*
// MAGIC 
// MAGIC This dataset is avaialbe from Public S3 bucket //databricks-public-datasets/data/iot or https://github.com/dmatrix/examples/blob/master/spark/databricks/notebooks/data/iot_devices.json

// COMMAND ----------



// COMMAND ----------

val AccessKey = "YOUR ACCESSY"
//Encode the Secret Key as that can contain "/"
val SecretKey = "YOUR SECRET KEY".replace("/", "%2F")
val AwsBucketName = "YOUR BUCKET NAME"
val MountName = "/YOUR_MOUNT_POINT"

// COMMAND ----------

// MAGIC %md Create a case class to represent your IoT Device Data

// COMMAND ----------

case class DeviceIoTData (battery_level: Long, c02_level: Long, cca2: String, cca3: String, cn: String, device_id: Long, device_name: String, humidity: Long, ip: String, latitude: Double, lcd: String, longitude: Double, scale:String, temp: Long, timestamp: Long)


// COMMAND ----------

//read the json file and create the dataset from the case class DeviceIoTData
// ds is now a collection of JVM Scala objects DeviceIoTData
val ds = spark.read.json(s"dbfs:/mnt/$MountName/iot/iot_devices.json").as[DeviceIoTData]

// COMMAND ----------

ds.count()

// COMMAND ----------

// MAGIC %md Displaying your Dataset

// COMMAND ----------

//display Dataset's 
display(ds)

// COMMAND ----------

// MAGIC %md #### Iterating, transforming, and filtering Dataset

// COMMAND ----------

// MAGIC %md Let's iterate over the first 10 entries with the foreach() method and print them

// COMMAND ----------

ds.take(10).foreach(println(_))

// COMMAND ----------

// MAGIC %md 
// MAGIC 
// MAGIC For all relational expressions, the [Catalyst Optimizer](https://databricks.com/blog/2015/04/13/deep-dive-into-spark-sqls-catalyst-optimizer.html) will formulate an optimized logical and physical plan for execution, and [Tungsten](https://databricks.com/blog/2015/04/28/project-tungsten-bringing-spark-closer-to-bare-metal.html) engine will optimize the generated code. For our *DeviceIoTData*, it will use its standard encoders to optimize its binary internal representation, hence decrease the size of generated code, minimize the bytes transfered over the networks between nodes, and execute faster.
// MAGIC 
// MAGIC For instance, let's first filter the device dataset on *temp* and *humidity* attributes with a predicate and display the first 10 items.
// MAGIC 
// MAGIC Note that filter(), as in RDDs, returns an immutable Dataset.

// COMMAND ----------

//issue select, map, filter, foreach operations on the datasets, just as you would for DataFrames
// it returns back a Dataset
val dsTempDS = ds.filter(d => {d.temp > 30 && d.humidity > 70})
display(dsTempDS)


// COMMAND ----------

// MAGIC %md Use Dataset APIs for filtering: take(10) returns an Array[DeviceIoTData]; using a foreach() method on the Array collection, I print each item.

// COMMAND ----------

//filter out dataset rows that meet the temperature and humimdity predicate
val dsFilter = ds.filter(d => {d.temp > 30 && d.humidity > 70}).take(10).foreach(println(_))

// COMMAND ----------

// MAGIC %md Filter out dataset using the high-level and readable method where(). Note that filter() and where() are equivalent.

// COMMAND ----------

val dsTemp = ds.where($"temp" > 25).map(d => (d.temp, d.device_name, d.device_id, d.cca3))

// COMMAND ----------

display(dsTemp)

// COMMAND ----------

// MAGIC %md Both where() and map() return a Dataset

// COMMAND ----------

// MAGIC %md Now use the filter() method that is equivalent as the filter() method used above.

// COMMAND ----------

val dsTemp2 = ds.filter(d=> {d.temp > 25} ).map(d => (d.temp, d.device_name, d.device_id, d.cca3))
display(dsTemp2)

// COMMAND ----------

// MAGIC %md Select individual fields using the Dataset method select() where battery_level is greater than 6, sort in asceding order on C02_level. Note that this high-level domain specific language API reads like a SQL query

// COMMAND ----------

display(ds.select($"battery_level", $"c02_level", $"device_name").where($"battery_level" > 6).sort($"c02_level"))

// COMMAND ----------

// MAGIC %md Apply higher-level Dataset API methods such as groupBy() and avg(). In order words, take all temperatures readings > 25, along with their corrosponding devices' humidity, groupBy ccca3 country code, and compute averages. Plot the resulting Dataset.

// COMMAND ----------

val dsAvgTmp = ds.filter(d => {d.temp > 25}).map(d => (d.temp, d.humidity, d.cca3)).groupBy($"_3").avg()
display(dsAvgTmp)


// COMMAND ----------

// MAGIC %md #### Visualizing datasets

// COMMAND ----------

// MAGIC %md **Finally, the fun bit!**
// MAGIC 
// MAGIC Data without visualization without a narrative arc, to infer insights or to see a trend, is useless. We always desire to make sense of the results.
// MAGIC 
// MAGIC By saving our Dataset, as a temporary table, I can issue complex SQL queries against it and visualize the results, using notebook's myriad plotting options.

// COMMAND ----------

ds.createOrReplaceTempView("iot_device_data")

// COMMAND ----------

// MAGIC %md Count all devices for a partiular country and map them

// COMMAND ----------

// MAGIC %sql select cca3, count(distinct device_id) as device_id from iot_device_data group by cca3 order by device_id desc limit 100

// COMMAND ----------

// MAGIC %md Let's visualize the results as a pie chart and distribution for devices in the country where C02 are high.

// COMMAND ----------

// MAGIC %sql select cca3, c02_level from iot_device_data where c02_level > 1400 order by c02_level desc

// COMMAND ----------

// MAGIC %md Select all countries' devices with high-levels of C02 and group by cca3 and order by device_ids 

// COMMAND ----------

// MAGIC %sql select cca3, count(distinct device_id) as device_id from iot_device_data where lcd == 'red' group by cca3 order by device_id desc limit 100

// COMMAND ----------

// MAGIC %md find out all devices in countries whose batteries need replacements 

// COMMAND ----------

// MAGIC %sql select cca3, count(distinct device_id) as device_id from iot_device_data where battery_level == 0 group by cca3 order by device_id desc limit 100

// COMMAND ----------

// MAGIC %md Converting a Dataset to RDDs.

// COMMAND ----------

val deviceEventsDS = ds.select($"device_name",$"cca3", $"c02_level").where($"c02_level" > 1300)
// convert to RDDs
val eventsRDD = deviceEventsDS.rdd.take(10)


// COMMAND ----------

// MAGIC %md With Apache Spark 2.0, you can use SparkSession.spark to create a Dataset using range. SparkSession is the new entry point, and it subsumes SparkContext, SqlContext, HiveContext, and StreamingContext.
// MAGIC 
// MAGIC See a companion [Apache Spark 2.0 Preview Blog](https://databricks-prod-cloudfront.cloud.databricks.com/public/4027ec902e239c93eaaa8714f173bcfc/6122906529858466/431554386690884/4814681571895601/latest.html)

// COMMAND ----------

val range100 = spark.range(100)
range100.collect()


// COMMAND ----------

spark
