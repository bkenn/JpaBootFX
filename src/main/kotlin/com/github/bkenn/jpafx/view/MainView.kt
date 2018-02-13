package com.github.bkenn.jpafx.view

import com.github.bkenn.jpafx.Styles
import com.github.bkenn.jpafx.data.VendorRepository
import com.github.bkenn.jpafx.model.Vendor
import javafx.collections.FXCollections
import javafx.geometry.Pos
import tornadofx.*

class MainView : View("Hello TornadoFX") {

    val vendorRepository: VendorRepository by di()
    val vendors = FXCollections.observableArrayList<Vendor>()

    init {
        vendorRepository.save(Vendor("Oracle"))
        vendorRepository.save(Vendor("Google"))
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
            column("ID", Vendor::id)
            column("Name", Vendor::name)
        }
    }

}

/*

 */