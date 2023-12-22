package PharmacyMangementSystem.Service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import PharmacyMangementSystem.DTO.ShopDto;
import PharmacyMangementSystem.Model.Shop;
import PharmacyMangementSystem.Repositories.ShopRepository;

@Service
public class ShopServiceImpl implements ShopService{

	@Autowired
	ShopRepository shopRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	ModelMapper modelMapper;
		
//	@Override
//	public Boolean login(String email, String password,String phoneNumber) {
//        
//		Shop find = shopRepository.findByEmailOrPhoneNumber(email,phoneNumber);
//        
//		if( find == null) {	
//            return null;       
//        }
//        
//		else {
//        	return passwordEncoder.matches(password, find.getPassword());  
//    }
//		}
	@Override
	public Boolean login( String password,String phoneNumber) {
        
		Shop find = shopRepository.findByPhoneNumber(phoneNumber);        
        	return passwordEncoder.matches(password, find.getPassword());  
    
		}
	
	@Override
	public List<Shop> getAll() {
		 return shopRepository.findAll();
	}
	
	@Override
	public Shop postDetails(Shop shop) {
		
	BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
	String encrypt=bCryptPasswordEncoder.encode(shop.getPassword()).toString() ;
	shop.setPassword(encrypt);
	return shopRepository.save(shop);		
	}
	
	@Override
	public Shop getDetails(int shopId) {
		return shopRepository.findByShopId(shopId);
	}
	
	
	@Override
	public Shop getDetailsByShopName(String shopName) {
		return shopRepository.findByShopNameIgnoreCase(shopName);
	}
	
	public ShopDto necessarryDetails(int shopId) {
		Shop shop=shopRepository.findByShopId(shopId);
		ShopDto dto=this.modelMapper.map(shop, ShopDto.class);
		if (dto!=null) {
			return dto;
		}
		else {
			return null;
		}
	}
	
	@Override
	public Boolean deleteDetails(int shopId) {
		  Shop shop= shopRepository.findByShopId(shopId);
	      shopRepository.delete(shop);
		  return true;
	   }
	
	   @Override
	   public Shop updateData(int shopId,Shop shop) {
		  
		    Optional<Shop> shopData = shopRepository.findById(shopId);
		    if (shopData.isPresent()) {
		      Shop shop2 = shopData.get();
		      shop2.setShopName(shop.getShopName());
		      shop2.setShopDescription(shop.getShopDescription());
		      shop2.setAddress(shop.getAddress());
		      shop2.setEmail(shop.getEmail());
		      shop2.setPassword(shop.getPassword());
		      shop2.setPhoneNumber(shop.getPhoneNumber());
		      shop2.setRegistrationNumber(shop.getRegistrationNumber()); 
		      return shopRepository.save(shop2);
			}
		    else 
		    {
		    	return null;
			}
		}

}
