public class Main {

    public static void main(String[] args) {

        BloodBank bank = new BloodBank();

        Recipient r1 = new Recipient(
            1, "Bushra", "Kandiaro", "O+"
        );

        Recipient r2 = new Recipient(
            2, "Aqsa", "Khairpur", "B+"
        );

        bank.addRecipient(r1);
        bank.addRecipient(r2);

        bank.displayRecipients();

        System.out.println("Total Recipients: "
                + bank.getTotalRecipients());


        
        BloodBank bank = new BloodBank();

        Recipient r1 = new Recipient(
            1, "Sara", "Nawabshah", "O+"
        );

        Recipient r2 = new Recipient(
            2, "Ayesha", "Karachi", "B+"
        );

        bank.addRecipient(r1);
        bank.addRecipient(r2);

        bank.displayRecipients();

        System.out.println("Total Recipients: "
                + bank.getTotalRecipients());
    
    }
}
