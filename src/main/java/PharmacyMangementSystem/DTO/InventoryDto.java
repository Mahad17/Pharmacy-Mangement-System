package PharmacyMangementSystem.DTO;

import lombok.Data;
@Data
public class InventoryDto {
	
	private int inventoryId;
	private String brandName;
	private String size;
	private int cost;
	private int unitCost;
	private int productId;
	private String productName;
    private int vendorId;
    private String vendorName;
    private int BillOrInvoice;
    private String expiryDate;
    private String manufacturedDate;
    private String purchasedDate;
    private int purchasedPrice;
    private int unitPrice;
    private String date;
    private String productBarcode;

}
