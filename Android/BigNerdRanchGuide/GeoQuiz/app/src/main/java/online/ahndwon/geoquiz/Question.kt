package online.ahndwon.geoquiz

data class Question(val textResId: Int, val answerTrue: Boolean, var isCheater: Boolean = false)