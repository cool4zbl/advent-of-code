fun main() {
    val day = "13"

    fun checkMatch(arr: CharArray2, i: Int, j: Int): Int {
        var p = i
        var q = j
        while (p >= 0 && q < arr.size) {
            if (arr[p].joinToString("") != arr[q].joinToString("")) {
                return -1
            }
            p--
            q++
        }
        return 0
    }

    fun process(arr: CharArray2): Long {
//        var sum = 0L
        val (m, n) = arr.size2()
        // vertical
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
    fun readGroupsFromFile(input: List<String>): List<List<String>> {
        val groups = mutableListOf<List<String>>()
        val currentGroup = mutableListOf<String>()

        input.forEach { line ->
            if (line.isBlank()) { // Check for an empty line
                if (currentGroup.isNotEmpty()) {
                    groups.add(currentGroup.toList())
                    currentGroup.clear()
                }
            } else {
                currentGroup.add(line)
            }
        }

        // Add the last group if it's not empty
        if (currentGroup.isNotEmpty()) {
            groups.add(currentGroup.toList())
        }

        return groups
    }

    fun part1(input: List<String>): Long {
        return readGroupsFromFile(input).sumOf { process(it.toCharArray2()) }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
    val r = part1(testInput)
    println(r)
    check( r == 405L)

    check(part2(testInput) == 400)

    val input = readInput("input/Day${day}")
    part1(input).println()
//    part2(input).println()
}
