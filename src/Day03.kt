
fun main() {
    fun part1(input: List<String>): Int {
        val newElement = "."
        val originalList = input.map { it.map { c -> c.toString() } }

        // Add an element at the beginning and end of each row
        val expandedRows = originalList.map { listOf(newElement) + it + newElement }
        // Create a new row for top and bottom
        val newRow = List(expandedRows[0].size) { newElement }
        // Add the new row at the top and bottom
        val arr = listOf(newRow) + expandedRows + listOf(newRow)

//        arr.forEach { row ->
//            println(row.joinToString(", "))
//        }

        var sum = 0
        var i = 1
        while (i <= originalList.size) {
            var j = 1
            while (j < originalList[0].size) {
                val cur = arr[i][j]

                if (cur[0].isDigit()) {
                    var right = j
                    while (arr[i][right][0].isDigit()) {
                        right++
                    }
                    var found = false
                    for (p in i-1 until i+2) {
                        for (q in j-1 until right+1) {
                            if (arr[p][q] != newElement && !arr[p][q][0].isDigit()) {
                                val num = arr[i].subList(j, right).joinToString("").toInt()
                                sum += num
                                found = true
                                break
                            }
                        }
                        if (found) break
                    }
                    j = right
                } else {
                    j++
                }
            }
            i++
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val r = part1(testInput)
    check(r == 4361)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
