fun main() {
    fun part1(input: List<String>): Int {
        val regex = """[a-zA-Z]""".toRegex()
        return input.map{
            regex.replace(it, "")
        }.map {
            "${it[0]}${it[it.length - 1]}"
        }.sumOf { it.toInt() }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
