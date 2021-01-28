package ru.skillbranch.devintensive.models

class Bender (var  status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion():String  = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    var error: Int = 0

    fun listenAnswer(answer: String ): Pair<String, Triple <Int, Int, Int>> {

        return if (question.answer.contains(answer)){
            if (question == Question.IDLE){
                "Отлично - ты справился\nНа этом все, вопросов больше нет" to status.color
            }
            else {
                question = question.nexQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            }
        } else {
            error++
            if (error == 3){
                error = 0
                question = Question.NAME
                status = Status.NORMAL
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }
            else {
                status = status.nextStatus()
                "Это неправильный ответ\n${question.question}" to status.color
            }
        }
    }

    enum class Status (val color: Triple<Int, Int, Int>){
        NORMAL (Triple(255, 255, 255)),
        WARNING (Triple(255, 120, 0)),
        DANGER (Triple(255, 60, 60)),
        CRITICAL (Triple(255, 0, 0));

        fun nextStatus():Status{
            return if (this.ordinal < values().lastIndex){
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question (val question: String, val answer: List<String>){
        NAME ("Как меня зовут?", listOf("Бендер", "bender")) {
            override fun nexQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nexQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nexQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nexQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nexQuestion(): Question = IDLE
        },
        IDLE ("На этом все, вопросов больше нет", listOf()) {
            override fun nexQuestion(): Question = IDLE
        };

        abstract fun nexQuestion():Question
    }
}

