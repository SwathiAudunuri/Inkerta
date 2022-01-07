package com.tecnics.einvoice.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.entity.RecipientMapping;
import com.tecnics.einvoice.entity.TodoTaskActivity;
import com.tecnics.einvoice.entity.VendorMapping;
import com.tecnics.einvoice.entity.VendorMappingActivity;
import com.tecnics.einvoice.model.CustomerInfoModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.service.VendorMappingService;
import com.tecnics.einvoicejson.model.VendorMappingSearchModel;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class VendorMappingController extends BaseController {

	private VendorMappingService vendorMappingService;
	@Autowired
	public VendorMappingController(VendorMappingService theVendorMappingService) {
		vendorMappingService = theVendorMappingService;
	}


	@GetMapping("/mappedVendors")
	public ResponseEntity<ResponseMessage> findByCustomerPartnerId(@RequestHeader("authorization") String token) {
		List<VendorMapping> response =  vendorMappingService.findByCustomerPartnerId(token);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	@GetMapping("/mappedVendors/{id}")
	public ResponseEntity<ResponseMessage> findById(@PathVariable Integer id) {
		Optional<VendorMapping> response = vendorMappingService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	@PutMapping("/inactiveVendor/{id}")
	public ResponseEntity<ResponseMessage> inActivate(@PathVariable Integer id) {
		Optional<VendorMapping> response = vendorMappingService.findById(id);
		response.get().setStatus(false);
		VendorMapping response2 = vendorMappingService.update(12, response.get());
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response2));
	}
	@PutMapping("/activeVendor/{id}")
	public ResponseEntity<ResponseMessage> activate(@PathVariable Integer id) {
		Optional<VendorMapping> response = vendorMappingService.findById(id);
		response.get().setStatus(true);
		VendorMapping response2 = vendorMappingService.update(12, response.get());
	return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response2));
	}

	@DeleteMapping("/vendorMapping/{id}")
	public void delete(@PathVariable Integer id) {
		vendorMappingService.delete(id);
	}
	@GetMapping("/myReciepeints")
	public ResponseEntity<ResponseMessage> findByVendorPartnerId(@RequestHeader("authorization") String token) {
		List<CustomerInfoModel> response=  vendorMappingService.findByVendorPartnerId(token);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	@GetMapping("/allVendors")
	public ResponseEntity<ResponseMessage> findAllVendors() {
		Map<Object, Object> response =  vendorMappingService.findAllVendors();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	
	
	@GetMapping("/allVendors/{companyName}")
	public ResponseEntity<ResponseMessage> findVendorsByName(@PathVariable String companyName) {
		Map<Object, Object> response =  vendorMappingService.findVendorsByName(companyName);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	
	@PostMapping("/newVendorMapping")
	public ResponseEntity<ResponseMessage> newVendorMapping(@RequestBody VendorMapping vendorMapping,@RequestHeader("authorization") String token) {
		TransactionResponse response=  vendorMappingService.save(vendorMapping, getUserName(token));
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	@PutMapping("/newVendorMapping/{id}")
	public ResponseEntity<ResponseMessage> save(@PathVariable int id,@RequestBody VendorMapping vendorMapping,@RequestHeader("authorization") String token) {
//		Optional<VendorMapping> response = vendorMappingService.findById(id);
		VendorMapping response=  vendorMappingService.update(id,vendorMapping);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	
//	@GetMapping("/vendorMapping/activites/{id}")
//	public List<VendorMappingActivity> findByUserId(@PathVariable int id ){
//		
//		return vendorMappingService.findByvendorMapping(id) ;
//	}
////
//	@PutMapping("/reciepientMapping/{id}")
//	public ResponseEntity<ResponseMessage> save(@PathVariable int id, @RequestBody RecipientMapping obj) {
//		RecipientMapping response = recipientMappingService.update(id, obj);
//		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
//	}
//	@PutMapping("/activeVendor/{id}")
//	public ResponseEntity<ResponseMessage> activate(@PathVariable Integer id) {
//		Optional<VendorMapping> response = vendorMappingService.findById(id);
//		response.get().setStatus(true);
//		VendorMapping response2 = vendorMappingService.update(12, response.get());
//	return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response2));
//	}
	
	
	
	
	
	@PostMapping("/vendorMapping/Search")
	public  List<VendorMapping> searchVendor(@RequestBody VendorMappingSearchModel vendorMapping) {
		return vendorMappingService.searchVendor(vendorMapping);
	}
//	 @RequestMapping("/Search")
//	    public String viewHomePage(VendorMapping vendorMapping, @Param("keyword") String keyword) {
//	        List<VendorMapping> listProducts = vendorMappingService.listAll(keyword);
//	        vendorMapping.addAttribute("listProducts", listProducts);
//	        vendorMapping.addAttribute("keyword", keyword);
	         
//	        return "index";
//	    }
}
