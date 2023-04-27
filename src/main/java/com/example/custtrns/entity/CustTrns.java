package com.example.custtrns.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table( name= "custtrns")
public class CustTrns {
	@Id
	@Column( name = "custid")
	public int id;
	@Column( name = "custname")
	public String custName;
	@Column( name = "transamt")
	public double  transAmt;
	@Column( name = "transdate")
	public LocalDateTime transDate;
}
