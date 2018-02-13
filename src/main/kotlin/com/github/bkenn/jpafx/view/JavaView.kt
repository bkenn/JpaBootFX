package com.github.bkenn.jpafx.view

import com.github.bkenn.jpafx.Styles
import com.github.bkenn.jpafx.data.VendorJavaRepository
//import com.github.bkenn.jpafx.data.VendorRepository
import com.github.bkenn.jpafx.model.VendorJava
import javafx.collections.FXCollections
import javafx.geometry.Pos
import tornadofx.*

class JavaView : View("Hello TornadoFX") {

    val vendorRepository: VendorJavaRepository by di()
    val vendors = FXCollections.observableArrayList<VendorJava>()

    init {
        vendorRepository.save(VendorJava("Oracle"))
        vendorRepository.save(VendorJava("Google"))
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
            column("ID", VendorJava::idProperty)
            column("Name", VendorJava::nameProperty)
        }
    }

}