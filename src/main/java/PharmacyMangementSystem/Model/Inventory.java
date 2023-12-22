package PharmacyMangementSystem.Model;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int inventoryId;
	@NotEmpty(message = "must not be empty")
	private String brandName;
	
	@NotEmpty(message = "must not be empty")
	private String size;
	
	private int cost;
	
	private int unitCost;
	
	
	@OneToOne
	@JoinColumn(name = "productId")
	private Product product;
	
//	@OneToOne
//	private ProductType productType;
	
	@NotEmpty(message = "must not be empty")
	private String productName;
    
	@OneToOne
	@JoinColumn(name = "vendorId")
	private Vendor vendor;
    
	@NotEmpty(message = "must not be empty")
    private String vendorName;
    
    private int BillOrInvoice;
    

    @NotEmpty(message = "must not be empty")
    @JsonFormat(pattern = "YYYY-MM-DD")
    private String expiryDate;
    
    @JsonFormat(pattern = "YYYY-MM-DD")
    @NotEmpty(message = "must not be empty")
    private String manufacturedDate;
    
    @NotEmpty(message = "must not be empty")
    @JsonFormat(pattern = "YYYY-MM-DD")
    private String purchasedDate;
    
    private int purchasedPrice;
    
    private int unitPrice;
    
    @NotEmpty(message = "must not be empty")
    @JsonFormat(pattern = "YYYY-MM-DD")
    private String date;
    
    @NotEmpty(message = "must not be empty")
    private String productBarcode;
}
