fun main() {
    fun matchNum(it: String): Int {
        val pattern = Regex(":\\s*(.*)\\s*\\|\\s*(.*)\\s*")
        return pattern.findAll(it).map {  match ->
            val (winning, mine) = match.destructured
            val l1 = winning.split(" ").filter { it != "" }
            val l2 = mine.split(" ").filter { it != "" }
            val r = l1.intersect(l2.toSet())
            r.size
        }.toList()[0]
    }

    fun part1(input: List<String>): Int {
        return input.map {
            matchNum(it)
        }.fold(0) { acc, i -> acc + if (i > 0) 1 shl (i-1) else 0 }
    }

    fun part2(input: List<String>): Int {
        val s = MutableList(input.size) { 0 }
        input.map { matchNum(it) }
            .forEachIndexed { index, times ->
                s[index] += 1
                for (i in 1..times) {
                    s[index+i] += 1 * s[index]
                }
        }

        return s.sumOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("input/Day04")
    part1(input).println()
    part2(input).println()
}
