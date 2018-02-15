package com.github.bkenn.jpafx.data

import com.github.bkenn.jpafx.model.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional
interface CustomerRepository: JpaRepository<Customer, Long> {

    fun findByName(name: String): Customer?

    @Query("""
        select * from CUSTOMERS as c where c.id in
        (
            select customer_fk from VENDORS_CUSTOMERS
                where vendor_fk in
                (
                    select id from VENDORS as v where v.name = ?1
                )
        )
    """, nativeQuery = true)
    fun findAllCustomersByVendorsName(vendorsName: String): List<Customer>

}