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
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int productId;
	
	@NotEmpty(message = "must not be empty")
	private String productName;
	
	@OneToOne
	@JoinColumn(name = "productTypeId")
	private ProductType productType;
	
	private String description;
	
	@NotEmpty(message = "must not be empty")
	private String category;
	
//	@OneToOne
//	private Manufacturer manufacturer;
	
	private int unitPrice;
		
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotEmpty(message = "must not be empty")
	private String expiryDate;

}
