fun main() {
    fun part1(input: List<String>): Int {
        val maxR = 12
        val maxG = 13
        val maxB = 14
        var sum = 0
        val pattern = Regex("(\\d+)\\s*(red|green|blue)")

        for ((index, s) in input.withIndex()) {
            val ok = s.split(";").all {
                val m = pattern.findAll(it).map { match ->
                    val (value, color) = match.destructured
                    color to value.toInt()
                }.toMap()
                val b = m["blue"] ?: 0
                val r = m["red"] ?: 0
                val g = m["green"] ?: 0

                b <= maxB && r <= maxR && g <= maxG
            }
            sum += if(ok) index+1 else 0
        }
        return sum

    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val r = part1(testInput)
    check(r == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
