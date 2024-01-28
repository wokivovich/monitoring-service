package org.nikulina;

import org.nikulina.in.Reader;
import org.nikulina.model.MeterReading;
import org.nikulina.model.Role;
import org.nikulina.model.Session;
import org.nikulina.model.User;
import org.nikulina.out.View;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MonitoringServiceApplication {

    public static void main( String[] args ) {

        User admin = new User("admin", "admin", Role.ADMIN, Set.of());
        User user = new User("user", "user", Role.USER, Set.of());
        HashMap<String, User> users = new HashMap();
        Set<MeterReading> meterReadings = new HashSet<>();
        users.put("admin", admin);
        users.put("user", user);
        Session session = new Session(true, false, null, meterReadings, users);
        View view = new View(session);
        Reader reader = new Reader(session, view);
        reader.read();
    }
}
