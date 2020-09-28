package model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Data;

@Builder
public @Data class Option implements Serializable{
	
	/**
	 * auto generate
	 */
	private static final long serialVersionUID = 6947716960780322088L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	
	private String startAddress;
	private String endAddress;
	private int carrierId; // 自己分配
	private String carrierType;

	private Route route;
	
	private String startTime;
	private String departureTime;
	private String deliveryTime; // 
	private String endTime;
	private float weight;
	
	private double fee;
	
	@OneToOne
	private User user;
	
	@OneToOne
	private Carrier carrier;// 不确定这里需不需要carrier 还是只需要carrierid就好
	
	@OneToOne
	private Order order;
}
