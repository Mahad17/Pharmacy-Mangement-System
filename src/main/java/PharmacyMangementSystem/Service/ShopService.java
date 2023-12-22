package PharmacyMangementSystem.Service;

import java.util.List;
import org.springframework.stereotype.Service;
import PharmacyMangementSystem.Model.Shop;

@Service
public interface ShopService {
	
	public List<Shop> getAll() ;

	public Shop postDetails(Shop shop) ;

	public Shop getDetails(int shopId) ;
		
	public Boolean deleteDetails(int shopId) ;
	
	public Shop updateData(int shopId,Shop shop);

	Shop getDetailsByShopName(String shopName);
	
	public Boolean login( String password,String phoneNumber);

}
