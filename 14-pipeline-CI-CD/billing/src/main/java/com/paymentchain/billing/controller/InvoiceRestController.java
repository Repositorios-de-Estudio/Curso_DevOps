/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.billing.controller;

import com.paymentchain.billing.common.InvoiceRequestMapper;
import com.paymentchain.billing.common.InvoiceResposeMapper;
import com.paymentchain.billing.dto.InvoiceRequest;
import com.paymentchain.billing.dto.InvoiceResponse;
import com.paymentchain.billing.entities.Invoice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.paymentchain.billing.respository.InvoiceRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;

/**
 *
 * @author sotobotero
 */
@Api(tags = "Billing API")
@RestController
@RequestMapping("/billing")
public class InvoiceRestController {
    
    @Autowired
    InvoiceRepository billingRepository;
    
    @Autowired
    InvoiceRequestMapper irm;
    
    @Autowired
    InvoiceResposeMapper irspm;
    
    @ApiOperation(value = "Return all transaction bundled into Response", notes = "Return 204 if no data found")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "There are not transactions"),
        @ApiResponse(code = 500, message = "Internal error")})
    @GetMapping()
    public List<InvoiceResponse> list() {
        List<Invoice> findAll = billingRepository.findAll();
        List<InvoiceResponse> InvoiceListToInvoiceResposeList = irspm.InvoiceListToInvoiceResposeList(findAll);
       return InvoiceListToInvoiceResposeList;
    } 
    @GetMapping("/{id}")
    public InvoiceResponse get(@PathVariable String id) {
        Optional<Invoice> findById = billingRepository.findById(Long.valueOf(id));
        Invoice get = findById.get();
        return irspm.InvoiceToInvoiceRespose(get);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody InvoiceRequest input) {
       Invoice save = null; 
        Optional<Invoice> findById = billingRepository.findById(Long.valueOf(id));
        Invoice get = findById.get();
        if(get != null){ 
                Invoice InvoiceRequestToInvoice = irm.InvoiceRequestToInvoice(input);
                    save = billingRepository.save(InvoiceRequestToInvoice);  
        }
        return ResponseEntity.ok(save);
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody InvoiceRequest input) {
        Invoice InvoiceRequestToInvoice = irm.InvoiceRequestToInvoice(input);
        Invoice save = billingRepository.save(InvoiceRequestToInvoice);  
        InvoiceResponse InvoiceToInvoiceRespose = irspm.InvoiceToInvoiceRespose(save);
        return ResponseEntity.ok(InvoiceToInvoiceRespose);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
          Invoice save = null; 
        Optional<Invoice> findById = billingRepository.findById(Long.valueOf(id));
        Invoice get = findById.get();
        if(get != null){               
                  billingRepository.delete(get);  
        }
        return ResponseEntity.ok().build();
    }
}
