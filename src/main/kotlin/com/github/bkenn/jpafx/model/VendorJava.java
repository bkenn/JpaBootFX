package com.github.bkenn.jpafx.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

@Entity
public class VendorJava {

    private final IntegerProperty idProperty = new SimpleIntegerProperty();

    private final StringProperty nameProperty = new SimpleStringProperty();

    public VendorJava() { }

    public VendorJava(String name) {
        setName(name);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return idProperty.get();
    }

    public void setId(Integer id) {
        idProperty.set(id);
    }

    public IntegerProperty idProperty() {
        return idProperty;
    }

    @Column
    public String getName() {
        return this.nameProperty.getValue();
    }

    public void setName(String name) {
        this.nameProperty.setValue(name);
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

}
