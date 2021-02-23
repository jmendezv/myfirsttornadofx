package com.example.demo.app

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import tornadofx.*

class SimpleView : View("My View") {

    val labelTextProperty = SimpleStringProperty("Default value")
    var labelText by labelTextProperty

    override val root = vbox {
        alignment = Pos.CENTER
        spacing = 10.0

        label(labelText) {
            bind(labelTextProperty)
            addClass(Styles.heading)
        }
        button("Click me") {
            action {
                //labelTextProperty.set("You just clicked me")
                labelText = "Another value"
            }
        }
    }
}
