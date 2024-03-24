# Apache Spark (Scala) project for XML propeprty processing with Docker

Created by [Nikita Roldugins](https://www.linkedin.com/in/nidugin/)

### Install and setup

- install [IntelliJ IDEA](https://jetbrains.com/idea)
- install [Docker Desktop](https://docker.com)
- either clone the repo or download as zip
- open with IntelliJ as an SBT project
- Windows users, you need to set up some Hadoop-related configs - use [this guide](/HadoopWindowsUserSetup.md) 

As you open the project, the IDE will take care to download and apply the appropriate library dependencies.

To set up the dockerized Spark cluster we will do the following:

- open a terminal and navigate to `spark-cluster`
- run `build-images.sh` (if you don't have a bash terminal, just open the file and run each line one by one)
- run `docker-compose up --scale spark-worker=3`
- go to `root` folder and move`spark-vzd.jar` to `spark-cluster/apps`
- go to `root` folder and move `valuation` folder to `spark-cluster/data`


To interact with the Spark cluster, the folders `data` and `apps` inside the `spark-cluster` folder are mounted onto the Docker containers under `/opt/spark-data` and `/opt/spark-apps` respectively.

To run an appliaction, go to `spark-cluster` and execute:


```
./run-app.sh
```


### The result

Go to the folder `spark-cluster/data/valuation/output`. Here you can see the JSON file. If we open it then you can see the result:

```
{
   "ObjectCadastralValue":null,
   "ObjectCadastralValueDate":null,
   "ObjectForestValue":null,
   "ObjectForestValueDate":null,
   "ObjectRelation":{
      "ObjectCadastreNr":98440010065,
      "ObjectType":"PROPERTY"
   },
   "PropertyCadastralValue":3193227,
   "PropertyCadastralValueDate":"2024-01-03",
   "PropertyValuation":18740108,
   "PropertyValuationDate":"2024-01-03"
}
```

### Errors
Here is the list of errors that can be faced and instructions how to fix them:

* `An exception or error caused a run to abort: class org.apache.spark.storage.StorageUtils$ (in unnamed module @0x3b2cf7ab) cannot access class sun.nio.ch.DirectBuffer (in module java.base) because module java.base does not export sun.nio.ch to unnamed module` - can be fixed by adding VM options in Intellij configuration
```
--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.lang.invoke=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED --add-opens=java.base/java.io=ALL-UNNAMED --add-opens=java.base/java.net=ALL-UNNAMED --add-opens=java.base/java.nio=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.util.concurrent=ALL-UNNAMED --add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED --add-opens=java.base/sun.nio.ch=ALL-UNNAMED --add-opens=java.base/sun.nio.cs=ALL-UNNAMED --add-opens=java.base/sun.security.action=ALL-UNNAMED --add-opens=java.base/sun.util.calendar=ALL-UNNAMED --add-opens=java.security.jgss/sun.security.krb5=ALL-UNNAMED
```