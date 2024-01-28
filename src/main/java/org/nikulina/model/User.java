package org.nikulina.model;

import java.util.Objects;
import java.util.Set;

public class User {

    private String name;
    private String password;
    private Role role;
    private Set<MeterReading> meterReading;

    public User(String name, String password, Role role, Set<MeterReading> meterReading) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.meterReading = meterReading;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<MeterReading> getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(Set<MeterReading> meterReading) {
        this.meterReading = meterReading;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(password, user.password) && role == user.role && Objects.equals(meterReading, user.meterReading);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, role, meterReading);
    }
}
