package com.example.custtrns.dto;

import lombok.Data;

@Data
public class CustTrnsPerQuarter {
    CustTrnsList firstMonth = new CustTrnsList();
    CustTrnsList secondMonth = new CustTrnsList();
    CustTrnsList thirdMonth = new CustTrnsList();
    public int getPoints(){
        return firstMonth.getPoints() + secondMonth.getPoints() + thirdMonth.getPoints();
    }
}
