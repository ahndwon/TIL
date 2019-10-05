package Naver_2019_open_recruit

class Solution3 {
    fun solution(drum: Array<String>): Int {
        var answer = -1

        var N = drum.size

        val map = Array(N) { Array(N) { i -> '#' } }


        val map2 = drum.flatMap {
            it.toCharArray().indices
        }

        drum.forEachIndexed {i, row ->
            row.toCharArray().forEachIndexed { j, c ->
                map[i][j] = c
            }
        }

        println(map.size)
        println(map2)

        return answer
    }
}

fun main(args: Array<String>) {
    val test = arrayOf("######",">#*###","####*#","#<#>>#",">#*#*<","######")
    Solution().solution(test)
}