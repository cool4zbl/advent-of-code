import java.util.*

fun main() {
    val day = "12"

    val m = mutableMapOf<String, List<String>>()

    fun generateCombinations(s: String): List<String> {
        if (s.isEmpty()) return listOf()

        if (m[s]?.isNotEmpty() == true) {
            return m[s]!!
        }

        val queue: Queue<StringBuilder> = LinkedList<StringBuilder>().apply {
            add(StringBuilder(s))
        }

        while (queue.peek().indexOf('?') != -1) {
            val current = queue.remove()
            val index = current.indexOf('?')
            queue.add(StringBuilder(current).apply { setCharAt(index, '#') })
            queue.add(StringBuilder(current).apply { setCharAt(index, '.') })
        }

        m[s] = queue.map { it.toString() }
        return queue.map { it.toString() }
    }

    // generate `???` with `#` & `.`
    fun combination(ans: MutableList<String>, s: String) {
        if (!s.contains('?')) {
            ans.add(s)
            return
        }
        for (i in listOf("#", ".")) {
            combination(ans, s.replaceFirst("?", i))
        }
    }

    fun part1(input: List<String>): Long {
        val records = mutableListOf<List<Int>>()
        val conds = mutableListOf<String>()
        input.map { ss ->
            val (str, ns) = ss.split(" ")

            conds.add(str)
            records.add(ns.split(",").map {
                it.toInt()
            })
        }

        val sum = conds.mapIndexed { index, c ->

            val ans = generateCombinations(c)
//            val ans = mutableListOf<String>()
//            combination(ans, c)
            val s = ans.filter {
                it.split(".").filter { it.isNotBlank() }.map { it.length } == records[index]
            }.size.toLong()
            println("part1 $s")
            s
        }.sumOf { it }
        return sum
    }

    // TODO:
    fun part2(input: List<String>): Long {

        val records = mutableListOf<List<Int>>()
        val conds = mutableListOf<String>()
        input.map { it.split(" ") }.map { row ->
            val (str, ns) = row

            conds.add(Array(5) { str }.joinToString("?"))
            records.add(Array(5) { ns }.joinToString(",").split(",").map {
                it.toInt()
            })
        }
        println(conds.map { c -> println(c ) })
        println("records: ${records.map { c -> println(c) }}")

        val sum = conds.mapIndexed { index, c ->
            val ans = generateCombinations(c)

            val s = ans.filter {
                it.split(".").filter { it.isNotBlank() }.map { it.length } == records[index]
            }.size.toLong()
            println("part2 $s")
            s
        }.sumOf { it }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
    check(part1(testInput) == 21L)

    val r = part2(testInput)
    println(r)
    check(r == 525152L)

    val input = readInput("input/Day${day}")
    part1(input).println()
//    part2(input).println()
}
