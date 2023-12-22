package PharmacyMangementSystem.Service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PharmacyMangementSystem.DTO.ProductTypeDto;
import PharmacyMangementSystem.Model.ProductType;
import PharmacyMangementSystem.Repositories.ProductTypeRepository;

@Service
public class ProductTypeServiceImpl implements ProductTypeService{
	
	@Autowired
	ProductTypeRepository productTypeRepository;
	@Autowired
	ModelMapper modelMapper;
	@Override
	public  ProductType postProductType(ProductTypeDto productTypeDto) {
	ProductType productType=modelMapper.map(productTypeDto,ProductType.class );
		return productTypeRepository.save(productType); 
	}
	
	@Override
	public ProductTypeDto getProductType(int productTypeId) {
		ProductType productType= productTypeRepository.findByProductTypeId(productTypeId);
	if (productType!=null) {
		ProductTypeDto productTypeDto=modelMapper.map(productType,ProductTypeDto.class );
	return productTypeDto;
	}
	else {
		return	null;
	}
	}
	
	@Override
	public List<ProductType> getAllProductType(){
	return productTypeRepository.findAll();
	}
	
	@Override
	public Boolean deleteProductType(int productTypeId) {
	ProductType productType= productTypeRepository.findByProductTypeId(productTypeId);
	productTypeRepository.delete(productType);
	 return true;
	}
	
	@Override
	 public ProductType updateData( int productTypeId, ProductType productType) {
	 Optional<ProductType> productTypeFind = productTypeRepository.findById(productTypeId);
		   
		    if (productTypeFind.isPresent()) {
		    	ProductType productTypeGet = productTypeFind.get();	 
		    	productTypeGet.setManufacturer(productType.getManufacturer());
		    	productTypeGet.setProductType(productType.getProductType());
		    	return productTypeRepository.save(productTypeGet);	
		   }
		    else {
				return null;
				}
		    }
}
