# order-parser
Samsung coding challenge

@Author : Akhilesh Borgaonkar 	
@Email: Borgaonkar.akhilesh@gmail.com	
@Contact: 2035701279

Approach:
My approach of solving this question is by scalable and faster way. The input file resides on HDFS and the rows in the file are read and mapped using spark application. The spark application is written in Java 8. My solution is a combination of spark and hive action which will help in larger scalability knowing that the order records files are very huge and painful to analyze by conventional programming methods.
Workflow:
1.	The jar for the spark application is generated and put on the Hadoop server along with the input files and update the file path in properties as required.
2.	The application is run using spark-submit command through bash.
3.	The resultant rows are saved in hive table for better scalability.
4.	The rows from hive table are then written to a text output file using beeline command.
5.	In order to automate this solution, we can use oozie.
Implementation:
Find the below parser class implemented to parse and transform the input file:
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

Execution:
1.	Update the input file path (file.path) and output hive table name (output.table) desired in the properties file at path src/main/resources/properties/ParserProperties.properties
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

3.	After the spark process is completed, run the hive query using beeline as below:

Hive Query is as follows:
set hive.cli.print.header=true;

select * from working.order_ab;

Beeline command is as follows:

	Hive -f output.sql > output.txt;



For more insights please find the project attached in the email compressed or clone from github at https://github.com/akhileshborgaonkar/order-parser.
