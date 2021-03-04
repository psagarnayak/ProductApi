package com.example.products.db.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
	
	@TableGenerator(name = "ProductIdGen", table = "ProductID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "id_Gen", initialValue = 10000, allocationSize = 10)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Address_Gen")
	private long productId;
	
	private String productName;
	
	private String productDescription;
	
	private BigDecimal price;
}
