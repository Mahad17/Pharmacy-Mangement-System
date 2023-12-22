package PharmacyMangementSystem.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PharmacyMangementSystem.DTO.InventoryDto;
import PharmacyMangementSystem.Model.Inventory;
import PharmacyMangementSystem.Repositories.InventoryRepository;
import PharmacyMangementSystem.Response.ResponseHandler;
import PharmacyMangementSystem.Service.InventoryServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	InventoryServiceImpl inventoryService ;
	
	@Autowired
	InventoryRepository inventoryRepository;

	@PostMapping("/post")
	public ResponseHandler postInventory(@Valid @RequestBody InventoryDto inventory) {
		Inventory inventoryPost= inventoryService.postInventory(inventory);
		if(inventoryPost!=null) {
		return new ResponseHandler(1,"Data inserted Successfully",inventoryPost) ;
		}else {
			return new ResponseHandler(0,"Error! Data Not inserted",null) ;
		}
	}
	@PostMapping("/post/withoutDTO")
	public ResponseHandler postInventory2(@Valid @RequestBody Inventory inventory) {
		Inventory inventoryPost= inventoryRepository.save(inventory);
		if(inventoryPost!=null) {
		return new ResponseHandler(1,"Data inserted Successfully",inventoryPost) ;
		}else {
			return new ResponseHandler(0,"Error! Data Not inserted",null) ;
		}
	}
	
	@GetMapping("/get/{inventoryId}")
	public ResponseHandler getDetails(@PathVariable("inventoryId") int inventoryId) {
		InventoryDto inventory= inventoryService.getInventory(inventoryId);
		if(inventory!=null) {
			return new ResponseHandler(1,"Data has been fetched of id : " + inventoryId ,inventory) ;

		}else {
			return new ResponseHandler(0,"No Data available on id : " + inventoryId,null);
		}
	}
	
	@GetMapping("/get/all")
	public ResponseHandler getAll() {
		List<Inventory> inventory= inventoryService.getAllInventory();
		if(inventory.isEmpty()) {
			return new ResponseHandler(0,"No Data available",null);

		}else {
			return new ResponseHandler(1,"All Data has been fetched" ,inventory) ;
		}
	}
	
	@DeleteMapping("/delete/{inventoryId}")
	public ResponseHandler deleteDetails(@PathVariable int inventoryId) {
		Inventory inventory=inventoryRepository.findByInventoryId(inventoryId);
				if(inventory!=null) {
					 inventoryService.deleteInventory(inventoryId);

			return new ResponseHandler(1,"Data has been Deleted of id : " + inventoryId,true) ;
			}
				else {
				return new ResponseHandler(0,"No Data available on id : "+inventoryId,null);
			}
	         }
	
	@PutMapping("/update/{inventoryId}")  
	 public ResponseHandler updateData(@PathVariable int inventoryId,@Valid @RequestBody Inventory inventory) {
	   
	    	Inventory inventorySave= inventoryService.updateData(inventoryId, inventory);
	    	if (inventorySave!=null) {
				
			
	      return new ResponseHandler(1,"Data has been updated of id : " + inventoryId,inventorySave) ;
		}
	    else {
			return new ResponseHandler(0,"Error ! Id is not updated of id : " + inventoryId,null);
		}
	    }

}
