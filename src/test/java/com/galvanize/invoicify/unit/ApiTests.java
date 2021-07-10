
@WebMvcTest
public class APITests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    InvoiceService service;

    @Test
    void addItemtoInvoice() throws Exception{
        List<InvoiceItem>  invoiceItems= new ArrayList<InvoiceItem>();

        Item myitem=new Item("Dev Items", 2, new FlatFee(20.0));
        myitem.setTotalFee();
//        invoiceItems.add(myitem);
        Item myitemNext = new Item("Dev more Items", 5, new RateFee(10.0));
        Invoice invoice = new Invoice();
        //invoiceItems.set
//        invoiceItems.add(myitemNext);

//        invoiceItems.add(new InvoiceItem().setItem(new Item().setDescription()))

    }


        }