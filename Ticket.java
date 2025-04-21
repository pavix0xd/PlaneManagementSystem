import java.io.FileWriter;
import java.io.IOException;

/**
 * The Ticket class represents an airplane ticket with associated seat information,
 * pricing, and passenger details. It provides functionality to save ticket information
 * to files and display ticket details.
 */
public class Ticket {
    // The row number of the seat (0-based index corresponding to A=0, B=1, etc.)
    private int row;
    
    // The seat number within the row (1-based numbering as displayed to users)
    private int seat;
    
    // The price of the ticket in pounds sterling
    private int prices;
    
    // The Person object containing passenger information (name, surname, email)
    private Person person;

    /**
     * Constructs a new Ticket object with the specified details.
     *
     * @param row     The row index of the seat (0-based)
     * @param seat    The seat number within the row (1-based)
     * @param prices  The price of the ticket in £
     * @param person  The Person object containing passenger information
     */
    public Ticket(int row, int seat, int prices, Person person) {
        this.row = row;
        this.seat = seat;
        this.prices = prices;
        this.person = person;
    }

    /**
     * Saves the ticket information to a text file named after the seat (e.g., "A1.txt").
     * The file contains:
     * - Seat identifier
     * - Passenger full name
     * - Passenger email
     * - Ticket price
     * 
     * The file is created in the program's working directory.
     * Uses FileWriter with try-with-resources for automatic resource management.
     */
    public void save() {
        // Generate filename based on seat location (e.g., "A1.txt")
        String filename = (char) ('A' + row) + Integer.toString(seat) + ".txt";

        try (FileWriter writer = new FileWriter(filename)) {
            // Write formatted ticket information to the file
            writer.write("Ticket Information:\n");
            writer.write("Seat: " + (char) ('A' + row) + seat + "\n");
            writer.write("Passenger's Full Name: " + person.getName() + " " + person.getSurname() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");
            writer.write("Price: £" + prices + "\n");
            
            System.out.println("Ticket saved to file: " + filename);
        } catch (IOException e) {
            // Handle file I/O errors
            System.out.println("An error occurred while saving the ticket to file: " + filename);
            e.printStackTrace(); // Print stack trace for debugging purposes
        }
    }

    // ========== ACCESSOR METHODS (Getters and Setters) ========== //

    /**
     * Gets the row index of the seat (0-based).
     * @return The row index (0=A, 1=B, etc.)
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row index of the seat.
     * @param row The new row index (0-based)
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Gets the seat number within the row (1-based).
     * @return The seat number
     */
    public int getSeat() {
        return seat;
    }

    /**
     * Sets the seat number within the row.
     * @param seat The new seat number (1-based)
     */
    public void setSeat(int seat) {
        this.seat = seat;
    }

    /**
     * Gets the ticket price in pounds sterling.
     * @return The ticket price in £
     */
    public int getPrice() {
        return prices;
    }

    /**
     * Sets the ticket price.
     * @param price The new price in £
     */
    public void setPrice(int price) {
        this.prices = price;
    }

    /**
     * Gets the Person object associated with this ticket.
     * @return The Person object containing passenger details
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the Person object for this ticket.
     * @param person The new Person object containing passenger details
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Prints the complete ticket information to standard output, including:
     * - Seat location
     * - Ticket price
     * - Passenger details (name, surname, email)
     */
    public void printTicketInfo() {
        System.out.println("Ticket Information:");
        System.out.println("Row: " + (char)('A' + row)); // Convert to letter for display
        System.out.println("Seat: " + seat);
        System.out.println("Price: £" + prices);
        System.out.println("Person Information:");
        person.printInfo(); // Delegate to Person's print method
    }
}