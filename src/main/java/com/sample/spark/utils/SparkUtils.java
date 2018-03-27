package com.sample.spark.utils;

import com.sample.spark.exceptions.SparkUtilException;
import com.sample.spark.properties.HiveProperties;
import com.sample.spark.properties.SparkProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.hive.HiveContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class SparkUtils {
    private static final Logger logger = LoggerFactory.getLogger(SparkUtils.class);

    public SparkUtils() {
    }

    public static JavaSparkContext initSpark(SparkProperties sparkProperties) {
        SparkConf conf = new SparkConf();
        return initSpark(sparkProperties, conf);
    }

    public static JavaSparkContext initSpark(SparkProperties sparkProperties, SparkConf conf) {
        if (StringUtils.isBlank(System.getProperty("spark.master"))) {
            String local = String.format("local[%d]", sparkProperties.getLocalNumThreads());
            System.err.println("Couldn't find spark.master, using " + local);
            conf.setAppName(sparkProperties.getLocalAppName());
            conf.setMaster(local);
        }

        return new JavaSparkContext(conf);
    }

    public static HiveContext getMetaStoreHiveContext(JavaSparkContext sc, HiveProperties hiveProperties) {
        HiveContext hc = new HiveContext(sc);
        if (StringUtils.isBlank(System.getProperty("spark.master"))) {
            hc.setConf("javax.jdo.option.ConnectionURL", hiveProperties.getLocalHiveJdbc());
            hc.setConf("hive.metastore.uris", hiveProperties.getLocalMetastoreURIs());
        }

        return hc;
    }

    public static Properties getPropertiesFromHdfsFile(Configuration conf, String path) throws IOException, SparkUtilException {
        Path filePath = new Path(path);

        FileSystem fs = FileSystem.get(conf);
        if (!fs.exists(filePath)) {
            logger.error("File not available: {}", filePath.getName());
            throw new SparkUtilException("File does not exist: " + filePath.getName());

        }
        Properties props = new Properties();
        FSDataInputStream inputStream = fs.open(filePath);
        props.load(inputStream);
        inputStream.close();
        return props;
    }

    public static Properties getPropertiesFromInternal(String resource) throws IOException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        InputStream resourceStream = loader.getResourceAsStream(resource);
        properties.load(resourceStream);
        resourceStream.close();
        return properties;
    }
}
