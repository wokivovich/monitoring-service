package org.nikulina.model;

import java.time.LocalDate;

public class MeterReading {

    private User author;
    private Integer hotWater;
    private Integer coldWater;
    private LocalDate addingDate;

    public MeterReading(User author, Integer hotWater, Integer coldWater, LocalDate addingDate) {
        this.author = author;
        this.hotWater = hotWater;
        this.coldWater = coldWater;
        this.addingDate = addingDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Integer getHotWater() {
        return hotWater;
    }

    public void setHotWater(Integer hotWater) {
        this.hotWater = hotWater;
    }

    public Integer getColdWater() {
        return coldWater;
    }

    public void setColdWater(Integer coldWater) {
        this.coldWater = coldWater;
    }

    public LocalDate getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(LocalDate addingDate) {
        this.addingDate = addingDate;
    }
}
