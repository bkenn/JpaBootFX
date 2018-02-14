package com.github.bkenn.jpafx.view

import com.github.bkenn.jpafx.Styles
import com.github.bkenn.jpafx.data.CustomerRepository
import com.github.bkenn.jpafx.data.VendorRepository
import com.github.bkenn.jpafx.model.Customer
import com.github.bkenn.jpafx.model.Vendor
import javafx.collections.FXCollections
import javafx.geometry.Pos
import tornadofx.*

class MainView : View("Hello TornadoFX") {

    val customerRepository: CustomerRepository by di()
    val vendorRepository: VendorRepository by di()
    val vendors = FXCollections.observableArrayList<Vendor>()

    init {
        val psCustomers = customerRepository.saveAll(listOf(Customer("Janet"), Customer("Steve")))
        val jetbrains = Vendor("Jetbrains")
        jetbrains.customers.addAll(psCustomers)
        vendorRepository.save(jetbrains)
        vendors.addAll(vendorRepository.findAll())
    }

    override val root = vbox {
        style {
            alignment = Pos.CENTER
        }
        label(title) {
            addClass(Styles.heading)
        }
        tableview(vendors) {
            column("ID", Vendor::idProperty)
            column("Name", Vendor::nameProperty).makeEditable()
            column("Customers", Vendor::customers)
            onEditCommit {
                vendorRepository.save(it)
            }
        }
    }

}