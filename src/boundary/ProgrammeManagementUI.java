/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import java.util.Scanner;
import entity.Programme;

/**
 *
 * @author Yu
 */
public class ProgrammeManagementUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        System.out.println("\nPROGRAMME MANAGEMENT MENU");
        System.out.println("1. Add a new programme");
        System.out.println("2. Remove a programme");
        System.out.println("3. Find programme");
        System.out.println("4. Ammend programme details");
        System.out.println("5. List all programmes");
        System.out.println("6. Add a tutorial group to a programme");
        System.out.println("7. Remove a tutorial group from a programme");
        System.out.println("8. List all tutorial groups for a programme");
        System.out.println("9. Generate reports");
        System.out.println("0. Quit");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public void listAllProgrammes(String outputStr) {
        System.out.println("\nList of Products:\n" + outputStr);
    }

    public void printProgrammeDetails(Programme programme) {
        System.out.println("Programme Details");
        System.out.println("Programme code:" + programme.getCode());
        System.out.println("Programme name: " + programme.getName());
        System.out.println("Quantity: " + product.getQuantity());
    }

    public String inputProductCode() {
        System.out.print("Enter product code: ");
        String code = scanner.nextLine();
        return code;
    }

    public String inputProductName() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        return name;
    }

    public int inputQuantity() {
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        return quantity;
    }

    public Product inputProductDetails() {
        String productCode = inputProductCode();
        String productName = inputProductName();
        int quantity = inputQuantity();
        System.out.println();
        return new Product(productCode, productName, quantity);
    }
}
