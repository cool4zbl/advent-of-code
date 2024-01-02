import java.util.*

//https://github.com/villuna/aoc23/wiki/A-Geometric-solution-to-advent-of-code-2023,-day-21

fun main() {
    fun solve(input: List<String>, ss: Int): Long {
        val a = input.toCharArray2()
        val (m, n) = a.size2()
        val queue = ArrayDeque<Pair<Int, P>>()
        val visited = mutableMapOf<P, Int>()

        queue.add(Pair(0, P(m/2, n/2)))
        visited[P(m/2, n/2)] = 0

        while (queue.isNotEmpty()) {
            val (steps, coord) = queue.removeFirst()

            val (di, dj) = DIRS_RDLU()
            for (k in 0..3) {
                val (xi, xj) = di[k] + coord.i to dj[k] + coord.j

                if (xi !in 0..<m || xj !in 0..<n || a[xi][xj] == '#' || visited.containsKey(P(xi, xj))) {
                    continue
                }
                queue.add(Pair(steps+1, P(xi, xj)))
                visited[P(xi, xj)] = steps+1
            }
        }

        val p1 = visited.values.count { it % 2 == 0 && it <= 64 }
        p1.println()

        val evenCorners = visited.values.count { it % 2 == 0 && it > 65 }
        val oddCorners = visited.values.count { it % 2 == 1 && it > 65 }

        val width = (ss / m).toLong()
        check(width == 202300L)

        val even = width * width
        val odd = (width + 1) * (width + 1)

        return odd * visited.values.count { it % 2 == 1 } +
                even * visited.values.count { it % 2 == 0 } -
                ((width + 1) * oddCorners) +
                (width * evenCorners)
    }

    val day = "21"

    val input = readInput("input/Day${day}")

    solve(input, 26_501_365).println()
}
