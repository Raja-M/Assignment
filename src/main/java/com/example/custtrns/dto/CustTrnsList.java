package com.example.custtrns.dto;

import com.example.custtrns.entity.CustTrns;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustTrnsList {
    String name;
    List<CustTrns> list;
    public int getPoints(){
        if(list == null ) return 0;
        int points=0;
        for(CustTrns ct :list){
            Double trnsAmount = ct.getTransAmt();
            Integer ita = trnsAmount.intValue();
            points= points+(ita-100)*2+ita-50;
        }
        return points;
    }
}
