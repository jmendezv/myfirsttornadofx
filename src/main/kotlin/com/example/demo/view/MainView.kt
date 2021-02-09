package com.example.demo.view

import com.example.demo.app.Styles
import tornadofx.*

class MainView : View("Hello TornadoFX") {
    override val root = vbox {
        label(title) {
            addClass(Styles.heading)
        }
        label("Etiqueta 2") {
            addClass(Styles.heading)
        }
    }
}