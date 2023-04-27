package com.example.custtrns.repository;

import com.example.custtrns.entity.CustTrns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustTrnsRepository extends JpaRepository<CustTrns, Integer> {

    List<CustTrns> findByCustName(String custName);
    List<CustTrns> findByTransDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
    List<CustTrns> findByCustNameAndTransDateBetween(String custName,LocalDateTime fromDate, LocalDateTime toDate);
}
