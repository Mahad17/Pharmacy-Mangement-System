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

import PharmacyMangementSystem.DTO.ShopDto;
import PharmacyMangementSystem.Model.Shop;
import PharmacyMangementSystem.Repositories.ShopRepository;
import PharmacyMangementSystem.Response.ResponseHandler;
import PharmacyMangementSystem.Service.ShopServiceImpl;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	ShopServiceImpl shopService;
	
	@Autowired
	ShopRepository shopRepository;
	
	       
	       @PostMapping("/login")
	       public ResponseHandler loginAuthenticator(@RequestBody Shop shop) {
	    	   
	    	   System.out.println(shop);
	    	   if (StringUtils.isEmpty(shop.getPhoneNumber()) || StringUtils.isEmpty(shop.getPassword())) {
	               return new ResponseHandler(0, "Phone number and password are required.");
	           } else {
	               String phoneNumber = shop.getPhoneNumber();
	               Boolean shopExists = shopRepository.existsByPhoneNumber(phoneNumber);
	               
	               if (!shopExists) {
	                   return new ResponseHandler(0, "Phone number is not registered.");
	               }
	               else {
	                   Boolean isAuthenticated = shopService.login(shop.getPassword(), phoneNumber);
	                   if (isAuthenticated) {
	                       Shop authenticatedShop = shopRepository.findByPhoneNumber(phoneNumber);
	                       return new ResponseHandler(1, "Login successfull.", authenticatedShop);
	                   } else {
	                       return new ResponseHandler(0, "Incorrect password.");
	                   }
	               }
	           }
	       }
	       
	    @PostMapping("/signUp")
	    public ResponseHandler shopDetails(@Valid @RequestBody Shop shop) {
	    	System.out.println(shop);
	    	if(
	    			StringUtils.isEmpty(shop.getAddress()) ||StringUtils.isEmpty(shop.getEmail()) ||
	    					StringUtils.isEmpty(shop.getPassword())||StringUtils.isEmpty(shop.getPhoneNumber())||
	    					StringUtils.isEmpty(shop.getShopName())||StringUtils.isEmpty(shop.getShopDescription())||
	    			shop.getRegistrationNumber()==null  ) {	    		
	    			return new ResponseHandler(0, "Values must not be null", null) ;	    
	    	}	    	
	    	
	    	if(shopRepository.existsByEmail(shop.getEmail()) ) {
	    		shop.setEmail(shop.getEmail());
	    		return new ResponseHandler(0, "Duplicate Entry : Email Must Be Unique", null) ;
	    	}
			
	    	else if (shopRepository.existsByPhoneNumber(shop.getPhoneNumber())) {
	    		shop.setPhoneNumber(shop.getPhoneNumber());
	    		return new ResponseHandler(0, "Duplicate Entry :  Phone Number Must Be Unique", null) ;
	    	}
				else{
					shopService.postDetails(shop);				
					return new ResponseHandler(1, "Data Inserted Successfully", shop) ;
				}
		}
	
	    @GetMapping("/get/{shopId}")
	    public ResponseHandler getDetails(@PathVariable("shopId") int shopId) {
		
	    	Shop shop= shopService.getDetails(shopId);
	    	if(shop!=null) {
			
	    		return new ResponseHandler(1,"Data has been fetched of id : " + shopId ,shop) ;
		
	    	}
		
	    	else {
			
	    		return new ResponseHandler(0,"No Data available on id : "+shopId,null);
	
	    	}	     
	    }
	    
	     @GetMapping("/getNecessaryDetails/{shopId}")
	     public ResponseHandler getNecessaryDetails(@PathVariable("shopId") int shopId) {
		 
	    	 ShopDto shop= shopService.necessarryDetails(shopId);
		 
	    	 if(shop!=null) {
			
	    		 return new ResponseHandler(1,"Data has been fetched of id : " + shopId ,shop) ;
		 
	    	 }
		 
	    	 else 
		 
	    	 {
			
	    		 return new ResponseHandler(0,"No Data available on id : "+shopId,null);
		 
	    	 }
	      }
	     
	     @GetMapping("/get/name/{shopName}")
	     public ResponseHandler getDetailsByShopName(@PathVariable String shopName) {
	    	 
	    	 Shop shop= shopService.getDetailsByShopName(shopName);
	    	 
	    	 if(shop!=null) {
		 			return new ResponseHandler(1," Data has been fetched of shop : " + shopName ,shop) ;
	 		 }
	 		 
	    	 else {
		 			return new ResponseHandler(0,"No Data found of shop : " + shopName,null);

	 		 }
	     }
	     
	     @GetMapping("/get/all")
	     public ResponseHandler getAll() {
		 
	    	 List<Shop> shop= shopService.getAll();
		 
		 if(shop.isEmpty()) {
			return new ResponseHandler(0,"No Data available",null);
		 }
		 
		 else {
			
			 return new ResponseHandler(1,"All Data has been fetched" ,shop) ;
		 
		 }
	     
	     }
	
	     @DeleteMapping("/delete/{shopId}")
	     public ResponseHandler deleteDetails(@PathVariable int shopId) {
		 
	    	 Shop shop=shopRepository.findByShopId(shopId);
				
		 if(shop!=null) {
		
			 shopService.deleteDetails(shopId);
			
			 return new ResponseHandler(1,"Data has been Deleted of id : " + shopId,true) ;
	
				
		 }
		
		 else {
					
				return new ResponseHandler(0,"No Data available on id : "+shopId,null);
			}
	        }
	     
	    @PutMapping("/update/{shopId}")
	    public ResponseHandler updateData(@PathVariable int shopId ,@Valid @RequestBody Shop shop) {
	   
	    	Shop shopUpdate=shopService.updateData(shopId, shop);
	   
	    	if(shopUpdate!=null) {
			
	    		return new ResponseHandler(1,"Data has been updated of id : " + shopId,shopUpdate) ;
		
	    	}
		
	    	else {
			
	    		return new ResponseHandler(0,"Error Data is not updated of id : " + shopId,shopUpdate) ;
		     }
	    }
}