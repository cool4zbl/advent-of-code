//--- Day 16: The Floor Will Be Lava ---

import java.util.*

fun main() {
    val day = "16"

    fun part1(input: List<String>, ii: Int, ij: Int, dir: Int): Int {
        val arr = input.toCharArray2()
        val (m, n) = arr.size2()

        // d -> di[ls[d]]
        val ls = listOf(3, 2, 1, 0)
        val rs = listOf(1, 0, 3, 2)

        val beams: Queue<Pair<List<Int>, Int>> = LinkedList()
        val visited = mutableSetOf<Pair<List<Int>, Int>>()

        fun genPos(i: Int, j: Int, d: Int) {
            val (di, dj) = DIRS_RDLU()

            beams.add(listOf(i + di[d], j+dj[d]) to d)
        }

        beams.add((listOf(ii, ij) to dir))

        while (beams.isNotEmpty()) {
            val cur = beams.remove()
            val (p, d) = cur
            val (i, j) = p

            if (visited.contains(cur) || i !in 0..<m || j !in 0..<n) {
                continue
            }
            visited.add(listOf(i, j) to d)

            when (arr[i][j]) {
                '/' -> {
                    genPos(i, j, ls[d])
                }
                '\\' -> {
                    genPos(i, j, rs[d])
                }
                '|' -> {
                    // right or left
                    if (d == 0 || d == 2) {
                        genPos(i, j, ls[d])
                        genPos(i, j, rs[d])
                    } else {
                        genPos(i, j, d)
                    }
                }
                '-' -> {
                    if (d == 1 || d == 3) {
                        genPos(i, j, ls[d])
                        genPos(i, j, rs[d])
                    } else {
                        genPos(i, j, d)
                    }
                }
                else -> genPos(i, j, d)
            }
        }
        return visited.map { it.first }.toSet().size
    }

    fun part2(input: List<String>): Int {
        val arr = input.toCharArray2()
        val (m, n) = arr.size2()

        return listOf(
            (0..n).maxOf { part1(input, 0, it, 1) },
            (0..n).maxOf { part1(input, m-1, it, 3) },
            (0..m).maxOf { part1(input, it, 0, 0) },
            (0..m).maxOf { part1(input, it, n-1, 2) },
        ).max()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
    check(part1(testInput, 0, 0, 0) == 46)
    check(part2(testInput) == 51)

    val input = readInput("input/Day${day}")
    part1(input, 0, 0, 0).println()
    part2(input).println()
}
