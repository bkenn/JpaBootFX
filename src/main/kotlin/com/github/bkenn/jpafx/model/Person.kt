package com.github.bkenn.jpafx.model

import org.hibernate.annotations.NaturalId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.*
import javax.transaction.Transactional

@Entity(name = "persons")
data class Person(val name: String = "") {

    @Id
    @GeneratedValue
    var id: Long? = null

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    val phones: MutableList<Phone> = mutableListOf()

    fun addPhone(phone: Phone) {
        if(!phones.contains(phone)) {
            phone.person = this
            phones.add(phone)
        }
    }

    fun removePhone(phone: Phone) {
        if(phones.contains(phone)) {
            phone.person = null
            phones.remove(phone)
        }
    }

    override fun toString(): String {
        return "Person(id=$id, name=$name, phones=${phones.joinToString(",","(", ")")})"
    }
}

@Entity(name = "Phone")
data class Phone(
        @NaturalId
        @Column(name = "`number`", unique = true)
        val number: String = ""
) {

    @ManyToOne
    var person: Person? = null

    @Id
    @GeneratedValue
    var id: Long? = null

    override fun toString(): String {
        return "Phone(id=$id, number=$number, personId=${person?.id})"
    }
}

@Repository
@Transactional
interface PersonRepository: JpaRepository<Person, Long>

@Repository
@Transactional
interface PhoneRepository: JpaRepository<Phone, Long>