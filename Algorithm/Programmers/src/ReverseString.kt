import java.lang.StringBuilder

class ReverseString {

}

fun String.reverse() : String {
    val charArray = this.toCharArray()
    val builder = StringBuilder()

    val length = charArray.size - 1
    for (i in length downTo 0) {
        println(i)
        builder.append(charArray[i])
    }

    return builder.toString()
}

fun main(args: Array<String> ) {
    val string = "String"

    println("reversed ${string.reverse()}")
}