package com.example.custtrns.service;

import com.example.custtrns.dto.CustTrnsPerQuarter;
import com.example.custtrns.entity.CustTrns;

import java.util.List;

public interface CustTrnsService {
    public CustTrns fetchCustTrnsByCustId(Integer custId);

    public Object[] fetchCustTrnsByQuarter(Integer qtr);
}
