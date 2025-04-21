import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The PlaneManagement class represents a system for managing airplane seat reservations.
 * It provides functionality for buying and canceling seats, checking seat availability,
 * displaying seating plans, and managing ticket information.
 */
public class PlaneManagement {
    // Seat pricing configuration (compile-time constants)
    private static final int yellow_price = 200;  // Price for yellow zone seats (columns 1-5 in all rows)
    private static final int blue_price = 150;    // Price for blue zone seats (columns 6-9 in all rows)
    private static final int green_price = 180;    // Price for green zone seats (columns 10-14 in rows A-B, 10-13 in rows C-D)

    /**
     * Two-dimensional array representing the airplane's seating arrangement.
     * - First dimension (rows): 4 rows (A-D)
     * - Second dimension (columns): Varies by row (14 seats in A, 13 in B-D)
     * - 'O' represents available seat
     * - 'X' represents booked seat
     */
    private static final char[][] seats = {
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},  // Row A (14 seats)
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},        // Row B (13 seats)
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},        // Row C (13 seats)
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'}   // Row D (14 seats)
    };

    // Array to store all tickets sold during the current session
    private static Ticket[] ticketSold = new Ticket[0];

    /**
     * Handles the seat purchasing process.
     * - Prompts user for seat selection
     * - Validates input
     * - Checks seat availability
     * - Collects passenger information
     * - Creates and stores ticket
     */
    public static void buy_seat() {
        System.out.println("You have chosen the option to buy a seat");
        Scanner scanner = new Scanner(System.in);

        // Seat selection validation - Row
        char rowChar;
        do {
            try {
                System.out.print("Enter the row letter(A-D): ");
                rowChar = scanner.next().toUpperCase().charAt(0);

                if (rowChar < 'A' || rowChar > 'D') {
                    throw new InputMismatchException("Invalid row. Please enter a valid row (A-D).");
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
                rowChar = '\0'; // Reset for retry
            }
        } while (rowChar < 'A');

        // Seat selection validation - Column
        int column;
        do {
            try {
                System.out.print("Enter the seat number (1-14): ");
                column = scanner.nextInt();

                if (column < 1 || column > 14) {
                    throw new InputMismatchException("Invalid column. Please enter a valid column (1-14).");
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
                column = -1; // Reset for retry
            }
        } while (column < 1);

        int row = rowChar - 'A'; // Convert character to array index (A=0, B=1, etc.)

        try {
            // Check seat availability
            if (seats[row][column - 1] == 'X') {
                System.out.println("Seat " + rowChar + column + " is already sold.");
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid seat selection.");
            return;
        }

        // Process available seat
        if ('O' == seats[row][column - 1]) {
            System.out.println("Great!.. Seat " + rowChar + column + " is available.");
            scanner.nextLine(); // Clear buffer
            
            // Collect passenger information
            System.out.print("Enter your first name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your surname: ");
            String surname = scanner.nextLine();
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            // Create passenger and ticket
            Person person = new Person(name, surname, email);
            int price = calculatePrice(row, column - 1);
            Ticket ticket = new Ticket(row, column, price, person);
            
            // Complete booking
            addTicket(ticket);
            ticket.save();
            seats[row][column - 1] = 'X';
            System.out.println("Successfully Booked a seat " + rowChar + column);
        } else {
            System.out.println("Seat " + rowChar + column + " is already sold.");
        }
    }

    /**
     * Adds a new ticket to the sold tickets array.
     * @param ticket The Ticket object to be added to the collection
     */
    private static void addTicket(Ticket ticket) {
        Ticket[] newTicketsSold = new Ticket[ticketSold.length + 1];
        System.arraycopy(ticketSold, 0, newTicketsSold, 0, ticketSold.length);
        newTicketsSold[ticketSold.length] = ticket;
        ticketSold = newTicketsSold;
    }

    /**
     * Calculates the price for a seat based on its position.
     * @param row The row index (0-3)
     * @param column The column index (0-13)
     * @return The price for the seat
     */
    private static int calculatePrice(int row, int column) {
        char seatChar = (char) ('A' + row);
        if (column >= 0 && column < 5) { // Yellow seats (columns 1-5)
            return yellow_price;
        } else if (column >= 5 && column < 9) { // Blue seats (columns 6-9)
            return blue_price;
        } else if ((column >= 9 && column < 14) && (seatChar == 'A' || seatChar == 'B')) { // Green seats for rows A-B
            return green_price;
        } else if ((column >= 9 && column < 13) && (seatChar == 'C' || seatChar == 'D')) { // Green seats for rows C-D
            return green_price;
        }
        return 0; // Invalid seat position
    }

    /**
     * Handles seat cancellation process.
     * - Validates seat selection
     * - Confirms cancellation
     * - Updates seat availability
     * - Removes associated ticket
     */
    public static void cancel_seat() {
        System.out.println("You have chosen the option to cancel a seat");
        Scanner scanner = new Scanner(System.in);

        // Seat selection validation - Row
        char rowChar;
        do {
            try {
                System.out.print("Enter the row letter(A-D): ");
                rowChar = scanner.next().toUpperCase().charAt(0);

                if (rowChar < 'A' || rowChar > 'D') {
                    throw new InputMismatchException("Invalid row. Please enter a valid row (A-D).");
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
                rowChar = '\0';
            }
        } while (rowChar < 'A');

        // Seat selection validation - Column
        int column;
        do {
            try {
                System.out.print("Enter the seat number (1-14): ");
                column = scanner.nextInt();

                if (column < 1 || column > 14) {
                    throw new InputMismatchException("Invalid column. Please enter a valid column (1-14).");
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
                column = -1;
            }
        } while (column < 1);

        int row = rowChar - 'A';

        if (column <= seats[row].length) {
            if (!is_seat_available(row, column)) {
                seats[row][column - 1] = 'O'; // Mark as available
                Scanner m = new Scanner(System.in);
                System.out.println("Do you want to cancel your ticket? (Yes/No) ");
                String answer = m.next().toLowerCase();
                if (answer.equals("yes")) {
                    removeTicket(0); // Note: This currently always removes the first ticket - needs improvement
                    System.out.println("Seat " + rowChar + column + " canceled successfully.");
                }
            } else {
                System.out.println("Seat " + rowChar + column + " is not booked to cancel.");
            }
        } else {
            System.out.println("Invalid seat selection.");
        }
    }

    /**
     * Removes a ticket from the sold tickets array.
     * @param index The position of the ticket to be removed
     */
    private static void removeTicket(int index) {
        Ticket[] updatedTickets = new Ticket[ticketSold.length - 1];
        System.arraycopy(ticketSold, 0, updatedTickets, 0, index);
        System.arraycopy(ticketSold, index + 1, updatedTickets, index, ticketSold.length - index - 1);
        ticketSold = updatedTickets;
    }

    /**
     * Finds and displays the first available seat in the plane.
     * Searches row by row from front to back.
     */
    public static void find_first_available() {
        System.out.println("You have chosen the option to find first available seat");
        boolean found = false;
        
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 'O') {
                    System.out.println("First available seat found at row " + (char) ('A' + i) + ", column " + (j + 1));
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
        
        if (!found) {
            System.out.println("No available seats found.");
        }
    }

    /**
     * Displays the current seating plan of the airplane.
     * - Shows 'X' for booked seats
     * - Shows 'O' for available seats
     */
    public static void show_seating_plan() {
        System.out.println("You have chosen the option to show seating plan");
        System.out.println("\n Seating Plan:");
        
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j] == 'X' ? "X " : "O ");
            }
            System.out.println();
        }
    }

    /**
     * Prints information for all sold tickets and calculates total sales.
     * Displays:
     * - Seat number
     * - Passenger name
     * - Email
     * - Price
     * - Total revenue
     */
    public static void print_tickets_info() {
        System.out.println("You have chosen the option to print ticket information and total sales");
        int totalSales = 0;

        for (Ticket ticket : ticketSold) {
            System.out.println("Tickets Information: \n");
            System.out.println("Seat Number: " + (char) ('A' + ticket.getRow()) + ticket.getSeat());
            System.out.println("Passenger's Full Name: " + ticket.getPerson().getName() + " " + ticket.getPerson().getSurname());
            System.out.println("Email: "+ ticket.getPerson().getEmail());
            System.out.println("Price: £" + ticket.getPrice());
            totalSales += ticket.getPrice();
            System.out.println();
        }

        System.out.println("Total Amount: £" + totalSales);
    }

    /**
     * Searches for a specific ticket by seat number.
     * - Validates input
     * - Checks if seat is booked
     * - Displays ticket information if found
     */
    public static void search_ticket() {
        System.out.println("You have chosen the option to search for a ticket");
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the row letter(A-D): ");
        char rowChar = scanner.next().toUpperCase().charAt(0);
        if (rowChar < 'A' || rowChar > 'D') {
            System.out.println("Invalid row. Please enter a valid row (A-D).");
            return;
        }
        
        System.out.print("Enter the seat number (1-14): ");
        int column = scanner.nextInt();
        int row = rowChar - 'A';

        if (column >= 1 && column <= seats[row].length) {
            if (!is_seat_available(row, column)) {
                for (Ticket ticket : ticketSold) {
                    if (ticket.getRow() == row && ticket.getSeat() == column) {
                        System.out.println("Ticket Information:");
                        System.out.println("Seat: " + (char) ('A' + ticket.getRow()) + ticket.getSeat());
                        System.out.println("Passenger's Full Name: " + ticket.getPerson().getName() + " " + ticket.getPerson().getSurname());
                        System.out.println("Price: £" + ticket.getPrice());
                        return;
                    }
                }
            } else {
                System.out.println("This seat is available.");
            }
        } else {
            System.out.println("Invalid seat selection.");
        }
    }

    /**
     * Checks if a seat is available.
     * @param row The row index (0-3)
     * @param column The seat number (1-14)
     * @return true if seat is available, false if booked
     */
    public static boolean is_seat_available(int row, int column) {
        return seats[row][column - 1] == 'O';
    }

    /**
     * Initializes all seats to available status ('O').
     * Typically called at program startup.
     */
    public static void initializeSeats() {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = 'O';
            }
        }
    }

    /**
     * Displays the main menu of options to the user.
     */
    public static void displayMenu() {
        System.out.println("""
                *************************************************
                *                  MENU OPTIONS                 *
                *************************************************
                                    
                1) Buy a seat
                2) Cancel a seat
                3) Find first available seat
                4) Show seating plan
                5) Print ticket
                6) Search ticket
                0) Quit
                                    
                *************************************************
                """);
    }

    /**
     * Main program entry point.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n'Welcome to the Plane Management application'\n");
        initializeSeats();

        int option;
        do {
            displayMenu();
            System.out.print("\nPlease select an option: ");
            
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                option = -1;
                scanner.next();
            }

            switch (option) {
                case 1 -> buy_seat();
                case 2 -> cancel_seat();
                case 3 -> find_first_available();
                case 4 -> show_seating_plan();
                case 5 -> print_tickets_info();
                case 6 -> search_ticket();
                case 0 -> System.out.println("End");
                default -> System.out.println("Invalid option");
            }
        } while (option != 0);
    }
}