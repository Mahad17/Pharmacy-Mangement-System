package PharmacyMangementSystem.Service;

import java.util.List;
import org.springframework.stereotype.Service;

import PharmacyMangementSystem.DTO.ProductDto;
import PharmacyMangementSystem.Model.Product;

@Service
public interface ProductService {
	
	public Product postProduct(ProductDto productDto);
	
	public ProductDto getProduct(int productId);

	public List<Product> getAllProducts();

	public Boolean deleteProduct(int productId);
	
	public Product updateData(int productId,Product product);


}
