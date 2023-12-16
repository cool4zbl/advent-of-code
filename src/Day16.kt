import java.util.*

fun main() {
    val day = "16"

    data class P3(val i:Int, val j: Int, val d: Int)

    fun part1(input: List<String>, ii: Int, ij: Int, id: Int): Int {
        val arr = input.toCharArray2()
        val (m, n) = arr.size2()

        val a2 = Array(m) { Array(n) {0} }
        // d -> di[ls[d]]
        val ls = listOf(3, 2, 1, 0)
        val rs = listOf(1, 0, 3, 2)

        val beams: Queue<P3> = LinkedList()
        val visited = Array(m) { Array(n) { BooleanArray(4) } }

        fun gen(i: Int, j: Int, d: Int) {
            val (di, dj) = DIRS_RDLU()
            val i1 = i + di[d]
            val j1 = j + dj[d]
            if (i1 !in 0..<m || j1 !in 0..<n) return

            if (visited[i1][j1][d]) return
            visited[i1][j1][d] = true

            a2[i1][j1] += 1

            beams.add(P3(i1, j1, d))
        }

        gen(ii, ij, id)

        while (beams.isNotEmpty()) {
//            println("$beams")
            val cur = beams.remove()
            val (i, j, d) = cur

            when (arr[i][j]) {
                '/' -> {
                    gen(i, j, ls[d])
                }
                '\\' -> {
                    gen(i, j, rs[d])
                }
                '|' -> {
                    // right or left
                    if (d == 0 || d == 2) {
                        gen(i, j, ls[d])
                        gen(i, j, rs[d])
                    } else {
                        gen(i, j, d)
                    }
                }
                '-' -> {
                    if (d == 1 || d == 3) {
                        gen(i, j, ls[d])
                        gen(i, j, rs[d])
                    } else {
                        gen(i, j, d)
                    }
                }
                else -> gen(i, j, d)
            }
        }

        return a2.sumOf { row -> row.count { it > 0 } }

    }

    fun part2(input: List<String>): Int {
        val arr = input.toCharArray2()
        val (m, n) = arr.size2()

        return listOf(
            (0..n).maxOf { part1(input, -1, it, 1) },
            (0..n).maxOf { part1(input, m, it, 3) },
            (0..m).maxOf { part1(input, it, -1, 0) },
            (0..m).maxOf { part1(input, it, n, 2) },
        ).max()

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
    val r = part1(testInput, 0, -1, 0)
    check(r == 46)
    check(part2(testInput) == 51)

    val input = readInput("input/Day${day}")
    part1(input, 0, -1, 0).println()
    part2(input).println()
}
