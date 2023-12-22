package PharmacyMangementSystem.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import PharmacyMangementSystem.DTO.StockDto;
import PharmacyMangementSystem.Model.Stock;
import PharmacyMangementSystem.Repositories.StockRepository;
import PharmacyMangementSystem.Response.ResponseHandler;
import PharmacyMangementSystem.Service.StockServiceImpl;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;

@RestController
@RequestMapping("/stock")
public class StockController {

	@Autowired
	StockServiceImpl stockService ;
	
	@Autowired
	StockRepository stockRepository;

    @PostMapping("/{stockId}/sell")
    public ResponseHandler sellStock(@PathVariable int stockId, @RequestParam int quantitySold) {
        try {
            Stock stock= stockService.sellStock(stockId, quantitySold);
            return new ResponseHandler(1,"Stock sold successfully",stock);
        } catch (IllegalArgumentException e) {

            return new ResponseHandler(0,"Error not Sold",null);
            }
    }
    
	@PostMapping("/post")
	public ResponseHandler postStock(@Valid @RequestBody StockDto stock) {
		System.out.println(stock);
		if(StringUtils.isEmpty(stock.getBrandName())||StringUtils.isEmpty(stock.getItemName())||StringUtils.isEmpty(stock.getExpiryDate())||StringUtils.isEmpty(stock.getManufacturedDate())||StringUtils.isEmpty(stock.getPurchaseDate())) {
		
			return new ResponseHandler(0,"Fields Are Empty",null) ;
		
		}
		
		Stock stockPost= stockService.postStock(stock);
		
		if(stockPost!=null) {
		
			return new ResponseHandler(1,"Data inserted Successfully",stockPost) ;
		
		}
		
		else {
		
			return new ResponseHandler(0,"Error! Data Not inserted",null) ;
		
		}
	}
	
	@GetMapping("/get/{stockId}")
	public ResponseHandler getDetails(@PathVariable("stockId") int stockId) {
		
		StockDto stock= stockService.getStock(stockId);
		
		if(stock!=null) {
		
			return new ResponseHandler(1,"Data has been fetched of id : " + stockId ,stock) ;

		}
		
		else {
		
			return new ResponseHandler(0,"No Data available on id : " + stockId,null);
		}
	}
	
	@GetMapping("/get/all")
	public ResponseHandler getAll() {
	
		List<Stock> stocks= stockService.getAllStock();
		
		if(stocks.isEmpty()) 
		{
			return new ResponseHandler(0,"No Data available",null);
		}
		else 
		{
			return new ResponseHandler(1,"All Data has been fetched" ,stocks) ;
		}
	}
	
	@DeleteMapping("/delete/{stockId}")
	public ResponseHandler deleteDetails(@PathVariable int stockId) {
		Stock stock=stockRepository.findByStockId(stockId);
				if(stock!=null) {
					stockService.deleteStock(stockId);

			return new ResponseHandler(1,"Data has been Deleted of id : " + stockId +" with Item Name : " + stock.getItemName(),true) ;
			}
				else {
				return new ResponseHandler(0,"No Data available on id : " + stockId,null);
			}         
	}
	
	@PutMapping("/update/{stockId}")  
	 public ResponseHandler updateData(@PathVariable int stockId,@Valid @RequestBody StockDto stock) {
	  Stock stockSave=stockService.updateData(stockId, stock); 
if (stockSave!=null) {
	
	  return new ResponseHandler(1,"Data has been updated of id : " + stockId,stockSave) ;
		}
	    else {
			return new ResponseHandler(0,"Error ! Id is not updated of id : " + stockId,null);
		}    
	}

}
