package com.sample.spark.properties;

import com.sample.spark.definitions.Definitions;
import com.sample.spark.exceptions.SparkUtilException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class HiveProperties extends SparkProperties {

    private String localHiveWarehouse;
    private String localHiveJdbc;
    private String localMetastoreURIs;

    public String getLocalHiveWarehouse() {
        return localHiveWarehouse;
    }

    public String getLocalHiveJdbc() {
        return localHiveJdbc;
    }

    protected HiveProperties(String localPropertiesFile) throws IOException, SparkUtilException {
        super(localPropertiesFile);
        init();
    }

    private void init() throws IOException, SparkUtilException {

        localHiveWarehouse = properties.getProperty(Definitions.Properties.LOCAL_HIVE_WAREHOUSE,
                Definitions.PropertyDefaults.LOCAL_HIVE_WAREHOUSE);

        localMetastoreURIs = properties.getProperty(Definitions.Properties.LOCAL_METASTORE_URIS,
                Definitions.PropertyDefaults.LOCAL_METASTORE_URIS);

        localHiveJdbc = properties.getProperty(Definitions.Properties.LOCAL_HIVE_JDBC, Definitions.PropertyDefaults.LOCAL_HIVE_JDBC);


    }

}

