/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.billing.common;

import com.paymentchain.billing.dto.InvoiceRequest;
import com.paymentchain.billing.entities.Invoice;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *
 * @author sotobotero
 */
@Mapper(componentModel = "spring")
public interface InvoiceRequestMapper {
    
      @Mappings({
        @Mapping(source = "customer", target = "customerId")})
    Invoice InvoiceRequestToInvoice(InvoiceRequest source);
                
    List<Invoice> InvoiceRequestListToInvoiceList(List<InvoiceRequest> source);
    
 /*     @InheritInverseConfiguration
    InvoiceRequest InvoiceToInvoiceRequest(Invoice source);  
    
     @InheritInverseConfiguration
      List<InvoiceRequest> InvoiceListToInvoiceRequestList(List<Invoice> source);*/
    
    
}
