package com.github.bkenn.jpafx.data

import com.github.bkenn.jpafx.MyApp
import com.github.bkenn.jpafx.model.Person
import com.github.bkenn.jpafx.model.PersonRepository
import com.github.bkenn.jpafx.model.Phone
import com.github.bkenn.jpafx.model.PhoneRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [MyApp::class])
class PersonPhoneRepositoryTest {

    @Autowired lateinit var personRepository: PersonRepository
    @Autowired lateinit var phoneRepository: PhoneRepository


    @Test
    fun `save`() {
        val n1 = "555-555-5555"
        val n2 = "444-444-4444"
        val p = Person()
        val ph1 = Phone(n1)
        val ph2 = Phone(n2)
        p.addPhone(ph1)
        p.addPhone(ph2)
        val person = personRepository.save(p)

        assert(person.id != 0L, {"Did not save \n $person"})
        assert(person.phones.size == 2, {"Phone Size is not 1 \n $person"})

        val hasPhone1 = person.phones.any { it.number == n1 }
        val hasPhone2 = person.phones.any { it.number == n2 }

        assert(hasPhone1, {"Phone1 not found"})
        assert(hasPhone2, {"Phone2 not found"})
        println(person)
    }

    @Test
    fun `delete phone`() {
        val p = Person()
        val ph = Phone("215-990-0440")
        val ph1 = Phone("215-643-9198")
        p.addPhone(ph)
        p.addPhone(ph1)
        val person = personRepository.save(p)
        person.removePhone(person.phones[1])
        val person2 = personRepository.save(person)
        println(person2)
        assert(person2.phones.size == 1, {"Phone List not 1"})
    }

}