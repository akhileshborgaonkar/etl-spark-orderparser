package com.sample.spark;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Parser {

    SparkConf conf = new SparkConf().setAppName("Sample-Spark").setMaster("local");
    JavaSparkContext sc = new JavaSparkContext(conf);
    SQLContext sqc = new SQLContext(sc);

    //Paths required to change as desired
    String inputPath = "/user/aborgaonkar/sample/scripting_challenge_input_file.txt";
    String outputPath = "/user/aborgaonkar/sample/output";

    public void parse() {

        //Variables required to change the syntax of date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat newformat = new SimpleDateFormat("yyyy-MM-dd");

        JavaRDD<Order> orderJavaRDD = sc.textFile(inputPath)
                .filter(r -> !r.contains("order_id:date"))  //ignoring header in file

                .map(r -> {
                    Order orderDbo = new Order();
                    String errorMsg = "";

                    try {

                        String[] f = r.split("\t");

                        if( StringUtils.isNotBlank(f[0])) {
                            if (StringUtils.isBlank((f[0].split(":"))[0])) {
                                errorMsg += "-Invalid Order_id-";
                                orderDbo.setOrderId("");
                            } else {
                                orderDbo.setOrderId((f[0].split(":"))[0]);
                            }

                            if (StringUtils.isBlank((f[0].split(":"))[1])) {
                                errorMsg += "-Invalid Date-";
                                orderDbo.setOrderDate("");
                            } else {
                                Date date = formatter.parse((f[0].split(":"))[1]);
                                orderDbo.setOrderDate(newformat.format(date));
                            }
                        }
                        else {
                            errorMsg += "-Invalid Order_Id and Date-";
                        }

                        orderDbo.setUserId(Integer.valueOf(f[1]));

                        int i = 0;
                        Double sum = 0.0;
                        for(int j=2 ;j<=5;j++){
                            if (StringUtils.isNotBlank(f[j]) && Double.valueOf(f[j]) > 0){
                                i++;
                                sum += Double.valueOf(f[j]);
                            }
                        }
                        orderDbo.setAvgItemPrice(sum/i);

                        if(StringUtils.isNotEmpty(f[6])) {
                            if (!(StringUtils.startsWith(f[6], "http://www.test-cpc.com"))) {
                                errorMsg += "-Invalid URL-";
                                orderDbo.setStartPageUrl("");
                            } else {
                                orderDbo.setStartPageUrl(f[6]);
                            }
                        }
                        else{
                            errorMsg += "-Invalid URL-";
                            orderDbo.setStartPageUrl("");
                        }

                    } catch (ArrayIndexOutOfBoundsException e) {
                        errorMsg += "-Less Columns-";
                        }

                    orderDbo.setErrorMsg(errorMsg);
                    return orderDbo;
                });

        //Writing output rows to file on HDFS
        sqc.createDataFrame(orderJavaRDD, Order.class).write()
                .format("com.databricks.spark.csv")
                .option("header","true")
                .option("delimiter","\t")
                .mode(SaveMode.Overwrite)
                .save(outputPath);

    }

    public static void main(String[] args) {

        Parser orderParser = new Parser();
        orderParser.parse();
    }
}
