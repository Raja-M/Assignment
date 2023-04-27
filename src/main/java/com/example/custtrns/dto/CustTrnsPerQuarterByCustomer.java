package com.example.custtrns.dto;

import lombok.Data;

@Data
public class CustTrnsPerQuarterByCustomer {
    private String name;
    private CustTrnsPerQuarter quarter;
}
