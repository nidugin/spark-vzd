#!/bin/bash
docker exec -t -i spark-cluster-spark-master-1 bash -c "spark/bin/spark-submit --packages com.databricks:spark-xml_2.13:0.17.0 --class valuation.VZD  --deploy-mode client --verbose --supervise /opt/spark-apps/spark-vzd.jar 'opt/spark-data/valuation/*/*.xml' 'opt/spark-data/valuation/output/'"
