import java.io.*;
import java.util.*;

public class HotelManagementSystem {

    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "hotel_data.txt";
    static class Room {
        int number;
        String type;
        int price;
        boolean booked;

        Room(int number, String type, int price) {
            this.number = number;
            this.type = type;
            this.price = price;
            this.booked = false;
        }
    }
    static ArrayList<Room> rooms = new ArrayList<>();
    static void loadRooms() {
        rooms.add(new Room(1, "Standard", 1200));
        rooms.add(new Room(2, "Standard", 1200));
        rooms.add(new Room(3, "Deluxe", 2200));
        rooms.add(new Room(4, "Deluxe", 2200));
        rooms.add(new Room(5, "Suite", 4000));
    }
    static void searchRoom() {
        System.out.print("Enter room type: ");
        String type = sc.nextLine();

        boolean found = false;
        for (Room r : rooms) {
            if (r.type.equalsIgnoreCase(type) && !r.booked) {
                System.out.println("Room " + r.number + " | ₹" + r.price);
                found = true;
            }
        }
        if (!found) System.out.println("No rooms available.");
    }
    static void bookRoom() {
        System.out.print("Customer name: ");
        String name = sc.nextLine();

        System.out.print("Room type: ");
        String type = sc.nextLine();

        for (Room r : rooms) {
            if (r.type.equalsIgnoreCase(type) && !r.booked) {
                r.booked = true;
                payment(r.price);
                saveBooking(name, r);
                System.out.println("Booking successful! Room No: " + r.number);
                return;
            }
        }
        System.out.println("Room not available.");
    }
    static void cancelBooking() {
        System.out.print("Enter room number: ");
        int num = sc.nextInt();
        sc.nextLine();

        for (Room r : rooms) {
            if (r.number == num && r.booked) {
                r.booked = false;
                System.out.println("Booking cancelled.");
                return;
            }
        }
        System.out.println("Invalid room number.");
    }
    static void payment(int amount) {
        System.out.println("Paying ₹" + amount + "...");
        System.out.println("Payment successful ✅");
    }
    static void saveBooking(String name, Room r) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(name + "," + r.number + "," + r.type + "," + r.price + "\n");
        } catch (IOException e) {
            System.out.println("File error!");
        }
    }
    static void viewBookings() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n--- BOOKINGS ---");
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                System.out.println("Name: " + d[0] +
                        " | Room: " + d[1] +
                        " | Type: " + d[2] +
                        " | Amount: ₹" + d[3]);
            }
        } catch (IOException e) {
            System.out.println("No booking data found.");
        }
    }
    public static void main(String[] args) {
        loadRooms();

        while (true) {
            System.out.println("\n==== HOTEL MANAGEMENT SYSTEM ====");
            System.out.println("1. Search Room");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> searchRoom();
                case 2 -> bookRoom();
                case 3 -> cancelBooking();
                case 4 -> viewBookings();
                case 5 -> {
                    System.out.println("Thank you 👋");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
