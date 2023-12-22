package PharmacyMangementSystem.DTO;

import java.time.LocalDate;
import lombok.Data;
@Data
public class ProductDto {


	private int productId;
	private String productName;
	private int productTypeId;
	private String description;
	private String category;
	private int manufacturerId;
	private int unitPrice;
	private LocalDate expiryDate;

}
