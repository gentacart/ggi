package gentalib;


public class Product  {
    private String id;
    private String name;
    private String color;
    private String description;
    private String jualseri;
    private String serian;
    private String minorder;
    private Double price;
    private int totalstock;
    private double weight;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

   
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getTotalstock() {
        return totalstock;
    }

    public void setTotalstock(int totalstock) {
        this.totalstock = totalstock;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getJualseri() {
        return jualseri;
    }

    public void setJualseri(String jualseri) {
        this.jualseri = jualseri;
    }

    public String getSerian() {
        return serian;
    }

    public void setSerian(String serian) {
        this.serian = serian;
    }

    public String getMinorder() {
        return minorder;
    }

    public void setMinorder(String minorder) {
        this.minorder = minorder;
    }


}
