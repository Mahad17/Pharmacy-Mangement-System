package PharmacyMangementSystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
public class Stock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int stockId;
	
    @NotEmpty(message = "must not be empty")
	private String brandName;
	
    @NotEmpty(message = "must not be empty")
    private String itemName;
	
	private int quantityAvailable;

	private int unitPrice;

	private String manufacturedDate;

    @NotEmpty(message = "must not be empty")
	private String expiryDate;

    @NotEmpty(message = "must not be empty")
	private String purchaseDate;
}
