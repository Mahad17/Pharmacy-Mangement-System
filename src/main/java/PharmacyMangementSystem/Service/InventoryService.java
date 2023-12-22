package PharmacyMangementSystem.Service;

import java.util.List;
import org.springframework.stereotype.Service;

import PharmacyMangementSystem.DTO.InventoryDto;
import PharmacyMangementSystem.Model.Inventory;

@Service
public interface InventoryService {

	public  Inventory postInventory(InventoryDto inventoryDto);
	
	public InventoryDto getInventory(int inventoryId);

	public List<Inventory> getAllInventory();

	public Boolean deleteInventory(int inventoryId);
	
	public Inventory updateData(int inventoryId,Inventory inventory);


}
