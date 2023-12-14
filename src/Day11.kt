import kotlin.math.abs

fun main() {
    val space = '.'

    fun part1(input: List<String>, factor: Int = 2): Long {
        val arr = input.toCharArray2()
        val (m, n) = arr.size2()

        val spaceRows = mutableListOf<Int>()
        for (i in 0..<m) {
            if (arr[i].all { it == space }) {
                spaceRows.add(i)
            }
        }

        val starsPos = mutableListOf<List<Int>>()
        val spaceCols = mutableListOf<Int>()
        for (j in 0..<n) {
            var isSpace = true
            for (i in arr.indices) {
                if (arr[i][j] != space) {
                    isSpace = false
                    starsPos.add(listOf(i, j))
                }
            }
            if (isSpace) {
                spaceCols.add(j)
            }
        }

        var sum = 0L
        for (p in 0 until starsPos.size) {
            for (q in (p+1) until starsPos.size) {
                val x1 = starsPos[p][0]
                val x2 = starsPos[q][0]
                var rows = abs(x1 - x2)
                val appendR = spaceRows.filter {
                    it in x1..x2 || it in x2..x1
                }.size
                val y1 = starsPos[p][1]
                val y2 = starsPos[q][1]
                var cols = abs(y1 - y2)
                val appendC = spaceCols.filter {
                    it in y1..y2 || it in y2..y1
                }.size

                rows += appendR * (factor - 1)
                cols += appendC * (factor - 1)

                sum += rows + cols
            }
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        return part1(input, 1000000)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day11_test")
    check(part1(testInput) == 374L)
    val r = part2(testInput)
    println(r)
    check(r == 82000210L)

    val input = readInput("input/Day11")
    part1(input).println()
    part2(input).println()
}
