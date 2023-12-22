package PharmacyMangementSystem.Service;


import java.util.List;

import org.springframework.stereotype.Service;

import PharmacyMangementSystem.DTO.StockDto;
import PharmacyMangementSystem.Model.Stock;

@Service
public interface StockService {
	
	public Stock postStock(StockDto stockDto);
	
	public StockDto getStock(int stockId);

	public List<Stock> getAllStock();

	public Boolean deleteStock(int stockId);
	
	Stock updateData(int stockId, StockDto stock);


}
