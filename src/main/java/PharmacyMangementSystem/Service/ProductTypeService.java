package PharmacyMangementSystem.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import PharmacyMangementSystem.DTO.ProductTypeDto;
import PharmacyMangementSystem.Model.ProductType;

@Service
public interface ProductTypeService {

	public ProductType postProductType(ProductTypeDto productTypeDto);
	
	public ProductTypeDto getProductType(int productTypeId);

	public List<ProductType> getAllProductType();

	public Boolean deleteProductType(int productTypeId);

	public ProductType updateData(int productTypeId,ProductType productType);
}
