import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BillingAPIService } from '../services/billing-module/api/billingAPI.service';
import { InvoiceRequest } from '../services/billing-module/model/invoiceRequest';
import { InvoiceResponse } from '../services/billing-module/model/invoiceResponse';
import { first } from 'rxjs/operators';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'billingApp';
  bills : InvoiceResponse[];
  billingForm: FormGroup; 
  constructor(
    private formBuilder: FormBuilder,
    private billingService : BillingAPIService){}  

  ngOnInit() {
    this.billingForm = this.formBuilder.group({
      number : new FormControl(''),
      customer : new FormControl(''),
      amount : new FormControl(''),
      detail : new FormControl('')
     });
    this.billingService.listUsingGET().subscribe(all => {
      console.log(all);
      this.bills = all;
    })
  }

   // convenience getter for easy access to form fields
   get f() { return this.billingForm.controls; }

  onSubmit() {
    let  invoiceRequest = {
      number : this.f.number.value,
      customer :   this.f.customer.value,
      amount :   this.f.amount.value,
      detail : this.f.detail.value      
    };   
    console.log('Your form data : ', invoiceRequest);
    this.billingService.postUsingPOST(invoiceRequest).subscribe(x => {
      console.log(x);
      this.ngOnInit();
    },
    error => {
      console.log(error.error.mensaje);
    });  
           
}

}
