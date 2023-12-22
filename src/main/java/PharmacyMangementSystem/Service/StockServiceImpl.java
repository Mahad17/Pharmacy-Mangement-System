package PharmacyMangementSystem.Service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import PharmacyMangementSystem.DTO.StockDto;
import PharmacyMangementSystem.Model.Stock;
import PharmacyMangementSystem.Repositories.InventoryRepository;
import PharmacyMangementSystem.Repositories.StockRepository;

@Service
public class StockServiceImpl implements StockService{
	
	@Autowired
	StockRepository stockRepository;
	@Autowired
	ModelMapper modelMapper;
	
	
	public List<Stock> findByQuantity() {
		return stockRepository.findStocksWithLowQuantity();	
		}
	
	@Override
	public  Stock postStock(StockDto stockDto) {
		Stock stock=modelMapper.map(stockDto, Stock.class);
		return stockRepository.save(stock); 
	}
	
	  public Stock sellStock(int stockId, int quantitySold) {
	        Stock stock = stockRepository.findByStockId(stockId);
	        int currentQuantity = stock.getQuantityAvailable();
	        int newQuantity = currentQuantity - quantitySold;
	        
	        if (newQuantity < 0) {
	            throw new IllegalArgumentException("Not enough stock available");
	        }
	        
	        stock.setQuantityAvailable(newQuantity);
	        return stockRepository.save(stock);
	    }
	
	
	@Override
	public StockDto getStock(int stockId) {
		
		Stock stock= stockRepository.findById(stockId).get();
	if (stock!=null) {
		StockDto stockDto=modelMapper.map(stock, StockDto.class);
		return stockDto;
		
	}
	else {
		return null;
	}
	}
	
	@Override
	public List<Stock> getAllStock(){
	return stockRepository.findAll();
	}
	
	@Override
	public Boolean deleteStock(int stockId) {
	Stock stock= stockRepository.findByStockId(stockId);
	stockRepository.delete(stock);
	 return true;

	 }
	@Autowired
	InventoryRepository inventoryRepository;
	 @Override
	 public Stock updateData( int stockId, StockDto stock) {
		   Optional<Stock> stockFind = stockRepository.findById(stockId);

		    if (stockFind.isPresent()) {

		    	Stock stockGet = stockFind.get();
		    	stockGet.setItemName(stock.getItemName());;
		    	stockGet.setExpiryDate(stock.getExpiryDate());
		    	stockGet.setPurchaseDate(stock.getPurchaseDate());
		    	stockGet.setQuantityAvailable(stock.getQuantityAvailable());
		    	stockGet.setManufacturedDate(stock.getManufacturedDate());
		    	stockGet.setUnitPrice(stock.getUnitPrice());
		    	stockGet.setBrandName(stock.getBrandName());

		    	return	stockRepository.save(stockGet); 
		    }
		    
		    else {
				return null;
				}
		    }
	 public Stock update( int stockId, StockDto stockDto) {
				   Optional<Stock> stock  = stockRepository.findById(stockId);
				   
				    if (stock.isPresent()) {
				    	Stock stockGet = stock.get();				
				    	stockGet.setBrandName(stockDto.getBrandName());
				    	stockGet.setExpiryDate(stockDto.getExpiryDate());
				    	stockGet.setItemName(stockDto.getItemName());
				    	stockGet.setManufacturedDate(stockDto.getManufacturedDate());
				    	stockGet.setPurchaseDate(stockDto.getPurchaseDate());
				    	stockGet.setQuantityAvailable(stockDto.getQuantityAvailable());
				    	stockGet.setUnitPrice(stockDto.getUnitPrice());				    	
				    	return stockRepository.save(stockGet);
				    }
				    else {
						return null;
						}
				    }	
	 
	    public List<Stock> getSelectedMedicines(List<Integer> stockId) {
	        return stockRepository.findAllById(stockId);
	    }
//	    public Stock getSelectedMedicinesInt(String itemName) {
//	        return stockRepository.findAllByItemNameIgnoreCase(itemName);
//	    }

		public List<Stock> getSelectedMedicinesByItemName(List<String> itemNames) {
			return	stockRepository.findByItemNameIn(itemNames);			 
		}
}
