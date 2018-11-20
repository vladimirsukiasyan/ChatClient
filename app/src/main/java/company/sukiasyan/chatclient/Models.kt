package company.sukiasyan.chatclient

enum class TypeOfMessage{
    SentMessage,ReceivedMessage
}

open class Message(val message: String, var type: TypeOfMessage)

class MessageReceived(val body:List<Professor>, val continue_dialog_on_client:Boolean, message: String): Message(message, TypeOfMessage.ReceivedMessage)

data class Professor (val id:Long, val surname:String, val name:String, val patronymic: String, val chair: String, val schedule: Schedule){
    data class Schedule(val id:Long, val monday: List<Lesson>, val tuesday: List<Lesson>, val wednesday: List<Lesson>, val thursday: List<Lesson>, val friday: List<Lesson>, val saturday: List<Lesson>){
        data class Lesson(val time:String, val subject: Subject){
            data class Subject(val numerator: SubjectWeek, val denominator: SubjectWeek, val is_differ: Boolean){
                data class SubjectWeek(val name: String, val auditory: String)
            }
        }
    }
}


