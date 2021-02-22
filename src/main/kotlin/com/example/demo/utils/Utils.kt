package com.example.demo.utils

import javafx.geometry.Pos
import javafx.scene.Node
import javafx.util.Duration
import org.controlsfx.control.Notifications
import org.jasypt.util.text.BasicTextEncryptor
import de.jensd.fx.glyphs.*
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.scene.control.Button
import javafx.scene.control.ContentDisplay
import javafx.scene.control.Tooltip
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.Executors.callable
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import org.controlsfx.control.action.Action

class Utils {

    companion object {

        private fun currentYear() = LocalDate.now().year

        fun currentCourseYear(): Int {
            val month = LocalDate.now().month.value
            /* Entre setembre i desembre és l'any actual, si no és un any menys */
            return if (month > 8 && month <= 12) currentYear()
            else currentYear() - 1
        }

        fun nextCourseYear() = currentCourseYear() + 1

        fun currentCourse() = "${currentCourseYear()}-${nextCourseYear()}"

        // From String to Base 64 encoding
        fun String.encode(): String = Base64.getEncoder().encodeToString(this.toByteArray())

        // From Base 64 encoding to String
        fun String.decode(): String = String(Base64.getDecoder().decode(this))

        // Basic encryption
        fun String.encrypt(password: String): String = BasicTextEncryptor().apply {
            setPassword(password)
        }.encrypt(this)

        // Basic decryption
        fun String.decrypt(password: String) = BasicTextEncryptor().apply {
            setPassword(password)
        }.decrypt(this)

        // Valid format and letter
        fun String.isValidDniOrNie(): Boolean {

            // 22 termminacions possibles aleatòriament distribuides
            val terminacions = arrayOf(
                "T",
                "R",
                "W",
                "A",
                "G",
                "M",
                "Y",
                "F",
                "P",
                "D",
                "X",
                "B",
                "N",
                "J",
                "Z",
                "S",
                "Q",
                "V",
                "H",
                "L",
                "C",
                "K",
                "E"
            )

            val terminacio = substring(length - 1)

            // DNI 099999999A
            if (matches("0\\d{8}[A-Z]".toRegex())) {
                val modul = Integer.parseInt(substring(1, 9)) % 23
                return terminacio == terminacions[modul]
            }
            // DNI 99999999A
            if (matches("\\d{8}[A-Z]".toRegex())) {
                val modul = Integer.parseInt(substring(0, 8)) % 23
                return terminacio == terminacions[modul]
            }
            // NIE que comença per X9999999A, la X cau
            if (matches("[X]\\d{7}[A-Z]".toRegex())) {
                return true
            }
            // NIE que comença per Y9999999A, la Y es substitueix per 1
            if (matches("[Y]\\d{7}[A-Z]".toRegex())) {
                return true
            }
            // NIE que comença per Z9999999A, la Z es substitueix per 2
            if (matches("[Z]\\d{7}[A-Z]".toRegex())) {
                return true
            }

            return false
        }

        /*
        * Aquesta funció planifica una acció després d'una demora
        * */
        fun <V, T : ScheduledExecutorService> T.schedule(
            delay: Long,
            unit: TimeUnit = TimeUnit.MILLISECONDS,
            action: () -> V
        ): ScheduledFuture<*> {
            return schedule(
                callable { action() },
                delay, unit
            )
        }

        /*
        * Aquesta funció converteix una cadena a LocalDate
        *
        * 99/99/9999
        * 9/9/99
        * 99-99-9999
        * 9-9-99
        * 99.99.9999
        * 9.9.99
        *
        * */
        fun parseDate(dataStr: String): LocalDate {

            lateinit var data: LocalDate

            if (dataStr.matches("\\d\\d/\\d\\d/[0-9]{4}".toRegex())) {
                data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    ?: LocalDate.now()
            } else if (dataStr.matches("\\d/\\d/[0-9]{2}".toRegex())) {
                data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("d/M/yy"))
                    ?: LocalDate.now()
            } else if (dataStr.matches("\\d\\d-\\d\\d-[0-9]{4}".toRegex())) {
                data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    ?: LocalDate.now()
            } else if (dataStr.matches("\\d-\\d-[0-9]{2}".toRegex())) {
                data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("d-M-yy"))
                    ?: LocalDate.now()
            } else if (dataStr.matches("\\d\\d\\.\\d\\d\\.[0-9]{4}".toRegex())) {
                data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                    ?: LocalDate.now()
            } else if (dataStr.matches("\\d\\.\\d\\.[0-9]{2}".toRegex())) {
                data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("d.M.yy"))
                    ?: LocalDate.now()
            } else {
                data = LocalDate.now()
            }

            return data
        }

        /*
        * Aquesta funció verifica si un email té un format correcte
        * */
        fun isEmailValid(email: String): Boolean = Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()


        /* From LocalDate to Date*/
        fun asDate(localDate: LocalDate) =
            asDate(localDate.atStartOfDay())

        /* From LocalDateTime to Date */
        fun asDate(localDateTime: LocalDateTime) =
            Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())

        fun asLocalDate(date: Date) =
            Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

        fun asLocalDateTime(date: Date) =
            asLocalDate(date).atStartOfDay()

        internal fun notification(
            title: String?,
            text: String?,
            graphic: Node?,
            position: Pos = Pos.BOTTOM_RIGHT,
            hideAfter: Duration = Duration.seconds(5.0),
            darkStyle: Boolean = false, owner: Any?, vararg actions: Action
        ): Notifications {
            val notification = Notifications
                .create()
                .title(title ?: "")
                .text(text ?: "")
                .graphic(graphic)
                .position(position)
                .hideAfter(hideAfter)
                .action(*actions)
            if (owner != null)
                notification.owner(owner)
            if (darkStyle)
                notification.darkStyle()
            return notification
        }

        fun warningNotification(
            title: String?,
            text: String?,
            position: Pos = Pos.BOTTOM_RIGHT,
            hideAfter: Duration = Duration.seconds(5.0),
            darkStyle: Boolean = false, owner: Any? = null, vararg actions: Action
        ) {
            notification(title, text, null, position, hideAfter, darkStyle, owner, *actions)
                .showWarning()
        }

        fun infoNotification(
            title: String?,
            text: String?,
            position: Pos = Pos.BOTTOM_RIGHT,
            hideAfter: Duration = Duration.seconds(5.0),
            darkStyle: Boolean = false, owner: Any? = null, vararg actions: Action
        ) {
            notification(title, text, null, position, hideAfter, darkStyle, owner, *actions)
                .showInformation()
        }

        fun confirmNotification(
            title: String?,
            text: String?,
            position: Pos = Pos.BOTTOM_RIGHT,
            hideAfter: Duration = Duration.seconds(5.0),
            darkStyle: Boolean = false, owner: Any? = null, vararg actions: Action
        ) {
            notification(title, text, null, position, hideAfter, darkStyle, owner, *actions)
                .showConfirm()
        }

        fun errorNotification(
            title: String?,
            text: String?,
            position: Pos = Pos.BOTTOM_RIGHT,
            hideAfter: Duration = Duration.seconds(5.0),
            darkStyle: Boolean = false, owner: Any? = null, vararg actions: Action
        ) {
            notification(title, text, null, position, hideAfter, darkStyle, owner, *actions)
                .showError()
        }

        fun customNotification(
            title: String?,
            text: String?,
            graphic: Node,
            position: Pos = Pos.BOTTOM_RIGHT,
            hideAfter: Duration = Duration.seconds(5.0),
            darkStyle: Boolean = false, owner: Any? = null,
            vararg actions: Action
        ) {
            notification(title, text, graphic, position, hideAfter, darkStyle, owner, *actions)
                .show()
        }

        /* De COGNOM1 COGNOM2, NOM1 NOM2 a Nom1 Nom2 Cognom1 Cognom2 */
        fun String.toNomPropi() =
            toLowerCase()
                .split(",")
                .reversed()
                .joinToString(separator = " ")
                .trim()
                .split(" ")
                .joinToString(separator = " ") {
                    it.capitalize()
                }

        fun Date.toCatalanDateFormat() = SimpleDateFormat("dd/MM/yyyy").format(this)

        fun Date.formData() = """Barcelona, a ${SimpleDateFormat("d MMMM yyyy").format(this)}"""

        fun Date.toCatalanTimeFormat() = SimpleDateFormat("hh:mm").format(this)

        val simpleDateFormatter = SimpleDateFormat("dd/MM/yyyy")

        val anotherSimpleDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val americanDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        fun Button.icon(icon: GlyphIcons, toolTip: String = "", minButtonWidth: Double = 64.0) {
            graphic = when (icon) {
                is FontAwesomeIcon -> FontAwesomeIconView(icon)
                is MaterialDesignIcon -> MaterialDesignIconView(icon)
                else -> throw IllegalArgumentException("Unknown font family ${icon.fontFamily}")
            }
            with(graphic as GlyphIcon<*>) {
                contentDisplay = ContentDisplay.GRAPHIC_ONLY
                setSize("3em")
                setGlyphSize(32.0)
            }
            minWidth = minButtonWidth
            tooltip = Tooltip(toolTip)
        }

    }
}

