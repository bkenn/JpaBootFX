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
            val psVendor = Vendor(name)
            val psCustomers = customerRepository.saveAll(customers[index])
            psVendor.customers.setAll(psCustomers)
            vendorRepository.save(psVendor)
        }

        block()

        vendorRepository.deleteAll()
        customerRepository.deleteAll()
    }

    private fun withOneVendor(block: () -> Unit) {
        val psVendor = Vendor("Jetbrains")
        val psCustomers = listOf(Customer("Janet"), Customer("Steve"))

        val customers = customerRepository.saveAll(psCustomers)
        psVendor.setCustomers(customers)
        vendorRepository.saveAndFlush(psVendor)


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
            val vendor = vendorRepository.findByName(name)!!
            assert(vendor.customers.size == 2,
                    {"Expect $name to have $customerCount customer but actually had ${vendor.customers.size}"})
        }
    }

    @Test
    fun `expect jetbrains to have customer Brian`() = withVendors {
        val vendorName = "Jetbrains"
        val customerName = "Brian"
        val vendors = vendorRepository.findByName(vendorName)
        vendors?.customers?.find { it.name == customerName }
                ?: error("$vendorName should have a customer named $customerName")
    }

    @Test
    fun update() = withOneVendor {
        val jetbrains = vendorRepository.findByName("Jetbrains")!!
        val santa = customerRepository.save(Customer("Santa"))
        jetbrains.customers.add(santa)
        vendorRepository.save(jetbrains)

        val jb =  vendorRepository.findByName("Jetbrains")!!
        val doesSantaExist = jb.customers.find { it.name == "Santa" } != null
        assert(doesSantaExist, {"SANTA IS NOT FOUND"})
    }

    @Test
    fun `delete customer`() = withOneVendor {
        val vendorName = "Jetbrains"
        val customerNameTBD = "Steve"

        val jetbrains = vendorRepository.findByName(vendorName)!!
        jetbrains.customers.setAll(jetbrains.customers.filterNot { it.name == customerNameTBD })
        vendorRepository.save(jetbrains)

        val jb =  vendorRepository.findByName(vendorName)!!
        val doesSteveNotExist = jb.customers.find { it.name == customerNameTBD } == null
        assert(doesSteveNotExist, {"$customerNameTBD IS STILL A CUSTOMER OF $vendorName"})
    }

    @Test
    fun `update customer's name and check if changes`() = withOneVendor {
        val vendorName = "Jetbrains"
        val customerNameTBU = "Steve"
        val customersUpdatedName = "Joel"

        val steve = customerRepository.findByName(customerNameTBU)!!
        steve.name = customersUpdatedName
        customerRepository.save(steve)

        val jb =  vendorRepository.findByName(vendorName)!!

        val doesCustomerNotExist = jb.customers.find { it.name == customerNameTBU } == null
        val doesCustomerExist = jb.customers.find { it.name == customersUpdatedName } != null

        assert(doesCustomerNotExist, {"$customerNameTBU IS STILL A CUSTOMER OF $vendorName"})
        assert(doesCustomerExist, {"$customersUpdatedName IS NOT A CUSTOMER OF $vendorName"})
    }

    @Test
    fun `find all customers by vendor's name`() = withVendors {
        val customers = customerRepository.findAllCustomersByVendorsName("Jetbrains")
        assert(customers.size == 2, {"Customers Size: ${customers.size} \n $customers"})

    }
}