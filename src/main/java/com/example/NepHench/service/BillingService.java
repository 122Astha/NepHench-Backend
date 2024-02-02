package com.example.NepHench.service;

import com.example.NepHench.model.Customer;
import com.example.NepHench.model.Invoice;
import com.example.NepHench.model.Nephenchservice;
import com.example.NepHench.repository.CustomerRepository;
import com.example.NepHench.repository.InvoiceRepository;
import com.example.NepHench.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BillingService {
    private final ServiceRepository serviceRepository;
    private final CustomerRepository customerRepository;
    private final InvoiceRepository invoiceRepository;

    public BillingService(ServiceRepository serviceRepository, CustomerRepository customerRepository, InvoiceRepository invoiceRepository) {
        this.serviceRepository = serviceRepository;
        this.customerRepository = customerRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice createInvoice(Integer customerId, List<Integer> serviceId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

         List<Nephenchservice> service = serviceRepository.findAllByIdIn(serviceId);

//        List<Integer> serviceId = services.getId();
        BigDecimal total = BigDecimal.ZERO;
//        total = services.getPrice();

        for (Nephenchservice services : service) {
            total = total.add(services.getPrice());
        }

        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setService(service.get(0));
        invoice.setTotalAmount(total);

        return invoiceRepository.save(invoice);

    }


}
