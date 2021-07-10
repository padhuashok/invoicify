import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.invoicify.domain.*;
import com.galvanize.invoicify.service.InvoiceService;
import com.galvanize.invoicify.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest
public class APITests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    InvoiceService invoiceService;

    @MockBean
    ItemService itemservice;

    @Test
    void addItemtoInvoice() throws Exception{
        Invoice invoice= new Invoice();
        List<InvoiceItem> invoiceItems= new ArrayList<InvoiceItem>();

        Item myitem=new Item("Dev Items",  new FlatFee(20.0));
        //myitem.setTotalFee();
//        invoiceItems.add(myitem);
        Item myitemNext = new Item("Dev more Items",  new RateFee(2d,10));
        invoiceItems.add( new InvoiceItem(myitem, invoice));


        //call service to save invoice
        when(invoiceService.saveInvoice(invoice)).thenReturn(invoice);
        //call service to save item
        when(itemservice.saveInvoice(invoice)).thenReturn(invoice);

        //call service to save invoiceItem

        when(service.saveInvoice(invoice)).thenReturn(invoice);

        //create request to call api to create and check result





    }


        }