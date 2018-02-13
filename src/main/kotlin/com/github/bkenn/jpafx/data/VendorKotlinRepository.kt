package com.github.bkenn.jpafx.data


import com.github.bkenn.jpafx.model.VendorKotlin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional
interface VendorRepository : JpaRepository<VendorKotlin, Int>
