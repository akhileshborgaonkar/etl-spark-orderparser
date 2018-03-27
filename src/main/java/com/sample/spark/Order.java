package com.sample.spark;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Order implements Serializable {

    private String orderId;
    private String OrderDate;
    private Integer UserId;
    private Double AvgItemPrice;
    private String StartPageUrl;
    private String ErrorMsg;

}
