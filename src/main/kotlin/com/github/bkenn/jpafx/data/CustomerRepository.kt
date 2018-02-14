package com.github.bkenn.jpafx.data

import com.github.bkenn.jpafx.model.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional
interface CustomerRepository: JpaRepository<Customer, Long> {

    fun findByName(name: String): Customer?

}