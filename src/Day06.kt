import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.math.sqrt

fun main() {
    fun part1(input: List<String>): Int {
        val l = input.map { s ->
            s.split(":")[1].trim().split("\\s+".toRegex()).map { it.toInt() }
        }
        val t = l[0]
        val d = l[1]

        return t.mapIndexed { index, ms ->
            var sum = 0
            for (i in 1 until ms) {
                val dis = (ms - i) * i
                sum += if (dis > d[index]) 1 else 0
            }
            sum
        }.reduce{ acc, i -> acc * i }
    }

    fun greater(a: String, b: String): Boolean {
        // compare two positive numbers
        if (a.length > b.length) return true
        if (a.length < b.length) return false

        val aList = a.split("").filter { it != "" }.map { it.toInt() }
        val bList = b.split("").filter { it!= "" }.map { it.toInt() }
        var carry = 0

        val s = ArrayList<Int>()

        for (i in aList.size-1 downTo 0) {
            val diff = aList[i] + carry - bList[i]
            carry = if (diff < 0) {
                s.add(10 + diff)
                -1
            } else {
                s.add(diff)
                0
            }
        }
        return s.count { it != 0 } > 0 && carry >= 0
    }

    fun part2(input: List<String>): Int {
        val l = input.map { s ->
            s.split(":")[1].replace(" ", "")
        }
        val ms = l[0].toDouble()
        val dist = l[1].toDouble()

        // (ms - x) * x - dist >= 0
        // a = 1, b = -ms, c = dist
        val r0 = (ms - sqrt(ms * ms - 4 * dist)) / 2
        val r1 = (ms + sqrt(ms * ms - 4 * dist)) / 2

        return(floor(r1) - ceil(r0)).roundToInt() + 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day06_test")
    check(part1(testInput) == 288)
    println(part2(testInput))
    check(part2(testInput) == 71503)

    val input = readInput("input/Day06")
    part1(input).println()
    part2(input).println()
}
