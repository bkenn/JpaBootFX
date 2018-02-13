package com.github.bkenn.jpafx.data

import com.github.bkenn.jpafx.model.Vendor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface VendorRepository : JpaRepository<Vendor, Int>