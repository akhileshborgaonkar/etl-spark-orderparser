package com.sample.spark;

import com.sample.spark.properties.ParserProperties;
import com.sample.spark.utils.SparkUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.hive.HiveContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Parser {

    private ParserProperties prop;
    private JavaSparkContext jsc;
    private HiveContext hc;
    private static Logger log = Logger.getLogger(Parser.class);

    public Parser(ParserProperties prop) {

        this.prop = prop;
        jsc = SparkUtils.initSpark(prop);
        hc = SparkUtils.getMetaStoreHiveContext(jsc, prop);
    }

    public Parser(JavaSparkContext jc, HiveContext hc, ParserProperties prop) {

        this.jsc = jsc;
        this.hc = hc;
        this.prop = prop;
    }


    public void parse() {
        hc.sql("use default");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat newformat = new SimpleDateFormat("yyyy-MM-dd");

        JavaRDD<Order> orderJavaRDD = jsc.textFile(prop.getFilePath())
                .filter(r -> !r.contains("order_id:date"))

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

                    } catch (Exception e) {
                        errorMsg += "-Less Columns-";
                        e.printStackTrace();

                        }

                    orderDbo.setErrorMsg(errorMsg);
                    return orderDbo;
                });

        hc.createDataFrame(orderJavaRDD, Order.class)
                .write().mode(SaveMode.Overwrite).format("ORC")
                .saveAsTable(prop.getOutputTable());

    }

    public static void main(String[] args) {

        ParserProperties prop = ParserProperties.getInstance();

        Parser orderParser = new Parser(prop);
        orderParser.parse();
    }
}
