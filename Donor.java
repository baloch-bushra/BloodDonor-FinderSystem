public class Donor extends Person {

    private String bloodGroup;
    private boolean available;

    public static int totalDonors = 0;

    public Donor(int id, String name, String city,
                 String bloodGroup, boolean available) {

        super(id, name, city);

        this.bloodGroup = bloodGroup;
        this.available = available;
        totalDonors++;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();

        System.out.println("Blood Group: " + bloodGroup);
        System.out.println("Available: " + available);
        System.out.println("------------------------");
    }
}