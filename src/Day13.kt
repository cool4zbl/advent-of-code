//--- Day 13: Point of Incidence ---

fun main() {
    val day = "13"

    fun find(arr: CharArray2): Long {
        val (m, n) = arr.size2()

        for (k in 1..<n) {
            var ok = true
            for (i in 0..<m) {
                for (j in 0..<minOf(n-k, k)) {
                    // offset `j` to `k`
                    // (k-j-1) .. k-1, k, .., (k+j)
                    if (arr[i][k-j-1] != arr[i][k+j]) {
                        ok = false
                        break
                    }
                }
            }
            if (ok) return k.toLong()
        }
        return 0
    }

    fun process(arr: CharArray2): Long {
        val (m, n) = arr.size2()
//         vertical
        for (k in 1..<n) {
            var ok = true
            for (i in 0..<m) {
                for (j in 0..<minOf(n-k, k)) {
                    // offset `j` to `k`
                    // (k-j-1) .. k-1, k, .., (k+j)
                    if (arr[i][k-j-1] != arr[i][k+j]) {
                        ok = false
                        break
                    }
                }
            }
            if (ok) return k.toLong()
        }

        for (k in 1..<m) {
            var ok = true
            for (i in 0..<minOf(m-k, k)) {
                for (j in 0..<n) {
                    if (arr[k-i-1][j] != arr[k+i][j]) {
                        ok = false
                        break
                    }
                }
            }
            if (ok) return (0L + k * 100)
        }
        return 0L

    }

    fun part1(input: List<String>): Long {
        return groupListFromInput(input).sumOf {
            find(it.toCharArray2()) + find(transpose(it.toCharArray2())) * 100 }
    }

    fun find2(arr: CharArray2): Long {
        val (m, n) = arr.size2()
        for (k in 1..<m) {
            var diff = 0
            for (i in 0..<minOf(m-k, k)) {
                for (j in 0..<n) {
                      if (arr[k-i-1][j] != arr[k+i][j]) {
                        diff += 1
                    }
                }
            }
            if (diff == 1) {
                return k.toLong()
            }
        }
        return 0L
    }

    fun part2(input: List<String>): Long {
        return groupListFromInput(input).sumOf { find2(it.toCharArray2()) * 100 + find2(transpose(it.toCharArray2())) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
    val r = part1(testInput)
    println(r)
    check( r == 405L)

    check(part2(testInput) == 400L)

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2(input).println()
}
