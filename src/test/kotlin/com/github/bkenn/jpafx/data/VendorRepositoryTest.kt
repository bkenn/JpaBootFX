package com.github.bkenn.jpafx.data

import com.github.bkenn.jpafx.model.Vendor
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class VendorRepositoryTest {

    @Autowired lateinit var vendorRepository: VendorRepository

    fun withVendors(block: () -> Unit) {
        vendorRepository.save(Vendor("Google"))
        vendorRepository.save(Vendor("Jetbrains"))
        vendorRepository.save(Vendor("Oracle"))

        block()

        vendorRepository.deleteAll()
    }

    @Test
    fun `expect 3 vendors`() = withVendors {
        val expected = 3L
        val count = vendorRepository.count()
        assert(count == expected, {"Expected the count to be $expected but is actually $count"})
    }

    @Test
    fun `expect to find all vendors`() = withVendors {
        listOf("Google", "Jetbrains", "Oracle").forEach { name ->
            vendorRepository.findByName(name) ?: error("Expected to find $name but is actually null")
        }
    }
}