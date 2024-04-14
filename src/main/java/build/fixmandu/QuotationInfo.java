package build.fixmandu;

public class QuotationInfo {
    private String serviceType;
    private String description;
    private String location;
    private boolean isUrgent;
    private double price;

    public QuotationInfo(String serviceType, String description, String location, boolean isUrgent, double price) {
        this.serviceType = serviceType;
        this.description = description;
        this.location = location;
        this.isUrgent = isUrgent;
        this.price = price;
    }

    // Getters and setters
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
