package com.example.demo.app

import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.*
import java.nio.file.Paths
import java.util.*

class MyApp: App(SimpleView::class, Styles::class) {

    override val configBasePath = Paths.get("/users/pep/conf")

    override fun start(stage: Stage) {
        super.start(stage)
        stage.isResizable = true
        setStageIcon(Image("file://images/logo.png"))
        with(config) {
        }
    }


    override fun init() {
        super.init()
        FX.locale = Locale("es")
        Thread.setDefaultUncaughtExceptionHandler(DefaultErrorHandler())
    }
}

fun main(args: Array<String>) {
    launch<MyApp>(args)
}