package PharmacyMangementSystem.Controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import PharmacyMangementSystem.DTO.SalesDto;
import PharmacyMangementSystem.Model.Sales;
import PharmacyMangementSystem.Model.Stock;
import PharmacyMangementSystem.Repositories.SalesRepository;
import PharmacyMangementSystem.Repositories.StockRepository;
import PharmacyMangementSystem.Response.ResponseHandler;
import PharmacyMangementSystem.Service.SalesServiceImpl;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/sales")
public class SalesController {

	@Autowired
	SalesServiceImpl salesService ;
	
	@Autowired
	SalesRepository salesRepository;
	
	@Autowired
	StockRepository stockRepository;
	
	@Autowired
	ModelMapper modelMapper;

//	@PostMapping("/stockUpdate/sell")
//    public ResponseHandler sellProducts(@Valid @RequestBody SalesDto sales) {
//				Object stockUpdate= salesService.subtractStockQuantity(sales);
// 
//				if (stockUpdate==null) {
//
//        	Stock stock=stockRepository.findByStockId(sales.getStockId());
//        	return new ResponseHandler(0,"Product Is Out Of Stock - Remaining "+ sales.getItemName() + " : "+stock.getQuantityAvailable(),stockUpdate);		
//        }
//        else {
//        	Stock stock=stockRepository.findByStockId( sales.getStockId());
//            return new ResponseHandler(1,sales.getQuantitySold()+" "+ sales.getItemName() + " sold successfully.Remaining "+ sales.getItemName() + " : " + stock.getQuantityAvailable(),stockUpdate);
//        }
//    	}

	@PostMapping("/postSalesData")
	  public ResponseHandler postSalesData(@RequestBody Sales sales) {
		System.out.println(sales);
        if (StringUtils.isEmpty(sales.getCustomerName()) || 
        	StringUtils.isEmpty(sales.getItemName())|| 
        	StringUtils.isEmpty(sales.getPaymentMethod())
        	){
            return new ResponseHandler(0, "Fields Are Empty");
        }

        String customerNameSet = "Walk-In-Customer";
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = currentDate.format(formatter);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime currentTime = LocalTime.now();
        String formattedTime = currentTime.format(formatterTime);
        if(sales.getPrescription()==null) {
        sales.setPrescription("None");
        }
        
        sales.setCustomerName(customerNameSet);
        Stock stock = stockRepository.findByItemNameIgnoreCase(sales.getItemName());
        if (stock == null) {
            return new ResponseHandler(0, "Medicine not found");
        }

        int stockQuantity = stock.getQuantityAvailable();
        int salesQuantity = sales.getQuantitySold();
        int remainingQuantity = stockQuantity - salesQuantity;
        stock.setQuantityAvailable(remainingQuantity);
        stockRepository.save(stock);

        Random random = new Random();
        int invoiceNumber = random.nextInt(90000000) + 10000000;
        sales.setTime(formattedTime);
        sales.setInvoiceNumber(invoiceNumber);
        sales.setInvoiceDate(formattedDateTime);
        Sales salesPost = salesRepository.save(sales);
        System.out.println(salesPost);
        return new ResponseHandler(1, "Sale has been sent", salesPost);
    }


@GetMapping("/getById/{salesId}")
	public ResponseHandler getDetailsById(@PathVariable("salesId") int salesId) {
		SalesDto sales=salesService.getSales(salesId);
		    
	 if(sales==null) {
	    	 	return new ResponseHandler(0,"No Data available on this Sales Id : " + salesId,null);
		 }
		else {
			return new ResponseHandler(1,"Data has been fetched of id : " + salesId ,sales) ;

		}
	}
	
	@GetMapping("/get/{salesId}")
	public ResponseHandler getDetails(@PathVariable("salesId") int salesId) {
		Sales sales=salesService.getSale(salesId);
		if(sales!=null) {
			return new ResponseHandler(1,"Data has been fetched of id : " + salesId ,sales) ;
		}
		else {
			return new ResponseHandler(0,"No Data available on id : " + salesId,null);
		}
	}
	
	@GetMapping("/get/all")
	public ResponseHandler getAll() {
	  List<Sales> sales= salesService.getAllSales();
	  
	  if(sales.isEmpty()) {
			return new ResponseHandler(0,"No Data available",null);
		}
	   else {
			return new ResponseHandler(1,"All Data has been fetched" ,sales) ;
		}
	}
	
	@DeleteMapping("/delete/{salesId}")
	public ResponseHandler deleteSales(@PathVariable int salesId) {
		Sales sales=salesRepository.findBySalesId(salesId);
				if(sales!=null) {
				salesService.deleteSales(salesId);
			    return new ResponseHandler(1,"Data has been Deleted of id : " + salesId,true) ;
			}
				else {
				return new ResponseHandler(0,"No Data available on id : "+salesId,null);
			}	
    }
	
	@PutMapping("/update/{salesId}")  
	 public ResponseHandler updateData(@PathVariable int salesId,@Valid @RequestBody Sales sales) {
	  Sales salesSave= salesService.updateData(salesId, sales);
	  
	  if (salesSave!=null) {
	      return new ResponseHandler(1,"Data has been updated of id : " + salesId,salesSave) ;
		}
	    else {
			return new ResponseHandler(0,"Error ! Id is not updated of id : " + salesId,null);
       		}
	    }
     }
