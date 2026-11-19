import java.util.ArrayList;

public class BloodBank {

    private ArrayList<Recipient> recipients = new ArrayList<>();

    public void addRecipient(Recipient recipient) {
        recipients.add(recipient);
        System.out.println("Recipient Added Successfully!");
    }

    public void displayRecipients() {
        System.out.println("\n--- Recipient Records ---");

        if (recipients.isEmpty()) {
            System.out.println("No Recipient Records Found!");
            return;
        }

        for (Recipient recipient : recipients) {
            recipient.displayInfo();
        }
    }

    public int getTotalRecipients() {
        return recipients.size();
    }
}