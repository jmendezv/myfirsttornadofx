package com.example.demo.app

import com.example.demo.app.controller.MyController
import javafx.beans.property.SimpleIntegerProperty
import javafx.concurrent.Task
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Modality
import javafx.stage.StageStyle
import tornadofx.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Path


class MyView : View() {
    // TornadoFX delegates
    override val root: BorderPane by fxml()
    val controller: MyController by inject()
    // Observable
    val counter = SimpleIntegerProperty()
    val counterLabel: Label by fxid()
    val incrementButton: Button by fxid()
    val file = File("filename.txt")

    init {
//        Files.write(File("anotherfile").toPath(), "contingut".toByteArray())
        file.printWriter().use { out ->
            out.println("Primera línea")
            out.println("Segunda línea")
            out.println("Tercera línea")
        }
        // Observer
        counterLabel.bind(counter)

        incrementButton.action {
            onIncrementButton()
        }
    }

    private fun onIncrementButton() {

        runAsync {
//                println(Thread.currentThread())
//                controller.print(counter.value.toString())
            } ui {
//                println(Thread.currentThread())
//                counter.value++
//                find<MyFragment2>().openModal(
//                    modality = Modality.APPLICATION_MODAL,
//                    stageStyle = StageStyle.UTILITY)
            }
    }

}

class MyFragment: Fragment() {
    override val root = vbox {
        textflow {
            tooltip("Two simple labels")
            text("Tornado") {
                fill = Color.PURPLE
                font = Font(20.0)
            }
            text("FX") {
                fill = Color.ORANGE
                font = Font(28.0)
            }
            checkbox("Admin Mode") {
                action { println(isSelected) }
            }
        }
    }
}

class MyFragment2: Fragment() {
    override val root = borderpane {
            top = label("TOP") {
                useMaxWidth = true
                style {
                    backgroundColor += Color.RED
                }
            }

            bottom = label("BOTTOM") {
                useMaxWidth = true
                style {
                    backgroundColor += Color.BLUE
                }
            }

            left = label("LEFT") {
                useMaxWidth = true
                style {
                    backgroundColor += Color.GREEN
                }
            }

            right = label("RIGHT") {
                useMaxWidth = true
                style {
                    backgroundColor += Color.PURPLE
                }
            }

            center = label("CENTER") {
                useMaxWidth = true
                style {
                    backgroundColor += Color.YELLOW
                }
            }
        }

}