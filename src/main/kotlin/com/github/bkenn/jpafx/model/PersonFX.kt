package com.github.bkenn.jpafx.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tornadofx.*
import javax.persistence.*
import javax.transaction.Transactional

@Entity(name = "personsfx")
class PersonFX(name: String = "") {

    @get: Transient
    val nameProperty = SimpleStringProperty(name)

    @get: Transient
    val idProperty = SimpleLongProperty()

    @get: Transient
    var phones: ObservableList<PhoneFX> = FXCollections.observableArrayList()

    @get: Column
    var name by nameProperty

    @get: Id
    @get: GeneratedValue
    var id by idProperty

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    fun getPhones(): List<PhoneFX> = phones

    fun addPhone(phonesFX: PhoneFX) {
        if(!phones.contains(phonesFX)) {
            phonesFX.personFX = this
            phones.add(phonesFX)
        }
    }


    fun setPhones(phonesFX: List<PhoneFX>) {
        try {
            phones.forEach(::addPhone)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun removePhone(phonesFX: PhoneFX) {
        if(phones.contains(phonesFX)) {
            phonesFX.personFX = null
            phones.remove(phonesFX)
        }
    }

    fun removePhones(phonesFX: List<PhoneFX>) {
        phonesFX.forEach(::removePhone)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersonFX

        if (name != other.name) return false
        if (id != other.id) return false
        if (phones.sumBy(PhoneFX::hashCode) != other.phones.sumBy(PhoneFX::hashCode)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + phones.hashCode()
        return result
    }


    override fun toString(): String {
        return "PersonFX(id=$id, name=$name, phones=${phones.joinToString(",","(", ")")})"
    }
}

@Entity(name = "PhoneFX")
class PhoneFX(number: String = "") {

    @get: Transient
    val personFXProperty = SimpleObjectProperty<PersonFX?>()

    @get: Transient
    val numberProperty = SimpleStringProperty(number)

    @get: Transient
    val idProperty = SimpleIntegerProperty()

    @get: ManyToOne
    var personFX by personFXProperty

    @get: Column(name = "phone_number")
    var number by numberProperty

    @get: Id
    @get: GeneratedValue
    var id by idProperty

    override fun toString(): String {
        return "Phone(id=$id, number=$number, personId=${personFX?.id})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PhoneFX

        if (personFX != other.personFX) return false
        if (number != other.number) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = number.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}


@Repository
@Transactional
interface PersonFXRepo: JpaRepository<PersonFX, Long>


@Repository
@Transactional
interface PhoneFXRepo: JpaRepository<PhoneFX, Long>
