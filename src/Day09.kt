fun main() {
    fun process(rowList: List<Int>): Int {
        val arr = ArrayList<List<Int>>()
        var lst = rowList
        while(!lst.all { n -> n == 0 }) {
            arr.add(lst)
            val diff = ArrayList<Int>()
            for (i in 1 until lst.size) {
                diff.add(lst[i] - lst[i-1])
            }
            lst = diff
        }

        return arr.fold(0) { acc, ints -> acc + ints[ints.size - 1] }
    }

    fun part1(input: List<String>): Int {
        return input.map { it ->
            process(it.split(" ").map { it.toInt() })
        }.sumOf { it }
    }

    fun part2(input: List<String>): Int {
        return input.map { it ->
            process(it.split(" ").map { it.toInt() }.reversed())
        }.sumOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)

    val testInput2 = readInput("Day09_test")
    check(part2(testInput2) == 2)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
