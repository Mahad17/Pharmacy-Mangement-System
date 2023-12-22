package PharmacyMangementSystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Sales {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
