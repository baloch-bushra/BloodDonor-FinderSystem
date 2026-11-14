import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

// Donor Class
class Donor implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private String phone;
    private String city;
    private String bloodGroup;

    public Donor(String name, int age, String phone,
                 String city, String bloodGroup) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.city = city;
        this.bloodGroup = bloodGroup;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getPhone() { return phone; }
    public String getCity() { return city; }
    public String getBloodGroup() { return bloodGroup; }
}

// Main Application Class
public class BloodDonorFinder extends JFrame {

    private ArrayList<Donor> donors = new ArrayList<>();

    private JTextField nameField, ageField, phoneField;
    private JTextField cityField, searchField;

    private JComboBox<String> bloodBox;

    private JTable table;
    private DefaultTableModel model;

    private final Color RED = new Color(190, 20, 45);
    private final Color DARK_RED = new Color(130, 10, 30);
    private final Color LIGHT_RED = new Color(255, 235, 238);

    private final String FILE = "donors.dat";

    public BloodDonorFinder() {

        setTitle("Blood Donor & Finder System");
        setSize(1050, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.PINK);

        loadData();

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(RED);
        header.setBorder(
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel title = new JLabel(
            "BLOOD DONOR & FINDER APP"
        );
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel subtitle = new JLabel(
            "Donate Blood | Save Lives"
        );
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));

        JPanel heading = new JPanel(new GridLayout(2, 1));
        heading.setBackground(RED);
        heading.add(title);
        heading.add(subtitle);

        header.add(heading, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Main Panel
        JPanel main = new JPanel(new BorderLayout(15, 10));
        main.setBackground(Color.WHITE);
        main.setBorder(
            BorderFactory.createEmptyBorder(10, 15, 15, 15)
        );

        // Donor Form
        JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));
        form.setBackground(LIGHT_RED);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(RED, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        form.setPreferredSize(new Dimension(280, 0));

        JLabel formTitle = new JLabel("ADD NEW DONOR");
        formTitle.setForeground(RED);
        formTitle.setFont(new Font("Arial", Font.BOLD, 19));
        form.add(formTitle);

        nameField = addField(form, "Donor Name:");
        ageField = addField(form, "Age:");
        phoneField = addField(form, "Phone Number:");
        cityField = addField(form, "City:");

        JLabel bloodLabel = new JLabel("Blood Group:");
        bloodLabel.setFont(new Font("Arial", Font.BOLD, 13));
        form.add(bloodLabel);

        String[] groups = {
            "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
        };

        bloodBox = new JComboBox<>(groups);
        form.add(bloodBox);

        JButton addButton = createButton("ADD DONOR");
        JButton clearButton = createButton("CLEAR FORM");

        form.add(addButton);
        form.add(clearButton);

        addButton.addActionListener(e -> addDonor());
        clearButton.addActionListener(e -> clearForm());

        // Right Panel
        JPanel right = new JPanel(new BorderLayout(10, 10));
        right.setBackground(Color.WHITE);

        JLabel tableTitle = new JLabel("REGISTERED BLOOD DONORS");
        tableTitle.setForeground(RED);
        tableTitle.setFont(new Font("Arial", Font.BOLD, 19));

        model = new DefaultTableModel(
            new String[]{
                "Name", "Age", "Phone",
                "City", "Blood Group"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setBackground(RED);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 13)
        );

        JScrollPane scroll = new JScrollPane(table);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);

        searchField = new JTextField(15);
        JButton searchButton = createButton("SEARCH");
        JButton showAllButton = createButton("SHOW ALL");
        JButton deleteButton = createButton("DELETE");

        searchPanel.add(new JLabel("Search by Blood Group / City:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);
        searchPanel.add(deleteButton);

        searchButton.addActionListener(e -> searchDonor());
        showAllButton.addActionListener(e -> refreshTable());
        deleteButton.addActionListener(e -> deleteDonor());

        JPanel topRight = new JPanel(new BorderLayout());
        topRight.setBackground(Color.WHITE);
        topRight.add(tableTitle, BorderLayout.NORTH);
        topRight.add(searchPanel, BorderLayout.SOUTH);

        right.add(topRight, BorderLayout.NORTH);
        right.add(scroll, BorderLayout.CENTER);

        main.add(form, BorderLayout.WEST);
        main.add(right, BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);

        refreshTable();
        setVisible(true);
    }

    // Text Field Method
    private JTextField addField(JPanel panel, String label) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(jLabel);

        JTextField field = new JTextField();
        panel.add(field);

        return field;
    }

    // Button Design Method
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(RED);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Add Donor Method
    private void addDonor() {

        try {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String phone = phoneField.getText().trim();
            String city = cityField.getText().trim();
            String blood = (String) bloodBox.getSelectedItem();

            if (name.isEmpty() || ageText.isEmpty()
                    || phone.isEmpty() || city.isEmpty()) {

                JOptionPane.showMessageDialog(
                    this, "Please fill all fields!"
                );
                return;
            }

            int age = Integer.parseInt(ageText);

            if (age < 18 || age > 100) {
                JOptionPane.showMessageDialog(
                    this, "Donor age must be between 18 and 100."
                );
                return;
            }

            if (!phone.matches("[0-9+ -]{7,20}")) {
                JOptionPane.showMessageDialog(
                    this, "Please enter a valid phone number."
                );
                return;
            }

            Donor donor = new Donor(
                name, age, phone, city, blood
            );

            donors.add(donor);
            saveData();
            refreshTable();
            clearForm();

            JOptionPane.showMessageDialog(
                this, "Donor Added Successfully!"
            );

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this, "Please enter a valid age."
            );
        }
    }

    // Search Donor Method
    private void searchDonor() {

        String query = searchField.getText()
                .trim().toLowerCase();

        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Enter blood group or city."
            );
            return;
        }

        model.setRowCount(0);

        for (Donor d : donors) {

            if (d.getBloodGroup().toLowerCase().equals(query)
                    || d.getCity().toLowerCase().contains(query)) {

                addRow(d);
            }
        }

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                this, "No donor found!"
            );
        }
    }

    // Display Donors
    private void refreshTable() {

        model.setRowCount(0);

        for (Donor d : donors) {
            addRow(d);
        }
    }

    private void addRow(Donor d) {
        model.addRow(new Object[]{
            d.getName(),
            d.getAge(),
            d.getPhone(),
            d.getCity(),
            d.getBloodGroup()
        });
    }

    // Delete Donor Method
    private void deleteDonor() {

        int selected = table.getSelectedRow();

        if (selected == -1) {
            JOptionPane.showMessageDialog(
                this, "Please select a donor first."
            );
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this donor?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            int modelRow = table.convertRowIndexToModel(selected);

            String name = model.getValueAt(modelRow, 0).toString();
            String phone = model.getValueAt(modelRow, 2).toString();

            for (int i = 0; i < donors.size(); i++) {
                Donor d = donors.get(i);

                if (d.getName().equals(name)
                        && d.getPhone().equals(phone)) {
                    donors.remove(i);
                    break;
                }
            }

            saveData();
            refreshTable();
        }
    }

    // Clear Form
    private void clearForm() {
        nameField.setText("");
        ageField.setText("");
        phoneField.setText("");
        cityField.setText("");
        bloodBox.setSelectedIndex(0);
    }

    // Save Data to File
    private void saveData() {

        try (ObjectOutputStream out =
                new ObjectOutputStream(
                    new FileOutputStream(FILE))) {

            out.writeObject(donors);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                this, "Error saving donor records!"
            );
        }
    }

    // Load Saved Data
    @SuppressWarnings("unchecked")
    private void loadData() {

        File file = new File(FILE);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream in =
                new ObjectInputStream(
                    new FileInputStream(FILE))) {

            donors = (ArrayList<Donor>) in.readObject();

        } catch (IOException | ClassNotFoundException ex) {
            donors = new ArrayList<>();
        }
    }

    // Main Method
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new BloodDonorFinder();
        });
    }
}