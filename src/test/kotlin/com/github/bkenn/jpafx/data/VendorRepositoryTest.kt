package com.github.bkenn.jpafx.data

import com.github.bkenn.jpafx.model.Customer
import com.github.bkenn.jpafx.model.Vendor
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class VendorRepositoryTest {

    @Autowired lateinit var customerRepository: CustomerRepository
    @Autowired lateinit var vendorRepository: VendorRepository

    val vendors = listOf("Google", "Jetbrains", "Oracle")

    private fun withVendors(block: () -> Unit) {
        val customers = listOf(
                listOf(Customer("Janet"), Customer("Steve")),
                listOf(Customer("Brian"), Customer("Derrick")),
                listOf(Customer("Jacob"), Customer("John"))
        )

        vendors.forEachIndexed { index, name ->
            val vendor = Vendor(name)
            vendor.customersProperty.setAll(customers[index])
            vendorRepository.save(vendor)
        }

        block()

        vendorRepository.deleteAll()
        customerRepository.deleteAll()
    }

    @Test
    fun `expect 3 vendors`() = withVendors {
        val expected = 3L
        val count = vendorRepository.count()
        assert(count == expected, {"Expected the count to be $expected but is actually $count"})
    }

    @Test
    fun `expect to find all vendors`() = withVendors {
        vendors.forEach { name ->
            vendorRepository.findByName(name) ?: error("Expected to find $name but is actually null")
        }
    }

    @Test
    fun `expect vendors has 2 customers`()  = withVendors {
        vendors.forEach { name ->
            val customerCount = 2
            val vendor = vendorRepository.findByName(name)
            assert(vendor?.customersProperty?.size == 2,
                    {"Expect $name to have $customerCount customer but actually had ${vendor?.customersProperty?.size}"})
        }
    }

    @Test
    fun `expect jetbrains to have customer Brian`() = withVendors {
        val vendorName = "Jetbrains"
        val customerName = "Brian"
        val vendors = vendorRepository.findByName(vendorName)
        vendors?.customersProperty?.find { it.name == customerName }
                ?: error("$vendorName should have a customer named $customerName")
    }
}