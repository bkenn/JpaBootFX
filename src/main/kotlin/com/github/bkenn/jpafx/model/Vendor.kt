package com.github.bkenn.jpafx.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Vendor(name: String? = null) {

    val idProperty = SimpleIntegerProperty()
    val nameProperty = SimpleStringProperty(name)

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
        return "Vendor(id=$id, name=$name)"
    }

}

/*


import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javax.persistence.*

@Entity(name = "vendors")
class Vendor {

    @Transient
    val idProperty = SimpleIntegerProperty()

    @Transient
    val nameProperty = SimpleStringProperty()

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
        set(value) {
            idProperty.value = value
            field = value
        }
        get() {
            field = idProperty.value
            return field
        }

    @Column(nullable = true)
    var name: String? = null
        set(value) {
            nameProperty.value = value
            field = value
        }
        get() {
            field = nameProperty.value
            return field
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
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Vendor(id=$id, name=$name)"
    }

}
 */