package PharmacyMangementSystem.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import PharmacyMangementSystem.Model.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {
	
	Shop findByShopId(int shopId);

	Shop findByEmailOrShopName(String email,String shopName);
	
	Shop findByEmailOrPhoneNumber(String email,String phoneNumber);
	
	Shop findByShopNameIgnoreCase(String shopName);	
	
	Shop findByPhoneNumber(String phoneNumber);
	
	boolean existsByPhoneNumber(String phoneNumber);

	boolean existsByEmail(String email);
	
}
