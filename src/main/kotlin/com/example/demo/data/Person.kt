package com.example.demo.data

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Person(name: String? = null, title: String? = null) {
    val nameProperty = SimpleStringProperty(this, "name", name)
    var name by nameProperty

    val titleProperty = SimpleStringProperty(this, "title", title)
    var title by titleProperty
}

class PersonModel(person: Person) : ItemViewModel<Person>(person) {
    val name = bind(Person::nameProperty)
    val title = bind(Person::titleProperty)
}