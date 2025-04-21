/**
 * Represents a person with basic personal information including name, surname, and email.
 * This class provides methods to access and modify personal details, as well as display them.
 */
public class Person {
    // The first name of the person
    private String name;
    
    // The last name or family name of the person
    private String surname;
    
    // The email address of the person (should follow standard email format)
    private String email;

    /**
     * Constructs a new Person instance with specified details.
     * 
     * @param name    the first name of the person (cannot be null or empty)
     * @param surname the last name of the person (cannot be null or empty)
     * @param email   the email address of the person (should be valid format)
     */
    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    // ========== Getter and Setter Methods ========== //

    /**
     * Gets the first name of the person.
     * 
     * @return the current first name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets or updates the first name of the person.
     * 
     * @param name the new first name to set (cannot be null or empty)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the last name of the person.
     * 
     * @return the current last name
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets or updates the last name of the person.
     * 
     * @param surname the new last name to set (cannot be null or empty)
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the email address of the person.
     * 
     * @return the current email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets or updates the email address of the person.
     * 
     * @param email the new email address to set (should be valid format)
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Displays the person's complete information in a readable format.
     * Prints the name, surname, and email address to the standard output.
     */
    public void printInfo() {
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Email: " + email);
    }
}