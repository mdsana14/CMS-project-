import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;  // Correct import for java.util.List

// Courier.java - Base class for all Courier types (Demonstrating inheritance and encapsulation)
abstract class Courier {
    private String senderName;
    private String receiverName;
    private String address;
    private double weight;
    private String status;
    private String courierId;
    
    public Courier(String senderName, String receiverName, String address, double weight) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.address = address;
        this.weight = weight;
        this.status = "In Transit"; // Default status
        this.courierId = UUID.randomUUID().toString(); // Unique ID for each courier
    }

    // Getter and Setter methods (Encapsulation)
    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getAddress() {
        return address;
    }

    public double getWeight() {
        return weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourierId() {
        return courierId;
    }
    
    // Abstract method to calculate cost (Polymorphism)
    public abstract double calculateCost();
}

// LocalCourier.java - Subclass of Courier
class LocalCourier extends Courier {
    private double localRate = 5.0;

    public LocalCourier(String senderName, String receiverName, String address, double weight) {
        super(senderName, receiverName, address, weight);
    }

    @Override
    public double calculateCost() {
        return getWeight() * localRate;
    }
}

// InternationalCourier.java - Subclass of Courier
class InternationalCourier extends Courier {
    private double internationalRate = 10.0;

    public InternationalCourier(String senderName, String receiverName, String address, double weight) {
        super(senderName, receiverName, address, weight);
    }

    @Override
    public double calculateCost() {
        return getWeight() * internationalRate;
    }
}

// Customer.java - Class representing a customer
class Customer {
    private String name;
    private String contact;
    
    public Customer(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }
}

// Staff.java - Class representing staff member
class Staff {
    private String staffName;
    private String role;
    
    public Staff(String staffName, String role) {
        this.staffName = staffName;
        this.role = role;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getRole() {
        return role;
    }
}

// DeliveryTask.java - Multithreading example (Manages Delivery tasks in separate threads)
class DeliveryTask extends Thread {
    private Courier courier;
    private java.util.List<Courier> courierList;  // Fully qualified List
    
    public DeliveryTask(Courier courier, java.util.List<Courier> courierList) {
        this.courier = courier;
        this.courierList = courierList;
    }

    @Override
    public void run() {
        try {
            System.out.println("Processing delivery for: " + courier.getSenderName());
            Thread.sleep(2000);
            courier.setStatus("Delivered");
            courierList.add(courier); // Add the courier to the list after delivery
            System.out.println("Delivery Completed for: " + courier.getSenderName());
            System.out.println("Delivery Cost: $" + courier.calculateCost());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// CourierManagementGUI.java - Using Swing to create GUI
public class CourierManagementGUI extends JFrame {
    private JTextField senderNameField, receiverNameField, addressField, weightField, trackIdField;
    private JTextField customerNameField, customerContactField, staffNameField, staffRoleField;
    private JButton submitButton, trackButton, customerListButton, courierListButton, staffListButton;
    private JButton addCustomerButton, addStaffButton, addCourierButton;
    private JTextArea resultArea;
    private java.util.List<Customer> customerList;  // Fully qualified List
    private java.util.List<Courier> courierList;  // Fully qualified List
    private java.util.List<Staff> staffList;  // Fully qualified List
    
    public CourierManagementGUI() {
        setTitle("Courier Management System");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Initialize Lists
        customerList = new ArrayList<>();
        courierList = new ArrayList<>();
        staffList = new ArrayList<>();
        
        // Create Components
        senderNameField = new JTextField(20);
        receiverNameField = new JTextField(20);
        addressField = new JTextField(20);
        weightField = new JTextField(10);
        trackIdField = new JTextField(20);
        
        customerNameField = new JTextField(20);
        customerContactField = new JTextField(20);
        
        staffNameField = new JTextField(20);
        staffRoleField = new JTextField(20);
        
        submitButton = new JButton("Submit Courier");
        trackButton = new JButton("Track Courier");
        customerListButton = new JButton("View Customer List");
        courierListButton = new JButton("View Courier List");
        staffListButton = new JButton("View Staff List");
        addCustomerButton = new JButton("Add Customer");
        addStaffButton = new JButton("Add Staff");
        addCourierButton = new JButton("Add Courier");
        
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        
        // Layout setup
        setLayout(new GridLayout(5, 1, 10, 10));  // 5 rows, 1 column layout for major sections
        
        // Courier Panel Setup with Background Color
        JPanel courierPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        courierPanel.setBackground(new Color(173, 216, 230)); // Light blue
        courierPanel.add(new JLabel("Sender Name:"));
        courierPanel.add(senderNameField);
        courierPanel.add(new JLabel("Receiver Name:"));
        courierPanel.add(receiverNameField);
        courierPanel.add(new JLabel("Address:"));
        courierPanel.add(addressField);
        courierPanel.add(new JLabel("Weight (kg):"));
        courierPanel.add(weightField);
        courierPanel.add(submitButton);
        
        // Track Panel Setup with Background Color
        JPanel trackPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        trackPanel.setBackground(new Color(255, 228, 225)); // Light pink
        trackPanel.add(new JLabel("Track Courier by ID:"));
        trackPanel.add(trackIdField);
        trackPanel.add(trackButton);
        
        // Customer Panel Setup with Background Color
        JPanel customerPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        customerPanel.setBackground(new Color(240, 248, 255)); // Alice blue
        customerPanel.add(new JLabel("Customer Name:"));
        customerPanel.add(customerNameField);
        customerPanel.add(new JLabel("Customer Contact:"));
        customerPanel.add(customerContactField);
        customerPanel.add(addCustomerButton);
        
        // Staff Panel Setup with Background Color
        JPanel staffPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        staffPanel.setBackground(new Color(250, 235, 215)); // Antique white
        staffPanel.add(new JLabel("Staff Name:"));
        staffPanel.add(staffNameField);
        staffPanel.add(new JLabel("Staff Role:"));
        staffPanel.add(staffRoleField);
        staffPanel.add(addStaffButton);
        
        // Buttons for Viewing Lists
        JPanel listButtonsPanel = new JPanel();
        listButtonsPanel.add(customerListButton);
        listButtonsPanel.add(courierListButton);
        listButtonsPanel.add(staffListButton);
        
        // Add the panels to the main frame
        add(courierPanel);
        add(trackPanel);
        add(customerPanel);
        add(staffPanel);
        add(listButtonsPanel);
        
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(new Color(255, 255, 240)); // Light yellow
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        add(resultPanel);
        
        // Button Click Event for Submit Courier
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String senderName = senderNameField.getText();
                String receiverName = receiverNameField.getText();
                String address = addressField.getText();
                double weight = Double.parseDouble(weightField.getText());
                
                // Create a Local Courier (you can extend it for International as well)
                Courier courier = new LocalCourier(senderName, receiverName, address, weight);
                
                // Start the delivery in a new thread
                DeliveryTask task = new DeliveryTask(courier, courierList);
                task.start();
                
                // Display result in the text area
                resultArea.append("Courier for " + senderName + " is being processed...\n");
            }
        });

        // Button Click Event for Track
        trackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String courierId = trackIdField.getText();
                Courier foundCourier = null;
                
                for (Courier courier : courierList) {
                    if (courier.getCourierId().equals(courierId)) {
                        foundCourier = courier;
                        break;
                    }
                }
                
                if (foundCourier != null) {
                    resultArea.append("Courier ID: " + foundCourier.getCourierId() + "\n");
                    resultArea.append("Status: " + foundCourier.getStatus() + "\n");
                    resultArea.append("Sender: " + foundCourier.getSenderName() + "\n");
                    resultArea.append("Receiver: " + foundCourier.getReceiverName() + "\n");
                } else {
                    resultArea.append("Courier not found.\n");
                }
            }
        });

        // Button Click Event for Add Customer
        addCustomerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = customerNameField.getText();
                String contact = customerContactField.getText();
                Customer customer = new Customer(name, contact);
                customerList.add(customer);
                resultArea.append("Customer added: " + name + "\n");
            }
        });

        // Button Click Event for Add Staff
        addStaffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = staffNameField.getText();
                String role = staffRoleField.getText();
                Staff staff = new Staff(name, role);
                staffList.add(staff);
                resultArea.append("Staff added: " + name + "\n");
            }
        });

        // Button Click Event for View Customer List
        customerListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultArea.setText("");
                if (customerList.isEmpty()) {
                    resultArea.append("No customers found.\n");
                } else {
                    for (Customer customer : customerList) {
                        resultArea.append("Customer Name: " + customer.getName() + ", Contact: " + customer.getContact() + "\n");
                    }
                }
            }
        });

        // Button Click Event for View Courier List
        courierListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultArea.setText("");
                if (courierList.isEmpty()) {
                    resultArea.append("No couriers found.\n");
                } else {
                    for (Courier courier : courierList) {
                        resultArea.append("Courier ID: " + courier.getCourierId() + ", Sender: " + courier.getSenderName() + ", Receiver: " + courier.getReceiverName() + ", Status: " + courier.getStatus() + "\n");
                    }
                }
            }
        });

        // Button Click Event for View Staff List
        staffListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultArea.setText("");
                if (staffList.isEmpty()) {
                    resultArea.append("No staff found.\n");
                } else {
                    for (Staff staff : staffList) {
                        resultArea.append("Staff Name: " + staff.getStaffName() + ", Role: " + staff.getRole() + "\n");
                    }
                }
            }
        });

        setVisible(true);
    }
    
    public static void main(String[] args) {
        new CourierManagementGUI();
    }
}
