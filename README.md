# order-parser
Samsung coding challenge

@Author: Akhilesh Borgaonkar 	
@Email: borgaonkar.akhilesh@gmail.com	
@Contact: 2035701279

Approach:
My approach of solving this question is by scalable and faster way. The input file resides on HDFS and the rows in the file are read and mapped using spark application. The spark application is written in Java 8. My solution is a combination of spark and hive action which will help in larger scalability knowing that the order records files are very huge and painful to analyze by conventional programming methods.
Pre-requisites:
1.	Linux based machine with capability of handling at least One-node Hadoop cluster
2.	Hadoop and Spark installed
3.	Input file placed on HDFS
Workflow:
1.	The jar for the spark application is generated and put on the Hadoop server along with the input files and update the file path in properties as required.
2.	The application is run using spark-submit command through bash.
3.	The resultant rows are written to HDFS file with tab delimiter at the location .
Implementation:
Find the below parser class implemented to parse and transform the input file:
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

Execution:
1.	Update the input file path (variable name: inputPath) according to the location of input file and desired output directory on HDFS (variable name: outputPath) desired in the Parser class file located at path src/main/java/com/sample/spark/Parser.java

2.	Use the spark-submit command as below to run the jar using bash:
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
       sample-jwd.jar

3.	After the spark process is completed, the results will be written to the part-00000 named file in output directory path.

Difference between two approaches:
If the input file is stored on HDFS then update the properties file as mentioned below with path like “hdfs:///user/data/input.txt”. But, if you want to parse the input file which is local on the machine and not on the HDFS then the file needs to be uploaded to HDFS using command hdfs dfs -put input_file hdfs_directory. In order to parse a local input file, we can use basic file operations but I have kept the basic implementations same thinking about the scalability and speed. Hence, the deployment remains same.

For more insights please find the project attached in the email compressed or clone from github at https://github.com/akhileshborgaonkar/order-parser.
