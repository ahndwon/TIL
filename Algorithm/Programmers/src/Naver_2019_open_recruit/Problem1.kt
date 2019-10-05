package Naver_2019_open_recruit

class Solution {
    fun solution(emails: Array<String>): Int {
        var answer = 0

        emails.forEach { email ->
            val atToken = email.split("@")

            if (atToken.size == 2) {
                println("atToken size == 2")

                var isUnder = true

                val periodToken = atToken[1].split(".")
                println(atToken)
                println(periodToken)


                atToken[1].toCharArray().forEach { char ->
                    isUnder = char in 'a'..'z' || char == '.'
                }


                if (isUnder && periodToken.size == 2)


//                    if (periodToken[1] == "com") {
//                        answer++
//                    }
                    when (periodToken[1]) {
                        "com" -> {
                            answer++
                        }
                        "net" -> answer++
                        "org" -> answer++
                        else -> {
                        }
                    }
            }
        }

        println(answer)

        return answer
    }
}

fun main(args: Array<String>) {
    val test = arrayOf("d@com@m.com", "a@abc.com")
    Solution().solution(test)
}