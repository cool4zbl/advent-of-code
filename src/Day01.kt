fun main() {
    fun part1(input: List<String>): Int {
        val regex = """[a-zA-Z]""".toRegex()
        return input.map{ regex.replace(it, "") }
            .map { "${it[0]}${it[it.length - 1]}" }
            .sumOf { it.toInt() }
    }

    fun formatFunc(toReplace: String): String {
        return listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
            .foldIndexed(toReplace) { index, acc, s ->
                // eighthree -> eight8eighthree -> eight8eighthree3three
                acc.replace(s, "$s${index + 1}$s")
            }
    }

    fun part2(input: List<String>): Int {
        return part1(input.map { formatFunc(it) })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val testInput2 = readInput("Day01_test_2")

    val r2 = part2(testInput2)
    println(r2)

    check(r2 == 281 + 83 + 79)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
