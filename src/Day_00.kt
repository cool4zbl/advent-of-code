fun main() {
    val day = "01"

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
    check(part1(testInput) == 1)

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2(input).println()
}
