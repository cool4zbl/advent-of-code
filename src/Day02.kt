fun main() {
    fun matchColor(it: String): Map<String, Int> {
        val pattern = Regex("(\\d+)\\s*(red|green|blue)")
        return pattern.findAll(it).map { match ->
            val (value, color) = match.destructured
            color to value.toInt()
        }.toMap()
    }

    fun part1(input: List<String>, maxR: Int, maxG: Int, maxB: Int): Int {
        var sum = 0

        input.mapIndexed { index, s ->
            val ok = s.split(";").all {
                val m = matchColor(it)
                val b = m["blue"] ?: 0
                val r = m["red"] ?: 0
                val g = m["green"] ?: 0

                b <= maxB && r <= maxR && g <= maxG
            }
            sum += if(ok) index+1 else 0
        }
        return sum
    }

    fun part2(input: List<String>, maxR: Int, maxG: Int, maxB: Int): Int {
        return input.map { game ->
            game.split(";").fold(listOf(maxR, maxG, maxB)) { acc, s ->
                val m = matchColor(s)
                val r = m["red"] ?: 0
                val g = m["green"] ?: 0
                val b = m["blue"] ?: 0

                val minR = if (r > acc[0]) r else acc[0]
                val minG = if (g > acc[1]) g else acc[1]
                val minB = if (b > acc[2]) b else acc[2]
                listOf(minR, minG, minB)
            }
        }.sumOf { it[0] * it[1] * it[2] }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val r = part1(testInput, 12, 13, 14)
    check(r == 8)

    val r2 = part2(testInput,0, 0, 0)
    check(r2 == 2286)

    val input = readInput("Day02")
    part1(input, 12, 13, 14).println()
    part2(input, 0, 0, 0).println()
}
