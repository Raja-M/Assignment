package com.example.custtrns.service;

import com.example.custtrns.dto.CustTrnsList;
import com.example.custtrns.dto.CustTrnsPerQuarter;
import com.example.custtrns.dto.CustTrnsPerQuarterByCustomer;
import com.example.custtrns.entity.CustTrns;
import com.example.custtrns.repository.CustTrnsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.util.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class CustTrnsServiceImpl implements CustTrnsService {

    @Autowired
    private CustTrnsRepository custTrnsRepository ;

    @Override
    public CustTrns fetchCustTrnsByCustId(Integer custId) {
        Optional<CustTrns> custTrns = custTrnsRepository.findById(custId);


        if( custTrns.isPresent()){
            CustTrns custTrns1 =  custTrns.get();
            log.debug("Custrns : {}" , custTrns1.getCustName());
            return  custTrns1;
        }else{
            return null;
        }
    }
    @Override
    public  Object[] fetchCustTrnsByQuarter(Integer qtr) {
        int firstMonth = (qtr-1)*3+1;
        Map<String, List<CustTrns>> firstQuarterMap = getCustTrnsPerQuarterByCustomer(firstMonth);
        Map<String, List<CustTrns>> secondQuarterMap = getCustTrnsPerQuarterByCustomer(firstMonth+1);
        Map<String, List<CustTrns>> thirdQuarterMap = getCustTrnsPerQuarterByCustomer(firstMonth+2);
        Map<String,CustTrnsPerQuarterByCustomer> map = new HashMap<String,CustTrnsPerQuarterByCustomer>();
        map = getQuarter(map,firstQuarterMap,"January",1);
        map = getQuarter(map,secondQuarterMap,"February",2);
        map = getQuarter(map,thirdQuarterMap,"March",3);
        System.out.println(map);
        return map.entrySet().toArray();
    }
    private Map<String,CustTrnsPerQuarterByCustomer> getQuarter(Map<String,CustTrnsPerQuarterByCustomer> map  ,Map<String, List<CustTrns>> quarterMap , String monthName, int monthNumber){
        for(String key : quarterMap.keySet()){

            CustTrnsList custTrnsList = new CustTrnsList();
            custTrnsList.setName(monthName);
            custTrnsList.setList(quarterMap.get(key));
            CustTrnsPerQuarterByCustomer quarterByCustomer = map.get(key);
            CustTrnsPerQuarter quarter = null;
            if(quarterByCustomer==null) {
                quarterByCustomer = new CustTrnsPerQuarterByCustomer();
                quarterByCustomer.setName(key);
                quarter = new CustTrnsPerQuarter();

            }else{
                quarter = quarterByCustomer.getQuarter();
            }
            if(monthNumber==1){
                quarter.setFirstMonth(custTrnsList);
            }else if(monthNumber==2){
                System.out.println(custTrnsList);
                quarter.setSecondMonth(custTrnsList);
            }else if(monthNumber==3){
                quarter.setThirdMonth(custTrnsList);
            }

            quarterByCustomer.setQuarter(quarter);
            map.put(key,quarterByCustomer);
        }
        return map;
    }
    private  Map<String, List<CustTrns>> getCustTrnsPerQuarterByCustomer(Integer startMonth){
        List<CustTrns> list = custTrnsRepository.findByTransDateBetween(LocalDate.of(2023, startMonth, 01).atStartOfDay(),LocalDate.of(2023, startMonth+1, 01).atStartOfDay());
        List<String> custList = new ArrayList<>(
                new HashSet<>(list.stream().map(CustTrns::getCustName).collect(Collectors.toList())));
        System.out.println(custList);
        Map<String, List<CustTrns>> map = new HashMap<String, List<CustTrns>>();

        for(String s : custList){
            List<CustTrns> filterList = list.stream().filter(custTrns -> custTrns.getCustName().equalsIgnoreCase(s)).collect(Collectors.toList());

            map.put(s,filterList);
        }
        System.out.println(map);
        return map;
    }

    public List<CustTrns> fetchCustTrnsByQuarterOld(Integer qtr) {
        //List<CustTrns> custTrnsList = custTrnsRepository.findByCustName(custName);
        List<CustTrns> custTrnsList = custTrnsRepository.findAll();

        LocalDate fromDt,  toDt ;
        fromDt = toDt =  LocalDate.of(2099, 01, 01);


        if ( qtr == 1 ){
            fromDt  = LocalDate.of(2023, 01, 01);
            toDt    = LocalDate.of( 2023, 03, 31);
        }
        Map<String, HashMap > customerBokets = new HashMap<>();
        HashMap<Integer, Double > monthBuckets = new HashMap<>();
        Iterator<CustTrns> custTrnsIterator = custTrnsList.iterator();

        while ( custTrnsIterator.hasNext()){
            CustTrns custTrns = custTrnsIterator.next();
            LocalDate trnsDate = LocalDate.from(custTrns.transDate);
            System.out.println( "fromDt : " + fromDt + "ToDt : " + toDt + " trnsDate : " + trnsDate + " Trans Amt :" + custTrns.getTransAmt() + " Month : " + trnsDate.getMonthValue() );

            if (trnsDate.isAfter(fromDt) && trnsDate.isBefore(toDt) ) {

                monthBuckets = customerBokets.get(custTrns.getCustName());

                if ( monthBuckets == null ){
                    monthBuckets = new HashMap<>();
                    monthBuckets.put( 1, 0d);
                    monthBuckets.put( 2, 0d);
                    monthBuckets.put( 3, 0d);
                }



                int month = trnsDate.getMonthValue();
                System.out.println(  " Trans Amt :" + custTrns.getTransAmt() + " Month : " + trnsDate.getMonthValue() );

                monthBuckets.put( month, monthBuckets.get(month) + custTrns.transAmt );
                customerBokets.put( custTrns.custName , monthBuckets);
            }
        }

        for( HashMap.Entry< String,HashMap  >  entry: customerBokets.entrySet()){
                HashMap<Integer, Double> monthBkt = customerBokets.get(entry.getKey());
            for( HashMap.Entry<Integer, Double> entry2:   monthBkt.entrySet()   ) {
                System.out.println(" Key :" + entry.getKey() + " Key2 :" + entry2.getKey() + " Value : " + entry2.getValue());
            }
        }

        return custTrnsList;
    }
}
