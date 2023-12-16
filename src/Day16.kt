import java.util.*

fun main() {
    val day = "16"

    fun part1(input: List<String>, ii: Int, ij: Int, id: Int): Int {
        val arr = input.toCharArray2()
        val (m, n) = arr.size2()

        // d -> di[ls[d]]
        val ls = listOf(3, 2, 1, 0)
        val rs = listOf(1, 0, 3, 2)

        fun genPos(i: Int, j: Int, d: Int): List<Int> {
            val (di, dj) = DIRS_RDLU()
            return listOf(i + di[d], j+dj[d])
        }

        val beams: Queue<Pair<List<Int>, Int>> = LinkedList()
        val visited = mutableSetOf<Pair<List<Int>, Int>>()

        beams.add(Pair(listOf(ii, ij), id))

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
                    beams.add((genPos(i, j, ls[d]) to ls[d]))
                }
                '\\' -> {
                    beams.add(genPos(i, j, rs[d]) to rs[d])
                }
                '|' -> {
                    // right or left
                    if (d == 0 || d == 2) {
                        beams.add((genPos(i, j, ls[d]) to ls[d]))
                        beams.add(genPos(i, j, rs[d]) to rs[d])
                    } else {
                        beams.add(genPos(i, j, d) to d)
                    }
                }
                '-' -> {
                    if (d == 1 || d == 3) {
                        beams.add(genPos(i, j, ls[d]) to ls[d])
                        beams.add(genPos(i, j, rs[d]) to rs[d])
                    } else {
                        beams.add(genPos(i, j, d) to d)
                    }
                }
                else -> beams.add(genPos(i, j, d) to d)
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
    val r = part1(testInput, 0, 0, 0)
    check(r == 46)
    check(part2(testInput) == 51)

    val input = readInput("input/Day${day}")
    part1(input, 0, 0, 0).println()
    part2(input).println()
}
