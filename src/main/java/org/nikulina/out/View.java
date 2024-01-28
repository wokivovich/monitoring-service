package org.nikulina.out;

import org.nikulina.model.MeterReading;
import org.nikulina.model.Role;
import org.nikulina.model.Session;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class View {

    private Session session;

    private final Map<String, String> unauthorizedUserCommands = new HashMap<>();
    private final Map<String, String> userCommands = new HashMap<>();
    private final Map<String, String> adminCommands = new HashMap<>();

    {
        unauthorizedUserCommands.put("/login", "Start authorization");
        unauthorizedUserCommands.put("/register", "Register");
        unauthorizedUserCommands.put("/exit", "End session");

        userCommands.put("/help", "Show all available commands");
        userCommands.put("/logout", "Logout from system");
        userCommands.put("/add-mr", "Add meter readings");
        userCommands.put("/actual-mr", "Show last entered meter readings");
        userCommands.put("/mon-mr", "Show meter readings by month added");
        userCommands.put("/history", "Show history of you meter reading adding");
        userCommands.put("/exit", "End session");

        adminCommands.put("/help", "Show all available commands");
        adminCommands.put("/user-history", "Show user meter reading history");
        adminCommands.put("/change-role", "Change user role");
    }

    public View(Session session) {
        this.session = session;
    }

    public void showHelp() {

        checkUserStatus().entrySet().forEach(c ->
                System.out.println(String.format("%s - %s", c.getKey(), c.getValue())));
    }

    public void showActualMeterReading() {
        MeterReading meterReading = session.getMeterReadings().stream()
                .filter(mr -> mr.getAuthor().equals(session.getUser()))
                .sorted(Comparator.comparing(MeterReading::getAddingDate))
                .findFirst()
                .orElse(null);

        if (meterReading == null) {
            System.out.println("You have never add meter readings");
        } else {
            System.out.println(String.format(
                    "Hot water - %d \nCold water - %d",
                    meterReading.getHotWater(),
                    meterReading.getColdWater()));
        }
    }

    public void showMontMeterReading(int month) {
        MeterReading meterReading = session.getMeterReadings().stream()
                .filter(mr -> mr.getAuthor().equals(session.getUser()))
                .filter(mr -> mr.getAddingDate().getMonthValue() == month)
                .findFirst()
                .orElse(null);

        if (meterReading == null) {
            System.out.println("You have never add meter readings for this month");
        } else {
            System.out.println(String.format(
                    "Hot water - %d \nCold water - %d",
                    meterReading.getHotWater(),
                    meterReading.getColdWater()));
        }
    }

    public void history() {
        List<LocalDate> addingDates = session.getMeterReadings()
                .stream()
                .filter(mr -> mr.getAuthor().equals(session.getUser()))
                .map(mr -> mr.getAddingDate())
                .collect(Collectors.toList());
        addingDates.sort(Comparator.comparing(LocalDate::atStartOfDay));
        addingDates.forEach(date -> System.out.println(date));
    }

    public void getMeterReadingByUsername(String username) {
        List<MeterReading> meterReadings = session.getMeterReadings()
                .stream()
                .filter(mr -> mr.getAuthor().getName().equals(username))
                .collect(Collectors.toList());

        meterReadings.forEach(mr -> System.out.println(String.format(
                "%s hot water - %s, cold water - %s",
                mr.getAddingDate(),
                mr.getHotWater(),
                mr.getColdWater()
        )));
    }

    private Map<String, String> checkUserStatus() {
        if (session.isUserIsAuthorized()) {
            if (session.getUser().getRole() == Role.ADMIN) {
                return adminCommands;
            } else {
                return userCommands;
            }
        } else {
            return unauthorizedUserCommands;
        }
    }

}
