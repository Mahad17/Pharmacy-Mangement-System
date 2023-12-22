package PharmacyMangementSystem.DTO;

import lombok.Data;

@Data
public class StockDto {
	
	private int stockId;
	private String brandName;
    private String itemName;
	private int quantityAvailable;
    private int unitPrice;
    private String manufacturedDate;

    private String expiryDate;
    private String purchaseDate;

}
