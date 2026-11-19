public class Recipient {

    private int id;
    private String name;
    private String city;
    private String bloodGroup;

    public Recipient(int id, String name, String city, String bloodGroup) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.bloodGroup = bloodGroup;
    }

    public void displayInfo() {
        System.out.println("Recipient ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("City: " + city);
        System.out.println("Required Blood Group: " + bloodGroup);
        System.out.println("------------------------");
    }
}