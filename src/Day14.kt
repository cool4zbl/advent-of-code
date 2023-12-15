fun main() {
    val day = "14"

    fun part1(input: List<String>): Int {
        val arr = input.toCharArray2()
        val (m, n) = arr.size2()
        for (j in 0..<n) {
            for (i in 0..<m) {
                if (arr[i][j] == 'O') {
                    var k = i
                    while (k > 0 && arr[k-1][j] == '.') k--
                    arr[i][j] = '.'
                    arr[k][j] = 'O'
                }
            }
        }
        print2DCharArray(arr)

        var sum = 0
        for (i in 0..<m) {
            for (j in 0..<n) {
                if (arr[i][j] == 'O') {
                    sum += m-i
                }
            }
        }
        println(sum)

        return sum

    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
    check(part1(testInput) == 136)

    val input = readInput("input/Day${day}")
//    part1(input).println()
    part2(input).println()
}
