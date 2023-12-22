package PharmacyMangementSystem.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import PharmacyMangementSystem.Model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
	
	@Query("SELECT stock FROM Stock stock WHERE stock.quantityAvailable < 20")
	List<Stock> findStocksWithLowQuantity();
	Stock findByStockId(int stockId);
	Stock findByItemNameIgnoreCase(String itemName);
	List<Stock> findByItemNameIn(List<String> itemNames);
}
