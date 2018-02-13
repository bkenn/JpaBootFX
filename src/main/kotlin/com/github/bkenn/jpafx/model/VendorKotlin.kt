package com.github.bkenn.jpafx.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javax.persistence.*

//@Entity(name = "vendor_kotlin")
class VendorKotlin(name: String? = null) {

    @Transient
    val idProperty = SimpleIntegerProperty(0)

    @Transient
    val nameProperty = SimpleStringProperty(name)

    var id: Int
        set(value) { idProperty.value = value }

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        get() = idProperty.value

    var name: String?
        set(value) { nameProperty.value = value }

        @Column(nullable = true)
        get() = nameProperty.value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VendorKotlin

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "VendorKotlin(id=$id, name=$name)"
    }

}
