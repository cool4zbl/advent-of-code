
fun main() {
    val newElement = "."
    val starSymbol = "*"

    fun enlarge2DList(originalList: List<List<String>>, newItem: String = newElement): List<List<String>> {
        // Add an element at the beginning and end of each row
        val expandedRows = originalList.map { listOf(newItem) + it + newItem }
        // Create a new row for top and bottom
        val newRow = List(expandedRows[0].size) { newItem }
        // Add the new row at the top and bottom
        return listOf(newRow) + expandedRows + listOf(newRow)
    }

    fun part1(input: List<String>): Int {
        val originalList = input.map { it.map { c -> c.toString() } }
        val arr = enlarge2DList(originalList)

        var sum = 0
        var i = 1
        while (i <= originalList.size) {
            var j = 1
            while (j <= originalList[0].size) {
                val cur = arr[i][j]

                if (cur[0].isDigit()) {
                    var right = j
                    while (arr[i][right][0].isDigit()) {
                        right++
                    }
                    var found = false
                    for (p in i-1 .. i+1) {
                        for (q in j-1 .. right) {
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

    fun findNum(arr: List<List<String>>, row: Int, col: Int): List<Int> {
        var l = col
        var r = col
        while (arr[row][l][0].isDigit()) {
            l--
        }
        while (arr[row][r][0].isDigit()) {
            r++
        }
        val num = arr[row].subList(l+1, r).joinToString("").toInt()
        return listOf(num, l, r)

    }

    fun part2(input: List<String>): Int {
        val originalList = input.map { it.map { c -> c.toString() } }
        val arr = enlarge2DList(originalList)
//        print2DList(arr)

        var sum = 0
        var i = 1
        while (i <= originalList.size) {
            var j = 1
            while (j <= originalList[0].size) {
                val cur = arr[i][j]

                if (cur == starSymbol) {
                    val numList = mutableSetOf<List<Int>>()
                    var isOver = false

                    for (p in i-1..i+1) {
                        for (q in j-1 .. j+1) {
                            if (arr[p][q][0].isDigit()) {
                                // find the num
                                val (num, l, r) = findNum(arr, p, q)
                                numList.add(listOf(num, l, r))
                                isOver = numList.size > 2
                            }
                        }
                        if (isOver) {
                            break
                        }
                    }
                    if (numList.size == 2) {
                        sum += numList.fold(1) { acc, s -> acc*s[0]}
                    }
                }
                j++
            }
            i++
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val r = part1(testInput)
    check(r == 4361)

    val r2 = part2(testInput)
    check(r2 == 467835)

    val r3 = part1(readInput("Day03_test_1"))
    check(r3 == 413)
    val r4 = part2(readInput("Day03_test_1"))
    check(r4 == 6756)

    val testInput2 = readInput("Day03_test_2")
    val r5 = part1(testInput2)
    check(r5 == 925)
    val r6 = part2(testInput2)
    check(r6 == 6756)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
