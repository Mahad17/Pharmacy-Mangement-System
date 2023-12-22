package PharmacyMangementSystem.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import PharmacyMangementSystem.DTO.CustomerDto;
import PharmacyMangementSystem.DTO.ManufacturerDto;
import PharmacyMangementSystem.DTO.SalesDto;
import PharmacyMangementSystem.DTO.StockDto;
import PharmacyMangementSystem.DTO.VendorDto;
import PharmacyMangementSystem.Model.Customer;
import PharmacyMangementSystem.Model.Inventory;
import PharmacyMangementSystem.Model.Manufacturer;
import PharmacyMangementSystem.Model.Sales;
import PharmacyMangementSystem.Model.SalesForm;
import PharmacyMangementSystem.Model.Shop;
import PharmacyMangementSystem.Model.Stock;
import PharmacyMangementSystem.Model.Vendor;
//import PharmacyMangementSystem.PDF.CreatePdfFileService;
//import PharmacyMangementSystem.PDF.DatabasePDFService;
import PharmacyMangementSystem.Repositories.CustomerRepository;
import PharmacyMangementSystem.Repositories.InventoryRepository;
import PharmacyMangementSystem.Repositories.ManufacturerRepository;
import PharmacyMangementSystem.Repositories.SalesRepository;
import PharmacyMangementSystem.Repositories.ShopRepository;
import PharmacyMangementSystem.Repositories.StockRepository;
import PharmacyMangementSystem.Repositories.VendorRepository;
import PharmacyMangementSystem.Service.CustomerServiceImpl;
import PharmacyMangementSystem.Service.ManufacturerServiceImpl;
import PharmacyMangementSystem.Service.SalesServiceImpl;
import PharmacyMangementSystem.Service.ShopServiceImpl;
import PharmacyMangementSystem.Service.StockServiceImpl;
import PharmacyMangementSystem.Service.VendorServiceImpl;

@Controller
//@RequestMapping("/api")
public class IndexController {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	StockServiceImpl stockServiceImpl;

	@Autowired
	CustomerServiceImpl customerService;
	
	
	@Autowired
	ManufacturerRepository manufacturerRepository;
	
	@Autowired
	StockRepository stockRepository;
	
	@Autowired
	VendorServiceImpl vendorService;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	VendorServiceImpl vendorServiceImpl;
	
	@Autowired
	VendorRepository vendorRepository;
	
	@Autowired
	ManufacturerServiceImpl manufacturerService;	
	
	@Autowired
	SalesServiceImpl salesService;
	
	@Autowired
	SalesRepository salesRepository;
	
	@Autowired
	ShopRepository shopRepository;	
	
	@Autowired
	ShopServiceImpl shopService;
	
	@Autowired
	InventoryRepository inventoryRepository;


	
	@GetMapping("/")
	public String login() {
		return "logIn";	
		}
	
     @PostMapping("/login")
     public String login(@ModelAttribute Shop shop, Model model) {
    	 String email = shop.getEmail();
         String password = shop.getPassword();
         String phoneNumber=shop.getPhoneNumber();
         Boolean isAuthenticated = shopService.login(password,phoneNumber);
         Boolean shopFind= shopRepository.existsByPhoneNumber(phoneNumber);
          if(shopFind==Boolean.FALSE) {
        	model.addAttribute("errorMessage", "This phone number is not registered");        	
             }
        else if (isAuthenticated) {
        	 shopRepository.findByEmailOrPhoneNumber(email, phoneNumber);
          model.addAttribute("success", true);
         }
         
         else if(isAuthenticated!=null) {
             model.addAttribute("success", false);
             model.addAttribute("errorMessage", "Wrong password");
         }
         
         else {
             model.addAttribute("success", false);
             model.addAttribute("errorMessage", "Wrong password");
         }
         
         return "login";
     }
         
	@GetMapping("/signUp")
	public String singUp( @ModelAttribute Shop shop) {
		return "signUp";	
		}
	
    @PostMapping("/signUp")
    public String shopDetails(@ModelAttribute Shop shop, Model model,BindingResult bindingResult){
    	if(shopRepository.existsByEmail(shop.getEmail()) ) {
    		shop.setEmail(shop.getEmail());
            model.addAttribute("success", false);
    		model.addAttribute("errorMessage", " This Email is already registered");
		}
    	
		else if (shopRepository.existsByPhoneNumber(shop.getPhoneNumber())|| bindingResult.hasErrors())
		{
			shop.setPhoneNumber(shop.getPhoneNumber());
            model.addAttribute("success", false);
    		model.addAttribute("errorMessage", "Check your phone number(already registered or not in correct format)");

		}
			else{
				shopService.postDetails(shop);				
	             model.addAttribute("success", true);
	    		model.addAttribute("Successful", "Signed Up Succesfully! Now you can login");
	        	return "redirect:/";

		       }
    	return "signUp";
		}

	@GetMapping("/index")
	public String index(Model model) {
		List<Sales> sales=salesRepository.findAll();	
		 sales.sort(Comparator.comparing(Sales::getInvoiceDate).reversed());
		model.addAttribute("sales", sales);
		int totalPrice= salesService.getTotalSales();
		model.addAttribute("totalPrice", totalPrice);
		return "index";	
		}
	
	@GetMapping("/salesReport")
	public String salesReport(Model model) {
		Object sales=salesService.getAllSales().stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Sales::getInvoiceDate))), ArrayList::new));
		model.addAttribute("salesAll", salesService.getAllSales());
		model.addAttribute("sales", sales);
		model.addAttribute("salesGet", salesRepository.findAll());
		return "salesReport";	
		}

	@GetMapping("/getSalesByDate")
		public String getSalesByDate(@RequestParam("invoiceDate") @DateTimeFormat(pattern="yyyy-MM-dd") String invoiceDate,Model model) {
			List<Sales> sales= salesService.getSalesByDate(invoiceDate);
			
			model.addAttribute("salesAll", sales);
			model.addAttribute("salesByDate", salesService.getSalesByDate(invoiceDate).stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Sales::getInvoiceDate))), ArrayList::new)));
			return "salesReportByDate";
		}
	@GetMapping("/getSalesByInvoiceNumber")
	public String getSalesByInvoiceNumber(@RequestParam("invoiceNumber") int invoiceNumber,Model model) {
		Sales sales= salesService.getSalesByInvoiceNumber(invoiceNumber);
		
		model.addAttribute("salesAll", sales);
		return "salesReportByDate";
	}
	
	@GetMapping("/sales")
	public String sales(Model model) {
		List<Stock> stocks=stockRepository.findAll();
		model.addAttribute("stocks", stocks);
		return "sales";	
		}
	
	@GetMapping("/suggestions")
	public Object getSuggestions(@RequestParam("input") String input) {
	Object suggestions= stockRepository.findByItemNameIgnoreCase(input);
	return suggestions;
	}

	@GetMapping("/invoiceNumbersAll")
	public Object invoiceNumbersAll(@RequestParam("input") int input) {
	Object suggestions= salesRepository.findByInvoiceNumber(input);
	return suggestions;
	}
	
	@PostMapping("/allSalesPost")
	public String allSalesPost(@ModelAttribute SalesDto[] sales) {
		 salesService.postSalesAll(sales);
		return "redirect:/sales";		
	} 
	
	@GetMapping("/addSales")
	public String addSales(Model model) {
		return "addSales";	
		}
	
//	@RequestMapping("/postSales")
//	public ResponseEntity<InputStreamResource> postSales(@ModelAttribute SalesDto salesDto) {
//		ByteArrayInputStream bis = pdf.subtractStockQuantityWithoutStockId(salesDto);
//		if(bis!=null) {
//		    HttpHeaders headers = new HttpHeaders();
//		    headers.add("Content-Disposition", "inline; filename=Sales-Invoice.pdf");
//		    return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
//		          .body(new InputStreamResource(bis));	
//		}
//		else {
//			return null;
//		}
//		}

//	   @PostMapping("/salesPost")
//	   public String salesPost(@ModelAttribute SalesDto sales) {
//		 salesService.postSales(sales);
//		 return "addSales";
//     	}

	    @PostMapping("/salesPostAll")
        public String addUsers(@ModelAttribute SalesForm salesForm,Model model) {
        List<Sales> sales = salesForm.getSales();
        String customerNameSet="Walk-In-Customer";
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");     // Get the current date and time
        LocalDateTime currentDate = LocalDateTime.now();
        String formattedDateTime = currentDate.format(formatter);
        
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime currentTime = LocalTime.now();
        String formattedTime = currentTime.format(formatterTime);
  
           List<Sales> usersWithValues = sales.stream()
                
        		.map(sale -> {
                	
                	String presciptionGet=sale.getPrescription();
                	if (presciptionGet==null) {
                		sale.setPrescription("None");
						
					} else {
						sale.setPrescription(sale.getPrescription());
					}
                	
                	sale.setCustomerName(customerNameSet);
                	Stock stock=stockRepository.findByItemNameIgnoreCase(sale.getItemName());
        			model.addAttribute("itemPrice",stock.getUnitPrice());
        			int stockQuantity= stock.getQuantityAvailable();
        			int salesQuantity= sale.getQuantitySold();
        			int remainingQuantity=stockQuantity-salesQuantity;
        			stock.setQuantityAvailable(remainingQuantity);
        			stockRepository.save(stock);
                	 Random random = new Random();
                     int invoiceNumber = random.nextInt(90000000) + 10000000;
                     sale.setTime(formattedTime);
                	sale.setInvoiceNumber(invoiceNumber);
                    sale.setInvoiceDate(formattedDateTime);
                    return sale;
                })
                .collect(Collectors.toList());

        List<Sales> salesAll= salesRepository.saveAll(usersWithValues);
        if(salesAll!=null) {
    		model.addAttribute("successful", "New Sales has been added!");
            return "redirect:/sales";
    	}
    	else {
    		model.addAttribute("errorMessage", "Error Sales hasn't been added!");
            return "redirect:/sales";
    		}
        }
	    //For android(copy of upper one)
	    @PostMapping("/salesPost")
        public String addUsersAll(@ModelAttribute SalesForm salesForm,Model model) {
        List<Sales> sales = salesForm.getSales();
        String customerNameSet="Walk-In-Customer";
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");     // Get the current date and time
        LocalDateTime currentDate = LocalDateTime.now();
        String formattedDateTime = currentDate.format(formatter);
        
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime currentTime = LocalTime.now();
        String formattedTime = currentTime.format(formatterTime);
  
           List<Sales> usersWithValues = sales.stream()
                
        		.map(sale -> {
                	
                	String presciptionGet=sale.getPrescription();
                	if (presciptionGet==null) {
                		sale.setPrescription("None");
						
					} else {
						sale.setPrescription(sale.getPrescription());
					}
                	
                	sale.setCustomerName(customerNameSet);
                	Stock stock=stockRepository.findByItemNameIgnoreCase(sale.getItemName());
        			model.addAttribute("itemPrice",stock.getUnitPrice());
        			int stockQuantity= stock.getQuantityAvailable();
        			int salesQuantity= sale.getQuantitySold();
        			int remainingQuantity=stockQuantity-salesQuantity;
        			stock.setQuantityAvailable(remainingQuantity);
        			stockRepository.save(stock);
                	 Random random = new Random();
                     int invoiceNumber = random.nextInt(90000000) + 10000000;
                     sale.setTime(formattedTime);
                	sale.setInvoiceNumber(invoiceNumber);
                    sale.setInvoiceDate(formattedDateTime);
                    return sale;
                })
                .collect(Collectors.toList());

        List<Sales> salesAll= salesRepository.saveAll(usersWithValues);
        if(salesAll!=null) {
    		model.addAttribute("successful", "New Sales has been added!");
            return "Sale Sent";
    	}
    	else {
    		model.addAttribute("errorMessage", "Error Sales hasn't been added!");
            return "Sale Not Sent";
    		}
        }
    
	    @GetMapping("/getUnitPrice")
	    @ResponseBody
	    public ResponseEntity<Map<String, Object>> getUnitPrice(@RequestParam String itemName) {
	        Map<String, Object> response = new HashMap<>();
	        try {

	        	double unitPrice = salesService.getUnitPriceByItemName(itemName);

	            response.put("unitPrice", unitPrice);
	        
	            return ResponseEntity.ok(response);
	        } 
	        catch (Exception e) {
	            response.put("error", "Error fetching unit price");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }

	    	    
	    @GetMapping("/getSales/{salesId}")
	    public String getSales(@PathVariable int salesId, Model model) {
		model.addAttribute("sales", salesRepository.findBySalesId(salesId));
		List<Stock> stocks=stockRepository.findAll();
		model.addAttribute("stocks", stocks);
	    return "updateSales";
	    }
	
	    @RequestMapping("/updateSales/{salesId}")
	    public String updateSales(@PathVariable int salesId,@ModelAttribute SalesDto sales,Model model) {
	    salesService.update(salesId, sales);
	    return "redirect:/salesReport";
	    }

	    @GetMapping("/getSalesInDetails/{salesId}")
	    public String getSalesInDetails(@PathVariable int salesId,Sales sales, Model model) {
		model.addAttribute("sales", salesRepository.findBySalesId(sales.getSalesId()));
		List<Stock> stocks=stockRepository.findAll();
		model.addAttribute("stocks", stocks);
	    return "updateSalesDateTime";
	    }
	
	    @RequestMapping("/updateSalesInDetails/{salesId}")
	    public String updateDateTime(@PathVariable int salesId,@ModelAttribute SalesDto sales) {
		salesService.updateDateTime(salesId, sales);
		return "redirect:/salesReport";
	    }
	    
	
	    @RequestMapping("/deleteSales/{salesId}")
	    public String deleteSales(@PathVariable("salesId") int salesId) {
		salesRepository.deleteById(salesId);
		return "redirect:/salesReport";
		}
	
	    @GetMapping("/vendors")
	    public String vendor(Model model) {
		List<Vendor> vendors=vendorRepository.findAll();
		model.addAttribute("vendors", vendors);
		return "vendor";	
		}
	
	    @GetMapping("/addVendor")
	    public String addVendor(Model model) {
		List<Manufacturer> manufacturer=manufacturerRepository.findAll();
		model.addAttribute("manufacturer", manufacturer);
		return "addvendor";	
		}
	
	    @RequestMapping("/deleteVendor/{vendorId}")
	    public String deleteVendor(@PathVariable("vendorId") int vendorId) {
		vendorRepository.deleteById(vendorId);	
		return "redirect:/vendors";
	    }
	
	    @GetMapping("/getVendor/{vendorId}")
	    public String getVendor(@PathVariable int vendorId, Model model) {
	    Vendor vendor = vendorRepository.findByVendorId(vendorId);
	    model.addAttribute("vendors",vendor);
		List<Manufacturer> manufacturer=manufacturerRepository.findAll();
		model.addAttribute("manufacturer", manufacturer);	
	    return "updVendor";
	    }

	    @RequestMapping("/updateVendor/{vendorId}")
	    public String updateVendor(@PathVariable int vendorId,@ModelAttribute VendorDto vendor) {
		vendorService.update(vendorId, vendor);	
		return "redirect:/vendors";
	    }
	
	    @PostMapping("/postVendor")
	    public String postVendor(@ModelAttribute VendorDto vendorDto,Model model) {
	    Vendor vendor=vendorServiceImpl.postVendorDto(vendorDto);
	    if(vendor!=null) {
		model.addAttribute("successful", "New Vendor has been added!");
		return "addvendor";
	    }
        	else {
		           model.addAttribute("errorMessage", "Error Vendor hasn't been added!");
		           return "addvendor" ;
		         }
	    }
	
	       @GetMapping("/medicines")
	       public String medicines(Model model) {
		   List<Stock> stocks=stockRepository.findAll();
		   model.addAttribute("stocks", stocks);
		   return "medicinesall";	
		   }
	       
	       @GetMapping("/low-stock-Medicines")
	       public String findByLessQuantity(Model model) {
	    	 List<Stock> stock=  stockServiceImpl.findByQuantity();
	    	 model.addAttribute("stocks", stock);
	    	 return "selectedMedDet";
	       }
	
 	       @GetMapping("/showSelectedDetails")
           public String showSelectedDetails(@RequestParam("stockId") List<Integer> stockIds, Model model) {
           List<Stock> selectedMedicines = stockServiceImpl.getSelectedMedicines(stockIds);
           model.addAttribute("stocks", selectedMedicines);
           return "selectedMedDet"; // Return the name of the HTML page
           }
	
	        @GetMapping("/showSelectedDetailsByItem/{itemName}")
     	    public String showSelectedDetailsByItem(@PathVariable List<String> itemName, Model model) {
	        List<Stock> selectedMedicines = stockServiceImpl.getSelectedMedicinesByItemName(itemName);
	        model.addAttribute("stocks", selectedMedicines);
	        return "selectedMedDet"; // Return the name of the HTML page
	        }
	        
	        @GetMapping("/showSelectedDetailsByItem")
     	    public String showSelectedDetailsByItemName(@RequestParam List<String> itemName, Model model) {
	        List<Stock> selectedMedicines = stockServiceImpl.getSelectedMedicinesByItemName(itemName);
	        model.addAttribute("stocks", selectedMedicines);
	        return "selectedMedDet"; // Return the name of the HTML page
	        }
	
    	    @GetMapping("/addMedicines")
	        public String addMedicines(Model model) {
		    List<Inventory> inventory=inventoryRepository.findAll();
		    model.addAttribute("inventory", inventory);
		    return "addMedicines";	
		    }
	
	    @GetMapping("/getMedicine/{stockId}")
	    public String getMedicine(@PathVariable int stockId, Model model) {
	    Stock stock = stockRepository.findByStockId(stockId);
	    model.addAttribute("stock",stock);
		List<Inventory> inventory=inventoryRepository.findAll();
		model.addAttribute("inventory", inventory);	
	    return "updateMed";
	    }
	
	  @RequestMapping("/updateMedicine/{stockId}")
	  public String updateMed(@PathVariable int stockId,@ModelAttribute StockDto stock) {
		stockServiceImpl.updateData(stockId, stock);	
		return "redirect:/medicines";
	 }
	  
	  @GetMapping("/pdf-generate")
	    public ResponseEntity<byte[]> generatePdf() throws IOException {
	        List<Stock> stockList = stockRepository.findStocksWithLowQuantity();
	        byte[] pdfContent = createPdf(stockList);

	        return ResponseEntity.ok()
	            .contentType(MediaType.APPLICATION_PDF)
	            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=LowStockMedicines.pdf")
	            .body(pdfContent);
	    }

	    private byte[] createPdf(List<Stock> stockList) throws IOException {
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");    
	        LocalDateTime currentDate = LocalDateTime.now();
	        String formattedDateTime = currentDate.format(formatter);
	        PdfWriter writer = new PdfWriter(outputStream);
	        PdfDocument pdfDoc = new PdfDocument(writer);
	        Document document = new Document(pdfDoc);
	        document.setBackgroundColor(Color.DARK_GRAY);
	        
	        Image logo = new Image(ImageDataFactory.create("classpath:/static/assets/images/PHARMACYLOGO3.png"));
	        logo.setWidth(100);
	        logo.setMarginBottom(20);

	        Paragraph p1 = new Paragraph("Low Stock Medicines").setFontSize(15);
	        p1.setUnderline();
	        p1.setMarginBottom(30);
	        p1.setTextAlignment(TextAlignment.CENTER);
	        
	        Paragraph p2=new Paragraph("Date : " + formattedDateTime);
	        p2.setTextAlignment(TextAlignment.RIGHT);
	        p2.setMarginTop(30);
	        
	        float[] columnWidth = {200f, 200f, 200f, 200f, 200f};
	        Table table = new Table(columnWidth);
	        table.addCell(new Cell().add("Medicine Id").setBorder(new SolidBorder(Color.BLACK, 1)).setFontSize(10).setBold().setTextAlignment(TextAlignment.CENTER));
	        table.addCell(new Cell().add("Medicine Name").setBorder(new SolidBorder(Color.BLACK, 1)).setFontSize(10).setBold().setTextAlignment(TextAlignment.CENTER));
	        table.addCell(new Cell().add("Brand Name").setBorder(new SolidBorder(Color.BLACK, 1)).setFontSize(10).setBold().setTextAlignment(TextAlignment.CENTER));
	        table.addCell(new Cell().add("Stock Available").setBorder(new SolidBorder(Color.BLACK, 1)).setFontSize(10).setBold().setTextAlignment(TextAlignment.CENTER));
	        table.addCell(new Cell().add("Unit Price").setBorder(new SolidBorder(Color.BLACK, 1)).setFontSize(10).setBold().setTextAlignment(TextAlignment.CENTER));

	        for (Stock stock : stockList) {
	            Cell cell = new Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(8).add(String.valueOf(stock.getStockId()));
	            cell.setBorder(new SolidBorder(Color.BLACK, 1));
	            table.addCell(cell);

	            cell = new Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(8).add(stock.getItemName());
	            cell.setBorder(new SolidBorder(Color.BLACK, 1));
	            table.addCell(cell);

	            cell = new Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(8).add(stock.getBrandName());
	            cell.setBorder(new SolidBorder(Color.BLACK, 1));
	            table.addCell(cell);

	            cell = new Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(8).add(String.valueOf(stock.getQuantityAvailable()));
	            cell.setBorder(new SolidBorder(Color.BLACK, 1));
	            table.addCell(cell);

	            cell = new Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(8).add(String.valueOf(stock.getUnitPrice()));
	            cell.setBorder(new SolidBorder(Color.BLACK, 1));
	            table.addCell(cell);
	        }
	        
	        document.setBackgroundColor(Color.GRAY);
	        document.add(logo);
	        document.add(p1);
	        document.add(table);
	        document.add(p2);
	        document.close();

	        return outputStream.toByteArray();
	    }
	    
	  @RequestMapping("/delMedicine/{stockId}")
	  public String del(@PathVariable int stockId) {
		stockServiceImpl.deleteStock(stockId);
		return "redirect:/medicines";
	 }
	
	@GetMapping("/Med/{stockId}")
	public String detailsMed(@PathVariable("stockId") int stockId,Model model) {	
		model.addAttribute("medicineupdate", stockServiceImpl.getStock(stockId));
		return "medicinesDetails";	
		}

	@PostMapping("/postMedicines")
	public String postMedicines(@ModelAttribute StockDto stockDto,Model model) {
		if(stockDto!=null) {
			Stock stock= stockServiceImpl.postStock(stockDto);
			model.addAttribute("successful",stock.getItemName() +" has been added!");
			return "addMedicines";
		}
		model.addAttribute("errorMessage",	" Error! New Medicine not inserted!");
		return "addMedicines";
		}
	
	 @PostMapping("/update-medicines")
	    public String updateMedicines(@ModelAttribute("stocks") List<Stock> stocks) {
	        for (Stock stock : stocks) {
	            stockRepository.save(stock);
	            }
	        return "redirect:/medicines";
	    }
	 
	@PutMapping("/updateMed/{stockId}")
	public String updatd(@ModelAttribute StockDto stockDto,@PathVariable int stockId) {
		stockServiceImpl.updateData(stockId ,stockDto);
		return "redirect:/medicines";
				}
	
	//customer
	@GetMapping("/AllCustomer")
	public String allCustomer(Model model) {
		List<Customer> customers=customerRepository.findAll();
		model.addAttribute("customers", customers);
		return "customers";
	}
	
	@GetMapping("/AddCustomer")
	public String addCustomer() {
		return "addCustomer";
	}

	@GetMapping("/getCustomer/{customerId}")
	public String getCustomer(@PathVariable int customerId, Model model) {
	    Customer customer = customerRepository.findByCustomerId(customerId);
	    model.addAttribute("customer",customer);
	    		return "updcustomer";
	}

	@RequestMapping("/updateCustomer/{customerId}")
	public String updateCustomer(@PathVariable int customerId,@ModelAttribute Customer customer) {
		customerService.updateData(customerId, customer);
		return "redirect:/AllCustomer";
		
	}
	
	@PostMapping("/postCustomer")
	public String postCostumer(@ModelAttribute CustomerDto customerDto,Model model) {
		Customer customer=customerService.postCustomer(customerDto);
		if(customer!=null) {
			model.addAttribute("successful","New Customer has been added!");
			return "addCustomer";
		}
		model.addAttribute("errorMessage",	" Error! New Customer not inserted!");
		return "addCustomer";

		}
		
	   @RequestMapping("/deleteCustomer/{customerId}")
	   public String delCustomer(@PathVariable int customerId) {
		customerService.deleteCustomer(customerId);
		return "redirect:/AllCustomer";
		}
	
	 @GetMapping("/api/manufacturers")
	  public Manufacturer getManufacturerSuggestions(@RequestParam("manufacturerName") String manufacturerName) {
	    // Call the ManufacturerService method to retrieve manufacturer suggestions based on the user input
	    return manufacturerRepository.findByManufacturerNameIgnoreCase(manufacturerName); 
	    }
	
	@GetMapping("/sales/post")
	public String showForm(Model model) {
		SalesDto sales=new SalesDto();
		model.addAttribute("sales",sales);
		return "index";
	}

	@GetMapping("/manufacturers")
	public String manufacturers(Model model) {
		List<Manufacturer> manufacturer=manufacturerRepository.findAll();
		model.addAttribute("manufacturers", manufacturer);
		return "manufacturers";	
		}
	
	    @GetMapping("/addManufacturer")
	    public String addManufacturer(Model model) {
		return "addManufacturer";	
		}
	
	    @GetMapping("/getManufacturer/{manufacturerId}")
	    public String getManufacturer(@PathVariable int manufacturerId, Model model) {
	    Manufacturer manufacturer =manufacturerRepository.findByManufacturerId(manufacturerId);
	    model.addAttribute("manufacturers",manufacturer);
	    return "updateManufacturer";
	    }
	
	  @RequestMapping("/updateManufacturer/{manufacturerId}")
	  public String updateManufacturer(@PathVariable int manufacturerId,@ModelAttribute Manufacturer manufacturers) {
		manufacturerService.updateData(manufacturerId ,manufacturers);
		
		return "redirect:/manufacturers";
	 }

	  @RequestMapping("/delManufacturer/{manufacturerId}")
	  public String delManufacturer(@PathVariable int manufacturerId) {
		manufacturerService.deleteManufacturer(manufacturerId);
		return "redirect:/manufacturers";
	 }

	@PostMapping("/postManufacturer")
	public String postManufacturer(@ModelAttribute Manufacturer manufacturer,Model model) {
		Manufacturer manufacturerSave=manufacturerService.postManufacturer(manufacturer);
		if(manufacturerSave!=null) {
			model.addAttribute("successful","New Manufacturer has been added!");
			return "addManufacturer";
		}
		model.addAttribute("errorMessage", "New Manufacturer Not Added");
		return "addManufacturer";
	}
	
	}