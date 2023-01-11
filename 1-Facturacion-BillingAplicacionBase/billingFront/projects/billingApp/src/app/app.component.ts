import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { BillingAPIService } from '../services/swaggerbillingAPI/api/billingAPI.service';
import { InvoiceResponse } from '../services/swaggerbillingAPI/model/invoiceResponse';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'billingApp';
  bills : InvoiceResponse[];
  billingForm: FormGroup;  
  constructor(
    private billingService : BillingAPIService,
    private formBuilder: FormBuilder,){}  


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
