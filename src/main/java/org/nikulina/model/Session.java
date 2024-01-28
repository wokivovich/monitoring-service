package org.nikulina.model;

import java.util.HashMap;
import java.util.Set;

public class Session {

    private boolean isActive;
    private boolean userIsAuthorized;
    private User user;
    private Set<MeterReading> meterReadings;
    private HashMap<String, User> users;

    public Session(boolean isActive, boolean userIsAuthorized, User user, Set<MeterReading> meterReadings, HashMap<String, User> users) {
        this.isActive = isActive;
        this.userIsAuthorized = userIsAuthorized;
        this.user = user;
        this.meterReadings = meterReadings;
        this.users = users;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isUserIsAuthorized() {
        return userIsAuthorized;
    }

    public void setUserIsAuthorized(boolean userIsAuthorized) {
        this.userIsAuthorized = userIsAuthorized;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<MeterReading> getMeterReadings() {
        return meterReadings;
    }

    public void setMeterReadings(Set<MeterReading> meterReadings) {
        this.meterReadings = meterReadings;
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }
}
