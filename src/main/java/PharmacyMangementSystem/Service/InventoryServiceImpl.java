package PharmacyMangementSystem.Service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import PharmacyMangementSystem.DTO.InventoryDto;
import PharmacyMangementSystem.Model.Inventory;
import PharmacyMangementSystem.Repositories.InventoryRepository;
import PharmacyMangementSystem.Repositories.SalesRepository;

@Service
public class InventoryServiceImpl implements InventoryService{
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	SalesRepository salesRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public  Inventory postInventory(InventoryDto inventoryDto) {
	Inventory inventory=modelMapper.map(inventoryDto, Inventory.class);
	return inventoryRepository.save(inventory); 
	}
	
	@Override
	public InventoryDto getInventory(int inventoryId) {
		Inventory inventory= inventoryRepository.findByInventoryId(inventoryId);
	if (inventory!=null) {
		InventoryDto inventoryDto=modelMapper.map(inventory,InventoryDto.class);
		return inventoryDto;
	}
	return null;
	}
	
	@Override
	public List<Inventory> getAllInventory(){
	return inventoryRepository.findAll();
	}
	
	@Override
	public Boolean deleteInventory(int inventoryId) {
	Inventory inventory= inventoryRepository.findByInventoryId(inventoryId);
	inventoryRepository.delete(inventory);
	 return true;
	}
	
	@Override
	public Inventory updateData( int inventoryId, Inventory inventory) {
		   Optional<Inventory> inventoryFind = inventoryRepository.findById(inventoryId);
		   
		    if (inventoryFind.isPresent()) {
		    	Inventory inventoryGet = inventoryFind.get();	 
		    	inventoryGet.setBrandName(inventory.getBrandName());
		    	inventoryGet.setBillOrInvoice(inventory.getBillOrInvoice());
		    	inventoryGet.setCost(inventory.getCost());
		    	inventoryGet.setDate(inventory.getDate());
		    	inventoryGet.setExpiryDate(inventory.getExpiryDate());
		    	inventoryGet.setManufacturedDate(inventory.getManufacturedDate());
		    	inventoryGet.setProductBarcode(inventory.getProductBarcode());
		    	inventoryGet.setProductName(inventory.getProductName());
		    	inventoryGet.setProduct(inventory.getProduct());
		    	inventoryGet.setPurchasedDate(inventory.getPurchasedDate());
		    	inventoryGet.setSize(inventory.getSize());
		    	inventoryGet.setPurchasedPrice(inventory.getPurchasedPrice());
		    	inventoryGet.setUnitCost(inventory.getUnitCost());
		    	inventoryGet.setUnitPrice(inventory.getUnitPrice());
		    	inventoryGet.setVendor(inventory.getVendor());
		    	inventoryGet.setVendorName(inventory.getVendorName());      
		    	return inventoryRepository.save(inventoryGet);
		    }
		    else {
				return null;
				}
		    }
}