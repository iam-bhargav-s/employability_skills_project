package model;

public class SparePart {
    private String partName;
    private int quantity;
    private double unitPrice;

    public SparePart(String partName, int quantity, double unitPrice) {
        this.partName = partName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getPartName() { return partName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }

    public double getTotalPrice() {
        return quantity * unitPrice;
    }

    @Override
    public String toString() {
        return partName + " | Qty: " + quantity + " | Unit Price: Rs."
                + unitPrice + " | Total: Rs." + getTotalPrice();
    }
}
