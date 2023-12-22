package PharmacyMangementSystem.DTO;

import lombok.Data;

@Data
public class SalesDto {
	
	private int salesId;
	private String customerName;
	private int invoiceNumber;
	private String invoiceDate;
	private String time;
	private String itemName;
	private int quantitySold;
	private int unitPrice;
	private int totalPrice;
	private String paymentMethod;	
	private String prescription;
	

	
}
