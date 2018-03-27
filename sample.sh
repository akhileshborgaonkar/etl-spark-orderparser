#! /bin/sh

spark-submit \
        --class com.sample.spark.Parser \
        --master yarn-client \
        --name SampleSpark \
        --num-executors 2 \
        --executor-cores 1 \
        --conf spark.yarn.executor.memoryOverhead=2048 \
        --conf spark.yarn.am.memory=2048M \
        --executor-memory 4096M \
        --driver-memory 4096M \
       /home/aborgaonkar/sample/sample-jwd.jar

if [ $? -eq 0 ]; then
    exit 0;
else
    exit 1;
fi
