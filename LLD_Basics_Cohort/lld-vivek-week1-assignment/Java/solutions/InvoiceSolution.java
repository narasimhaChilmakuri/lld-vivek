package solutions;


import java.util.*;

class LineItem {
    String sku;
    int quantity;
    double unitPrice;

    LineItem(String sku, int quantity, double unitPrice) {
        this.sku = sku;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}

class Invoice{

    //fields
    private List<LineItem> items;
    private List<IDiscount> discounts;
    private String email;

    //constructors
    public Invoice(List<LineItem> items, List<IDiscount> discounts, String email){
        this.items = items;
        this.discounts = discounts;
        this.email = email;
    }

    public Invoice(){

    }

    //getters and setters
    public void setItems(List<LineItem> items){
        this.items = items;
    }

    public List<LineItem> getItems(){
        return items;
    }

    public void setDiscounts(List<IDiscount> discounts){
        this.discounts = discounts;
    }

    public List<IDiscount> getDiscounts(){
        return discounts;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }
}

interface IDiscount{
    double calculateDiscount(double subtotal);
}

interface IEmailSender{
    void sendInvoice(String email,String invoiceContent);
}

class SmtpEmailSender implements IEmailSender{

    @Override
    public void sendInvoice(String email, String invoiceContent) {
        if (email != null && !email.isEmpty()) {
            System.out.println("[SMTP] Sending invoice to " + email + "...");
        }
    }
}

class PercentageDiscount implements IDiscount{

    double percent;

    public PercentageDiscount(double percent){
        this.percent = percent;
    }

    @Override
    public double calculateDiscount(double subtotal) {
        return subtotal * (percent / 100.0);
    }
}

class FlatOffDiscount implements IDiscount{

    double discountValue;

    public FlatOffDiscount(double discountValue){
        this.discountValue = discountValue;
    }

    @Override
    public double calculateDiscount(double subtotal) {
        return discountValue;
    }
}


class DiscountService{

    double calculateDiscount(List<IDiscount> discounts,double subtotal){
        double totalDiscount = 0;

        for(IDiscount discount : discounts){
            totalDiscount+=discount.calculateDiscount(subtotal);
        }

        return  totalDiscount;
    }

}

class InvoiceService {

    DiscountService discountService = new DiscountService();
    IEmailSender emailSender = new SmtpEmailSender();

    String process(Invoice invoice) {
        // pricing
        double subtotal = calculateSubtotal(invoice);
        double discountTotal = discountService
                .calculateDiscount(invoice.getDiscounts(),subtotal);


        // tax inline
        double tax = (subtotal - discountTotal) * 0.18;
        double grand = subtotal - discountTotal + tax;

        // rendering inline (pretend PDF)
        StringBuilder pdf = new StringBuilder();
        pdf.append("INVOICE\n");
        for (LineItem it : invoice.getItems()) {
            pdf.append(it.sku).append(" x").append(it.quantity).append(" @ ").append(it.unitPrice).append("\n");
        }
        pdf.append("Subtotal: ").append(subtotal).append("\n")
                .append("Discounts: ").append(discountTotal).append("\n")
                .append("Tax: ").append(tax).append("\n")
                .append("Total: ").append(grand).append("\n");

        emailSender.sendInvoice(invoice.getEmail(),pdf.toString());


        // logging inline
        System.out.println("[LOG] Invoice processed for " + invoice.getEmail() + " total=" + grand);

        return pdf.toString();
    }


    private double calculateSubtotal(Invoice invoice){
        double subtotal = 0.0;
        for (LineItem it : invoice.getItems()) subtotal += it.unitPrice * it.quantity;
        return subtotal;
    }
}



public class InvoiceSolution {

    public static void main(String[] args) {
       InvoiceService invoiceService = new InvoiceService();
        List<LineItem> items = Arrays.asList(
                new LineItem("BOOK-001", 2, 500.0),
                new LineItem("USB-DRIVE", 1, 799.0)
        );
        List<IDiscount> discounts = new ArrayList<>();
        discounts.add(new PercentageDiscount(10));

        Invoice invoice = new Invoice(items,discounts,"customer@example.com");
        System.out.println(invoiceService.process(invoice));
    }


}
