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

import PharmacyMangementSystem.DTO.ProductTypeDto;
import PharmacyMangementSystem.Model.ProductType;
import PharmacyMangementSystem.Repositories.ProductTypeRepository;
import PharmacyMangementSystem.Response.ResponseHandler;
import PharmacyMangementSystem.Service.ProductTypeServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/productType")
public class ProductTypeController {

	@Autowired
	ProductTypeServiceImpl productTypeService ;
	
	@Autowired
	ProductTypeRepository productTypeRepository;

	@PostMapping("/post")
	public ResponseHandler postCustomer(@Valid @RequestBody ProductTypeDto productType) {
		ProductType productTypePost= productTypeService.postProductType(productType);
		if(productTypePost!=null) {
		return new ResponseHandler(1,"Data inserted Successfully",productTypePost) ;
		}else {
			return new ResponseHandler(0,"Error! Data Not inserted",null) ;
		}
	}
	
	@GetMapping("/get/{productTypeId}")
	public ResponseHandler getDetails(@PathVariable("productTypeId") int productTypeId) {
		ProductTypeDto productType= productTypeService.getProductType(productTypeId);
		if(productType!=null) {
			return new ResponseHandler(1,"Data has been fetched of id : " + productTypeId ,productType) ;

		}else {
			return new ResponseHandler(0,"No Data available on id : " + productTypeId,null);
		}
	}
	
	@GetMapping("/get/all")
	public ResponseHandler getAll() {
		List<ProductType> productType= productTypeService.getAllProductType();
		if(productType.isEmpty()) {
			return new ResponseHandler(0,"No Data available",null);

		}else {
			return new ResponseHandler(1,"All Data has been fetched" ,productType) ;
		}
	}
	
	@DeleteMapping("/delete/{productTypeId}")
	public ResponseHandler deleteDetails(@PathVariable int productTypeId) {
		ProductType productType=productTypeRepository.findByProductTypeId(productTypeId);
				if(productType!=null) {
					productTypeService.deleteProductType(productTypeId);

			return new ResponseHandler(1,"Data has been Deleted of id : " + productTypeId,true) ;
			}
				else {
				return new ResponseHandler(0,"No Data available on id : "+productTypeId,null);
			}
	         }
	
	@PutMapping("/update/{productTypeId}")  
	 public ResponseHandler updateData(@PathVariable int productTypeId,@Valid @RequestBody ProductType productType) {
	  	ProductType productTypeSave= productTypeService.updateData(productTypeId, productType);
	if (productTypeSave!=null) {
		
	    	return new ResponseHandler(1,"Data has been updated of id : " + productTypeId,productTypeSave) ;
		}
	    else {
			return new ResponseHandler(0,"Error ! Id is not updated of id : " + productTypeId,null);
		     }
	    }
}
