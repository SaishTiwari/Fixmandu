package build.fixmandu;

public class CustomerQuotation {
    private final int id;
    private final int customerId;
    private final String serviceType;
    private final String description;
    private final String location;
    private final boolean urgent;
    private final double price;
    private final String status;


    public CustomerQuotation(int id, int customerId, String serviceType, String description, String location, boolean urgent, double price, String status) {
        this.id = id;
        this.customerId = customerId;
        this.serviceType = serviceType;
        this.description = description;
        this.location = location;
        this.urgent = urgent;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "CustomerQuotation{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", serviceType='" + serviceType + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", urgent=" + urgent +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }

}





