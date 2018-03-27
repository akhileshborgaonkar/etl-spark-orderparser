package com.sample.spark.properties;

import com.sample.spark.exceptions.SparkUtilException;
import lombok.Data;

import java.io.IOException;

@Data
public class ParserProperties extends HiveProperties{

    private static final String LOCAL_PROPERTIES_FILE = "properties/ParserProperties.properties";
    private static ParserProperties instance;
    private String filePath;
    private String outputTable;

    public static ParserProperties getInstance() {

        if (instance == null) {
            try {
                instance = new ParserProperties();
            }
            catch(Exception e) {
                System.err.println("Failed to instantiate properties: " + e);
                System.exit(-1);
            }
        }
        return instance;
    }

    private ParserProperties() throws IOException, SparkUtilException {
        super(LOCAL_PROPERTIES_FILE);
        init();
    }

    private void init() {

        filePath = properties.getProperty("file.path","/tdx/aborgaonkar/sample/scripting_challenge_input_file.txt");
        outputTable = properties.getProperty("output.table","working.order_ab");
    }
}
