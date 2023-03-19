/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.billing.common;

import com.paymentchain.billing.dto.InvoiceResponse;
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
public interface InvoiceResposeMapper {
    
      @Mappings({
  @Mapping(source = "customerId", target = "customer"),
  @Mapping(source = "id", target = "invoiceId")})
  InvoiceResponse InvoiceToInvoiceRespose(Invoice source);  
  
  
  
  List<InvoiceResponse> InvoiceListToInvoiceResposeList(List<Invoice> source);    

/*  @InheritInverseConfiguration
  Invoice InvoiceResponseToInvoice(InvoiceResponse srr);
  
  @InheritInverseConfiguration
  List<Invoice> InvoiceResponseToInvoiceList(List<InvoiceResponse> source);  */  
    
}
