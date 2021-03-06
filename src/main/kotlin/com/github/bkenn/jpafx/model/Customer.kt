package com.github.bkenn.jpafx.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.persistence.*

@Entity(name="customers")
class Customer(name: String? = null) {


    @get:Transient
    val idProperty = SimpleIntegerProperty(0)

    @get:Transient
    val nameProperty = SimpleStringProperty(name)

    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    var id by idProperty

    var name by nameProperty


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
        return "Customer(id=$id, name=$name)"
    }


}