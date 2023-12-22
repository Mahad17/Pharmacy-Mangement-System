package PharmacyMangementSystem.Service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PharmacyMangementSystem.DTO.ProductDto;
import PharmacyMangementSystem.Model.Product;
import PharmacyMangementSystem.Repositories.ProductRepository;
import PharmacyMangementSystem.Repositories.SalesRepository;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	ProductRepository productRepository;

	@Autowired
	SalesRepository salesRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public Product postProduct(ProductDto productDto) {
		Product product=modelMapper.map(productDto,Product.class);
		return productRepository.save(product);
	}

	@Override
	public ProductDto getProduct(int productId) {
		Product product= productRepository.findByProductId(productId);
	if (product!=null) {
		ProductDto productDto=modelMapper.map(product, ProductDto.class);
		return productDto;
		
	}
	else {
		return null;
	}
	}


	@Override
	public Boolean deleteProduct(int productId) {
		Product product=productRepository.findByProductId(productId);
		 productRepository.delete(product);
		 return true;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product updateData( int productId, Product product) {
		   Optional<Product> productFind = productRepository.findById(productId);
		   
		    if (productFind.isPresent()) {
		    	Product productGet = productFind.get();	 
		    	productGet.setCategory(product.getCategory());
		    	productGet.setDescription(product.getDescription());
		    	productGet.setExpiryDate(product.getExpiryDate());
		    	productGet.setProductName(product.getProductName());
		    	productGet.setUnitPrice(product.getUnitPrice());
		    	productGet.setProductType(product.getProductType());
		    	
		      return productRepository.save(productGet);
		    }
		    else {
				return null;
				}
		    }
}
//public Product updateProductQuantityAfterSale(int salesId) {
//    Sales sales = salesRepository.findById(salesId).orElseThrow(() -> new IllegalArgumentException("Invalid sales ID"));
//    
//    Product product = productRepository.findById(sales.getProduct().getProductId()).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
//    
//    int soldQuantity = sales.getQuantity();
//    int currentQuantity = product.getQuantity();
//    int updatedQuantity = currentQuantity - soldQuantity;
//
//    product.setQuantity(updatedQuantity);
//     productRepository.save(product);
//      salesRepository.save(sales);
//     return null;
//  }