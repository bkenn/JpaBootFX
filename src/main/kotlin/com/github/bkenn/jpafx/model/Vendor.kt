package com.github.bkenn.jpafx.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javax.persistence.*
import tornadofx.*

@Entity(name = "vendors")
class Vendor(name: String? = null) {

    @get:Transient
    val idProperty = SimpleIntegerProperty(0)

    @get:Transient
    val nameProperty = SimpleStringProperty(name)

    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    var id by idProperty

    var name by nameProperty

    @get:Transient
    var customers: ObservableList<Customer> = FXCollections.observableArrayList()

    @OneToMany(cascade = [CascadeType.ALL])
    fun getCustomers(): List<Customer> = customers.toList()

    fun setCustomers(customers: List<Customer>) {
        this.customers.setAll(customers)
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vendor

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "Vendor(id=$id, name=$name, customers=${customers.joinToString(",", prefix = "[", postfix = "]")})"
    }

}
