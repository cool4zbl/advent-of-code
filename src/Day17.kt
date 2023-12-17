//--- Day 17: Clumsy Crucible ---

fun main() {
    val day = "17"

    fun part1(input: List<String>): Int {

        return input.size
    }
    fun part1_0(input: List<String>): Int {
        val arr = input.toIntArray2()
        val (m, n) = arr.size2()

        val dp = Array(m) { IntArray(n) }
        (0..<m).map { dp[it][0] = arr[it][0] }
        (0..<n).map { dp[0][it] = arr[0][it] }

        for (i in 1..<m) {
            for (j in 1..<n) {
                dp[i][j] = arr[i][j] + minOf(dp[i-1][j], dp[i][j-1])
            }
        }

        print2DList(dp)

        val r = mutableListOf<Int>()
        var i = m-1
        var j = n-1
        while (i > 0 && j > 0) {
            r.add(dp[i][j] - minOf(dp[i-1][j], dp[i][j-1]))
            if (minOf(dp[i-1][j], dp[i][j-1]) == dp[i-1][j]) {
                i--
            } else {
                j--
            }
        }
        if (i == 0) r.addAll(arr[0].toList().reversed())
        else r.addAll((0..<m).map { arr[it][0] }.reversed())
        r.reversed().println()


        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
    check(part1(testInput) == 102)

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2(input).println()
}
