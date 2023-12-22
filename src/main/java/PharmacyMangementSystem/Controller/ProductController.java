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

import PharmacyMangementSystem.DTO.ProductDto;
import PharmacyMangementSystem.Model.Product;
import PharmacyMangementSystem.Repositories.ProductRepository;
import PharmacyMangementSystem.Response.ResponseHandler;
import PharmacyMangementSystem.Service.ProductServiceImpl;
import PharmacyMangementSystem.Service.SalesServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductServiceImpl productService ;
	
	@Autowired
	SalesServiceImpl salesService;
	
	@Autowired
	ProductRepository productRepository;

	@PostMapping("/post")
	public ResponseHandler postCustomer(@Valid @RequestBody ProductDto product) {
		Product productPost= productService.postProduct(product);
		if(productPost!=null) {
		return new ResponseHandler(1,"Data inserted Successfully",productPost) ;
		}else {
			return new ResponseHandler(0,"Error! Data Not inserted",null) ;
		}
	}
	
	@GetMapping("/get/{productId}")
	public ResponseHandler getDetails(@PathVariable("productId") int productId) {
		ProductDto product= productService.getProduct(productId);
		if(product!=null) {
			return new ResponseHandler(1,"Data has been fetched of id : " + productId ,product) ;

		}else {
			return new ResponseHandler(0,"No Data available on id : " + productId,null);
		}
	}
	
	@GetMapping("/get/all")
	public ResponseHandler getAll() {
		List<Product> products= productService.getAllProducts();
		if(products.isEmpty()) {
			return new ResponseHandler(0,"No Data available",null);

		}else {
			return new ResponseHandler(1,"All Data has been fetched" ,products) ;
		}
	}
	
	@DeleteMapping("/delete/{productId}")
	public ResponseHandler deleteDetails(@PathVariable int productId) {
		Product product=productRepository.findByProductId(productId);
				if(product!=null) {
					productRepository.deleteById(productId);

			return new ResponseHandler(1,"Data has been Deleted of id : " + productId,true) ;
			}
				else {
				return new ResponseHandler(0,"No Data available on id : "+productId,null);
			}
	         }
	
	@PutMapping("/update/{productId}")  
	 public ResponseHandler updateData(@PathVariable int productId,@Valid @RequestBody Product product) {
	    	
	      Product productSave= productService.updateData(productId, product);
	      if (productSave!=null) {
			
		
	      return new ResponseHandler(1,"Data has been updated of id : " + productId,productSave) ;
		}
	    else {
			return new ResponseHandler(0,"Error ! Id is not updated of id : " + productId,null);
		}
	    }

	
}

//@GetMapping("/quantitySubtract/{salesId}")
//private ResponseHandler getQuantityAfterSubtraction(@PathVariable int salesId) {
//	Product product=productService.updateProductQuantityAfterSale(salesId);
//	if (product!=null) {
//		return new ResponseHandler(1,"Data has been updated",product) ;
//	}
//	else {
//		return new ResponseHandler(0,"Error! Data Not inserted",null) ;
//	}
//}