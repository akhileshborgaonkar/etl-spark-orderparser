package com.sample.spark.properties;

import com.sample.spark.definitions.Definitions;
import com.sample.spark.exceptions.SparkUtilException;
import com.sample.spark.utils.SparkUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;
import java.util.Properties;

public abstract class SparkProperties {

    private boolean localMode;
    private int localNumThreads;
    private String localAppName;
    protected Properties properties;

    @SuppressWarnings("unused")
    public boolean getLocalMode() {
        return localMode;
    }

    public int getLocalNumThreads() {
        return localNumThreads;
    }

    public String getLocalAppName() {
        return localAppName;
    }

    protected SparkProperties(String localPropertiesFile) throws IOException, SparkUtilException {
        init(localPropertiesFile);
    }

    private void init(String localPropertiesFile) throws IOException, SparkUtilException {

        if (StringUtils.isNotEmpty(System.getProperty(Definitions.PROPERTY_FILE_NAME_SYSTEM_PROP_NAME))) {
            properties = SparkUtils.getPropertiesFromHdfsFile(new Configuration(), System.getProperty(Definitions.PROPERTY_FILE_NAME_SYSTEM_PROP_NAME));

        } else {
            System.out.println("Getting propertes from local jar resource");
            properties = SparkUtils.getPropertiesFromInternal(localPropertiesFile);
        }

        localMode = Boolean.parseBoolean(properties.getProperty(Definitions.Properties.LOCAL_MODE, Definitions.PropertyDefaults.LOCAL_MODE));
        localNumThreads = Integer.parseInt(properties.getProperty(Definitions.Properties.LOCAL_NUM_THREADS, Definitions.PropertyDefaults.LOCAL_NUM_THREADS));
        localAppName = properties.getProperty(Definitions.Properties.LOCAL_APP_NAME);

        if (localMode && StringUtils.isBlank(localAppName)) {
            throw new SparkUtilException("Missing localAppName for local testing");
        }

    }


}

