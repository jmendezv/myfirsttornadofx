package com.example.demo.app.controller

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.Controller

class MyController : Controller() {
//    val values: ObservableList<String> = FXCollections.observableArrayList("Alpha","Beta","Gamma","Delta")
    fun print(msg: String) {
       println(msg)
    }
}