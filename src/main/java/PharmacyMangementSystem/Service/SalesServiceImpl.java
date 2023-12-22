package PharmacyMangementSystem.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PharmacyMangementSystem.DTO.SalesDto;
import PharmacyMangementSystem.Model.Sales;
import PharmacyMangementSystem.Model.Stock;
import PharmacyMangementSystem.Repositories.CustomerRepository;
import PharmacyMangementSystem.Repositories.InventoryRepository;
import PharmacyMangementSystem.Repositories.SalesRepository;
import PharmacyMangementSystem.Repositories.StockRepository;

@Service
public class SalesServiceImpl implements SalesService{
	
	@Autowired
	SalesRepository salesRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	StockRepository stockRepository;
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	public Sales postSalesData(Sales sales) {
		Stock stock=stockRepository.findByItemNameIgnoreCase(sales.getItemName());
		int stockQuantity= stock.getQuantityAvailable();
		int salesQuantity= sales.getQuantitySold();
		int remainingQuantity=stockQuantity-salesQuantity;
		stock.setQuantityAvailable(remainingQuantity);
		stockRepository.save(stock);
		Random random = new Random();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		// Get the current date
		LocalDate currentDate = LocalDate.now();
		// Format the date as a string
		String formattedDate = currentDate.format(formatter);

		int randomNumber = random.nextInt(90000000) + 10000000;
        sales.setInvoiceNumber(randomNumber);
        sales.setInvoiceDate(formattedDate);
        return salesRepository.save(sales);
	}
	
	public String postSalesAll(SalesDto[] sales) {

		for(SalesDto sale:sales) {
			String customerNameSet="Walk-In-Customer";
			Stock stock=stockRepository.findByItemNameIgnoreCase(sale.getItemName());
			int stockQuantity= stock.getQuantityAvailable();
			int salesQuantity= sale.getQuantitySold();
			int remainingQuantity=stockQuantity-salesQuantity;
			stock.setQuantityAvailable(remainingQuantity);
			stockRepository.save(stock);
		Random random = new Random();
        int randomNumber = random.nextInt(90000000) + 10000000;
        sale.setInvoiceNumber(randomNumber);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			// Get the current date
			LocalDate currentDate = LocalDate.now();
			// Format the date as a string
			String formattedDate = currentDate.format(formatter);
			sale.setCustomerName(customerNameSet);
			sale.setInvoiceDate(formattedDate);
        Sales salesMap= modelMapper.map(sale, Sales.class);
		 salesRepository.save(salesMap);
		 return "data saved";
		}
		return null;
		}
	
//	public ByteArrayInputStream subtractStockQuantityWithoutStockId(SalesDto salesDone) {
        
//   		Document document = new Document();   		
//   		ByteArrayOutputStream out = new ByteArrayOutputStream();
//    
//           try {
//    
//               PdfWriter.getInstance(document, out);
//               document.open();
//    
//               Font fontHeader = FontFactory.getFont(FontFactory.TIMES_BOLD, 22 ,Color.gray);
//               Paragraph para1 = new Paragraph("My Pharmacy", fontHeader);
//               para1.setAlignment(Element.ALIGN_CENTER);
//               document.add(para1);
//               document.add(Chunk.NEWLINE);
//                           
//               Font fontHeader2 = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
//               Paragraph para2 = new Paragraph("invoice # : "+ salesDone.getInvoiceNumber(), fontHeader2);
//               para2.setAlignment(Element.ALIGN_LEFT);
//               document.add(para2);
//               
//               Paragraph para3 = new Paragraph("Date : "+ salesDone.getInvoiceDate(), fontHeader2);
//               para3.setAlignment(Element.ALIGN_LEFT);
//               document.add(para3);
//               document.add(Chunk.NEWLINE);
//    
//               Font fontHeader3 = FontFactory.getFont(FontFactory.TIMES_BOLD, 13);
//               Paragraph para44 = new Paragraph("Item Name : "+ salesDone.getItemName(), fontHeader3);
//               para44.setAlignment(Element.ALIGN_CENTER);
//               document.add(Chunk.NEWLINE);
//               document.add(para44);
//               
//               Paragraph para5 = new Paragraph("Quantity : "+ salesDone.getQuantitySold(), fontHeader3);
//               para5.setAlignment(Element.ALIGN_CENTER);
//               document.add(para5);
//               Paragraph para7 = new Paragraph("Exp. Date : "+ inventory.getExpiryDate(), fontHeader3);
//               para7.setAlignment(Element.ALIGN_CENTER);
//               document.add(para7);         
//               Paragraph para6 = new Paragraph("Unit Price : "+ salesDone.getUnitPrice(), fontHeader3);
//               para6.setAlignment(Element.ALIGN_CENTER);
//               document.add(para6);                 
//               document.add(Chunk.NEWLINE);
//               
//               Paragraph paraTP = new Paragraph("Total Price : "+ salesDone.getTotalPrice(), fontHeader2);
//               paraTP.setAlignment(Element.ALIGN_RIGHT);
//               document.add(paraTP);
//               document.add(Chunk.NEWLINE);
//               document.close();
//    
//               Sales sales= modelMapper.map(salesDone, Sales.class);
//           	   int stockQuantity = stockFind.getQuantityAvailable() ;
//               int salesQuantity=sales.getQuantitySold();
//               if (salesQuantity>stockQuantity) {
//            	   return null;
//           			}
//               
//               else {
//            	   
//               int remainingQuantity=stockQuantity-salesQuantity;          
//               	   stockFind.setQuantityAvailable(remainingQuantity);
//                   stockRepository.save(stockFind);
//                   Sales salesSave= salesRepository.save(sales);
//                   modelMapper.map(salesSave, SalesDto.class);                   
//           }
//           
//           } 
//           catch (DocumentException e) {
//               e.printStackTrace();
//           }
//           return new ByteArrayInputStream(out.toByteArray());        	
//            }   
//        
//        else {
//        	return null;
//        }
//    }

	@Override
	public  Sales postSales( SalesDto sales) {
		Sales mapSales= modelMapper.map(sales, Sales.class);
		  return salesRepository.save(mapSales);
	}
		
	public Sales postSalesDto(SalesDto salesDto) {
	    Sales sales = modelMapper.map(salesDto, Sales.class);
	    return salesRepository.save(sales);
	}
	
	@Override
	public SalesDto getSales(int salesId) {
		
		Sales sales=salesRepository.findBySalesId(salesId);
		if (sales!=null) {
			SalesDto dto=this.modelMapper.map(sales, SalesDto.class);
			return dto;
		}
		
		else {
			return null;
		}
	}
	public List<Sales> getSalesByDate(String invoiceDate) {
	 List<Sales> sales=salesRepository.findAllByInvoiceDate(invoiceDate);
		return sales;
	}
	public Sales getSalesByInvoiceNumber(int invoiceNumber) {
		 Sales sales=salesRepository.findByInvoiceNumber(invoiceNumber);
			return sales;
		}
	public Sales getSale(int salesId){
		return salesRepository.findBySalesId(salesId);
		}
 
	 @Override
	 public List<Sales> getAllSales(){
	 return salesRepository.findAll();
	 }
	
	 @Override
	 public Boolean deleteSales(int salesId) {
		Sales sales= salesRepository.findBySalesId(salesId);
		salesRepository.delete(sales);
		return true;
	  }
	 public int getTotalSales() {
	        return salesRepository.sumTotalPrice();
	    }
	 
	 public Double getUnitPriceByItemName(String itemName) throws Exception {
	        // Assuming StockRepository has a method to find by item name
	        Stock stock = stockRepository.findByItemNameIgnoreCase(itemName);

	        if (stock != null) {
	            return (double) stock.getUnitPrice();
	        } else {
	            // Handle the case when the item is not found
	            throw new Exception("Item not found: " + itemName);
	        }
	    }
	 
	 public Sales update( int salesId, SalesDto salesDto) {
		   Optional<Sales> sales  = salesRepository.findById(salesId);
		   Sales salesMap= modelMapper.map(sales, Sales.class);
		   if (sales.isPresent()) {
		    	Sales salesGet = sales.get();
		    	salesGet.setCustomerName(salesDto.getCustomerName());
		    	
		    	salesGet.setInvoiceDate(salesMap.getInvoiceDate());
		    	salesGet.setTime(salesMap.getTime());
		    	salesGet.setInvoiceNumber(salesDto.getInvoiceNumber());
		    	salesGet.setItemName(salesDto.getItemName());
		    	salesGet.setPaymentMethod(salesDto.getPaymentMethod());
		    	salesGet.setPrescription(salesDto.getPrescription());
		    	salesGet.setQuantitySold(salesDto.getQuantitySold());
		    	salesGet.setTotalPrice(salesDto.getTotalPrice());
		    	salesGet.setUnitPrice(salesDto.getUnitPrice());;
		    	return salesRepository.save(salesGet);
		    }
		    else {
				return null;
				}
		    }	

	 public Sales updateDateTime( int salesId, SalesDto salesDto) {
		   Optional<Sales> sales  = salesRepository.findById(salesId);
		   
		    if (sales.isPresent()) {
		    	Sales salesGet = sales.get();
		    	salesGet.setCustomerName(salesDto.getCustomerName());
		    	salesGet.setInvoiceDate(salesDto.getInvoiceDate());
		    	salesGet.setTime(salesDto.getTime());
		    	salesGet.setInvoiceNumber(salesDto.getInvoiceNumber());
		    	salesGet.setItemName(salesDto.getItemName());
		    	salesGet.setPaymentMethod(salesDto.getPaymentMethod());
		    	salesGet.setPrescription(salesDto.getPrescription());
		    	salesGet.setQuantitySold(salesDto.getQuantitySold());
		    	salesGet.setTotalPrice(salesDto.getTotalPrice());
		    	salesGet.setUnitPrice(salesDto.getUnitPrice());;
		    	return salesRepository.save(salesGet);
		    }
		    else {
				return null;
				}
		    }	

	
	  @Override
	  public Sales updateData( int salesId, Sales sales) {
	  Optional<Sales> salesFind = salesRepository.findById(salesId);
		   
		    if (salesFind.isPresent()) {
		    	Sales salesGet = salesFind.get();
		    	salesGet.setCustomerName(sales.getCustomerName());
		    	salesGet.setInvoiceDate(sales.getInvoiceDate());
		    	salesGet.setInvoiceNumber(sales.getInvoiceNumber());
		    	salesGet.setItemName(sales.getItemName());
		    	salesGet.setPaymentMethod(sales.getPaymentMethod());
		    	salesGet.setPrescription(sales.getPrescription());
		    	salesGet.setQuantitySold(sales.getQuantitySold());
		    	salesGet.setTotalPrice(sales.getTotalPrice());
		    	salesGet.setUnitPrice(sales.getUnitPrice());   
		        return salesRepository.save(salesGet);
		    }		    
		    else {
				return null;
				}
		    }
	  
}