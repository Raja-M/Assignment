package com.example.custtrns.dto;

import lombok.Data;

@Data
public class CustTrnsPerQuarter {
    CustTrnsList firstMonth;
    CustTrnsList secondMonth;
    CustTrnsList thirdMonth;
    public int getPoints(){
        return firstMonth.getPoints() + secondMonth.getPoints() + thirdMonth.getPoints();
    }
}
