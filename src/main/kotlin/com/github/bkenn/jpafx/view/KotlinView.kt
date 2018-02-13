package com.github.bkenn.jpafx.view

import com.github.bkenn.jpafx.Styles
//import com.github.bkenn.jpafx.data.VendorRepository
import com.github.bkenn.jpafx.model.VendorKotlin
import javafx.collections.FXCollections
import javafx.geometry.Pos
import tornadofx.*

class KotlinView : View("Hello TornadoFX") {

    //val vendorRepository: VendorRepository by di()
    val vendors = FXCollections.observableArrayList<VendorKotlin>()

    init {
        //vendorRepository.save(VendorKotlin("Oracle"))
        //vendorRepository.save(VendorKotlin("Google"))
        //vendors.addAll(vendorRepository.findAll())
    }

    override val root = vbox {
        style {
            alignment = Pos.CENTER
        }
        label(title) {
            addClass(Styles.heading)
        }
        tableview(vendors) {
            column("ID", VendorKotlin::id)
            column("Name", VendorKotlin::name)
        }
    }

}