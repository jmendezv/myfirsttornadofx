package com.example.demo.app

import com.example.demo.app.controller.MyController
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import javafx.scene.control.Label
import javafx.scene.control.TextInputDialog
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.FileChooser
import tornadofx.*
import tornadofx.FX.Companion.messages
import java.io.File


class MyView : View(messages["app_title"]) {
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
            showDialog()
        }
    }

    /*
    * Aquest mètode retorna un File o null
    * */
    fun openFile() : File? {
        val fileChooser = FileChooser()
        fileChooser.title = "Obre Estada"

        fileChooser.initialDirectory = File("/users/pep")
        fileChooser.extensionFilters.addAll(
            FileChooser.ExtensionFilter("Texto", "*.txt")
//            FileChooser.ExtensionFilter("All Files", "*.*")
        )
        val selectedFile: File? = fileChooser.showOpenDialog(currentWindow)
        return selectedFile

    }

    /*
    * Aquest mètode retorna un File o null
    * */
    fun openFile2(): File? {
        val title = "Editor de texto"
        val filters = arrayOf(FileChooser.ExtensionFilter("Texto", "*.txt"))
        val mode = FileChooserMode.Single
        val owner = currentWindow
        val files = chooseFile(title, filters, mode, owner)
        return if (files.size > 0) files[0]
        else null
    }

    /*
    * Aquest mètode retorna una cadena o null
    * */
    fun getFilename(): String? {
        var filename: String? = null
        val dialog = TextInputDialog()
        dialog.setTitle("Editor de textos")
        dialog.contentText = "Nombre de archivo"
        dialog.showAndWait()
            .ifPresent { string ->
                filename = string
            }
        return filename
    }

    fun showDialog() {

        dialog("Add note") {
            val model = ViewModel()
                val note = model.bind { SimpleStringProperty() }

                field("Note") {
                    textarea(note) {
                        required()
                        whenDocked { requestFocus() }
                    }
                }
                buttonbar {
                    button("Save note").action {
                        model.commit {  }
                    }
                }
            }
    }

    fun chooseFilename() {
        val filename = getFilename()
        information("Has escojido este fichero",
            filename,
            ButtonType.CLOSE,
            owner = currentWindow,
            title = "Editor de textos")
    }


    private fun onIncrementButton() {

        information("Titulo", "Se ha guardado correctament")

        confirmation("nom de fitxer") { resp ->
            when(resp) {
                ButtonType.OK -> {

                }
            }
        }

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