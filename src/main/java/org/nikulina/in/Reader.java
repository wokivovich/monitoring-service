package org.nikulina.in;

import org.nikulina.model.MeterReading;
import org.nikulina.model.Role;
import org.nikulina.model.Session;
import org.nikulina.model.User;
import org.nikulina.out.View;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class Reader {

    private Session session;
    private View view;

    public Reader(Session session, View view) {
        this.session = session;
        this.view = view;
    }

    public void read() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (session.isActive()) {
                System.out.println("Enter command");
                String input = scanner.next();

                if (session.isUserIsAuthorized()) {
                    if (session.getUser().getRole() == Role.ADMIN) {
                        getAdminInput(scanner, input);
                    } else {
                        getUserInput(scanner, input);
                    }
                } else {
                    getUnauthorizedUserInput(scanner, input);
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Unknown command. Use '/help' to see available commands");
        }
    }

    private void getAdminInput(Scanner scanner, String input) {
        switch (input) {
            case "/help":
                view.showHelp();
                break;
            case "/user-history":
                showUserMeterReadings(scanner);
                break;
            case "/change-role":
                changeUserRole(scanner);
                break;
            case "/logout":
                logout();
                break;
            case "/exit":
                session.setActive(false);
                break;
            default:
                System.out.println("Unknown command. Use '/help' to see available commands");
        }
    }

    public void getUserInput(Scanner scanner, String input) {
        switch (input) {
            case "/help":
                view.showHelp();
                break;
            case "/logout":
                logout();
                break;
            case "/exit":
                session.setActive(false);
                break;
            case "/add-mr":
                addMeterReading(scanner);
                break;
            case "/actual-mr":
                view.showActualMeterReading();
                break;
            case "/mon-mr":
                showMeterReadingForMonth(scanner);
                break;
            case "/history":
                view.history();
                break;
            default:
                System.out.println("Unknown command. Use '/help' to see available commands");
        }
    }

    private void getUnauthorizedUserInput(Scanner scanner, String input) {
        switch (input) {
            case "/help":
                view.showHelp();
                break;
            case "/login":
                login(scanner);
                break;
            case "/register":
                register(scanner);
                break;
            case "/exit":
                session.setActive(false);
                break;
            default:
                System.out.println("Unknown command. Use '/help' to see available commands");
        }
    }

    private void changeUserRole(Scanner scanner) {
        System.out.println("Enter username");
        String username = scanner.next();
        if (session.getUsers().containsKey(username)) {
            System.out.println("What role you want set?");
            String role = scanner.next();
            switch (role.toLowerCase()) {
                case "admin":
                    session.getUsers().get(username).setRole(Role.ADMIN);
                    System.out.println("Role changed to admin");
                    break;
                case "user":
                    session.getUsers().get(username).setRole(Role.USER);
                    System.out.println("Role changed to user");
                    break;
                default:
                    System.out.println("Incorrect role name");
            }
        } else {
            System.out.println("User with this username doesn't exists");
        }
    }

    private void showMeterReadingForMonth(Scanner scanner) {
        System.out.println("Enter the month number");
        view.showMontMeterReading(scanner.nextInt());
    }

    private void showUserMeterReadings(Scanner scanner) {
        System.out.println("Enter username");
        String username = scanner.next();
        view.getMeterReadingByUsername(username);
    }

    private void addMeterReading(Scanner scanner) {
        LocalDate today = LocalDate.now();
        User user = session.getUser();

        if (session.getMeterReadings().stream()
                .filter(mr -> mr.getAuthor().equals(user))
                .map(mr -> mr.getAddingDate().getMonthValue())
                .anyMatch(month -> month.equals(today))) {
            System.out.println("In this month you have already add meter readings");
        } else {
            System.out.println("Enter hot water value");
            int hotWater = scanner.nextInt();
            System.out.println("Enter cold water value");
            int coldWater = scanner.nextInt();

            MeterReading meterReading = new MeterReading(
                    session.getUser(),
                    hotWater,
                    coldWater,
                    today
            );
            session.getMeterReadings().add(meterReading);
        }
    }

    private void register(Scanner scanner) {

        System.out.println("Enter your username");
        String username = scanner.next();
        if (session.getUsers().containsKey(username)) {
            System.out.println("User with this username already exists!");
        } else {
            System.out.println("Enter new password");
            String password = scanner.next();
            User user = new User(username, password, Role.USER, Set.of());
            session.getUsers().put(username, user);
            System.out.println("Registration success! Now, you can sign in entering '/login'");
        }
    }

    private void login(Scanner scanner) {
        System.out.println("Enter your username");
        String username = scanner.next();

        if (session.getUsers().containsKey(username)) {
            User user = session.getUsers().get(username);
            System.out.println("Enter your password");
            String password = scanner.next();

            if (user.getPassword().equals(password)) {
                session.setUserIsAuthorized(true);
                session.setUser(user);
            } else {
                System.out.println("Wrong password, try again");
            }

        } else {
            System.out.println("User with this username doesn't exists, try again or register");
        }
    }

    private void logout() {
        session.setUserIsAuthorized(false);
        session.setUser(null);
        System.out.println("You successfully logout");
    }
}
