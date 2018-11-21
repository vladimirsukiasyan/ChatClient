package company.sukiasyan.chatclient

import android.content.Context

const val HTTP_CONFIGURATION = "HTTP_CONFIGURATION"
const val DEFAULT_PORT = "8080"
const val DEFAULT_HOST = "localhost"
val daysName = listOf("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота")

fun Context.saveHttpConfig(host: String, port: String) {
    val edit = getSharedPreferences(HTTP_CONFIGURATION, Context.MODE_PRIVATE)?.edit()
    edit?.putString("host", host)
    edit?.putString("port", port)
    edit?.apply()
}

fun Context.getPort() = getSharedPreferences(HTTP_CONFIGURATION, Context.MODE_PRIVATE).getString("port", DEFAULT_PORT)!!

fun Context.getHost() = getSharedPreferences(HTTP_CONFIGURATION, Context.MODE_PRIVATE).getString("host", DEFAULT_HOST)!!

//TODO обощить на все классы
fun convertListToChatAnswer(list: List<Professor>?) = StringBuilder().apply {
    list?.forEach {
        val days = listOf(it.schedule.monday, it.schedule.tuesday, it.schedule.wednesday, it.schedule.thursday, it.schedule.friday, it.schedule.saturday)
        for (i in days.indices) {
            append("<<<<<<<${daysName[i]}>>>>>>>\n")
            days[i]?.let {
                it.forEach { lesson ->
                    append("${lesson.time}-------->")
                    if (lesson.subject.is_differ) {
                        append("ЧС-${lesson.subject.numerator?.name?:"Нет пар"},${lesson.subject.numerator?.auditory?:""}/ЗН-${lesson.subject.denominator?.name?:"Нет пар"},${lesson.subject.denominator?.auditory?:""}")
                    } else {
                        append("${lesson.subject.numerator?.name ?:"Нет пар"},${lesson.subject.numerator?.auditory?:"-"}")
                    }
                    append("\n\n")

                }
            }
            append("\n")
        }
    }
}.toString()