package com.example.demo.app

import com.example.demo.app.controller.MyController
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class LoginScreen : View() {
    val loginController: MyController by inject()
    val username = SimpleStringProperty(this, "username", config.string("username"))
    val password = SimpleStringProperty(this, "password", config.string("password"))

    override val root = form {
        fieldset("Login") {
            field("Username:") { textfield(username) }
            field("Password:") { textfield(password) }
            buttonbar {
                button("Login").action {
                    runAsync {
                        loginController.tryLogin(username.value, password.value)
                    } ui { success ->
                        if (success) {
                            with(config) {
                                set("username" to username.value)
                                set("password" to password.value)
                                save()
                            }
                            showMainScreen()
                        }
                    }
                }
            }
        }
    }

    fun showMainScreen() {
        // hide LoginScreen and show the main UI of the application
    }
}