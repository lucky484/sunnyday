package com.f2b.security.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * 农场产品表
 * @author mozzie.chu
 * @version 16/08/17, 09:48
 *
 */
@Entity
@Table(name = "farm_produce")
@GenericGenerator(name = "FARM_PRODUCE_GEN", strategy = "enhanced-table",
		parameters = {
				@org.hibernate.annotations.Parameter(name = "table_name", value = "table_generator"),
				@org.hibernate.annotations.Parameter(name = "segment_column_name", value = "segment_name"),
				@org.hibernate.annotations.Parameter(name = "segment_value", value = "produce_id"),
				@org.hibernate.annotations.Parameter(name = "value_column_name", value = "next"),
				@org.hibernate.annotations.Parameter(name = "initial_value", value = "1000"),
				@org.hibernate.annotations.Parameter(name = "increment_size", value = "10"),
				@org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo")
		}
)
public class FarmProduce {

	/**
	 * 主键
	 */
	private String produceId;
	
	/**
	 * 批发商
	 */
	private String wholesaler;

	/**
	 * 商品名
	 */
	private String produceName;
	
	/**
	 * 单价
	 */
	private BigDecimal unitPrice;
	
	/**
	 * 简介
	 */
	private String synopsis;

	/**
	 * 建立时间
	 */
	private Date creatDate;
	
	/**
	 * 品种
	 */
	private String variety;
	
	//主键
	@Id
	@GeneratedValue(generator = "FARM_PRODUCE_GEN")
	@Column(name = "produce_id")
	public String getProduceId() {
		return produceId;
	}

	public void setProduceId(String produceId) {
		this.produceId = produceId;
	}

	//批发商
	@Column(name = "wholesaler")
	public String getWholesaler() {
		return wholesaler;
	}

	public void setWholesaler(String wholesaler) {
		this.wholesaler = wholesaler;
	}

	//商品名
	@Column(name = "produce_name")
	public String getProduceName() {
		return produceName;
	}

	public void setProduceName(String produceName) {
		this.produceName = produceName;
	}

	//单价
	@Column(name = "unit_price")
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	//简介
	@Column(name = "synopsis")
	public String getSynopsis() {
		return synopsis;
	}
	
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	//建立时间
	@Column(name = "creat_date")
	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	//品种
	public String getVariety() {
		return variety;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}
	
	
}
