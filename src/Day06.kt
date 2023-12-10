import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.math.sqrt

fun main() {
    fun part1(input: List<String>): Int {
        val (t, d) = input.map { s ->
            s.split(":")[1].trim().split("\\s+".toRegex()).map { it.toInt() }
        }

        return t.mapIndexed { index, ms ->
            var sum = 0
            for (i in 1 until ms) {
                val dis = (ms - i) * i
                sum += if (dis > d[index]) 1 else 0
            }
            sum
        }.reduce{ acc, i -> acc * i }
    }


    fun part2(input: List<String>): Int {
        val (ms, dist) = input.map { s ->
            s.split(":")[1].replace(" ", "")
        }.map { it.toDouble() }

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
