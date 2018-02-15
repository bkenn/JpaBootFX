package com.github.bkenn.jpafx.data

import com.github.bkenn.jpafx.MyApp
import com.github.bkenn.jpafx.model.PersonFX
import com.github.bkenn.jpafx.model.PersonFXRepo
import com.github.bkenn.jpafx.model.PhoneFX
import com.github.bkenn.jpafx.model.PhoneFXRepo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [MyApp::class])
class PersonPhoneFXRepositoryTest {

    @Autowired lateinit var personFXRepo: PersonFXRepo
    @Autowired lateinit var phoneFXRepo: PhoneFXRepo

    val n1 = "555-555-5555"
    val n2 = "444-444-4444"

    fun withOnePersonFX(block: (PersonFX) -> Unit) {
        val p = PersonFX("Brian Kennedy")
        val ph1 = PhoneFX(n1)
        val ph2 = PhoneFX(n2)
        p.addPhone(ph1)
        p.addPhone(ph2)

        block(personFXRepo.save(p))

        phoneFXRepo.deleteAll()
        personFXRepo.deleteAll()
    }

    @Test
    fun `save`() = withOnePersonFX { person ->
        assert(person.id != 0L, {"Did not save \n $person"})
        assert(person.phones.size == 2, {"Phone Size is not 1 \n $person"})
        val hasPhone1 = person.phones.any { it.number == n1 }
        val hasPhone2 = person.phones.any { it.number == n2 }
        assert(hasPhone1, {"Phone1 not found"})
        assert(hasPhone2, {"Phone2 not found"})
        println(person)
    }

    @Test
    fun `delete phone`() = withOnePersonFX { person ->
        person.removePhone(person.phones[1])
        val person1 = personFXRepo.saveAndFlush(person)
        assert(person1.phones.size == 1, {"Phone List not 1"})
    }

    @Test
    fun `delete`() = withOnePersonFX { person ->
        val phone = person.phones[0]
        person.removePhone(phone)
        val savedPerson = personFXRepo.save(person)
        assert(!savedPerson.phones.contains(phone), {"Should not be found $phone \n IN \n $savedPerson"})
    }

    @Test
    fun `add a third phone number`() = withOnePersonFX { person ->
        val phone = PhoneFX("333-333-3333")
        person.addPhone(phone)
        val updated = personFXRepo.save(person)
        assert(updated.phones.size == 3, {"NOT \n$updated"})
        print(updated)
    }

    @Test
    fun `add a third phone number and then delete it after update`() = withOnePersonFX { person ->
        val phone = PhoneFX("333-333-3333")
        person.addPhone(phone)
        val updated = personFXRepo.save(person)
        assert(updated.phones.size == 3, {"NOT CORRECT SIZE OF 3 \n$updated"})
        updated.removePhone(updated.phones[2])
        val update2 = personFXRepo.save(updated)
        assert(update2.phones.size == 2, {"NOT CORRECT SIZE OF 2 \n$updated"})
    }

    @Test
    fun `update phone number`() = withOnePersonFX { person ->
        val phone = person.phones[0]
        val pn = "222-222-2222"
        phone.number =  pn
        val updated = personFXRepo.save(person)
        assert(updated.phones.size == 2, {"NOT \n$updated"})
        assert(updated.phones[0].number == pn, {"Phone Doesn't Equal: \n$updated"})
        assert(updated.phones[0] == phone, {"Doesn't equal"})
    }

}