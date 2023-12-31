import kotlin.collections.ArrayDeque
import kotlin.math.abs

//--- Day 10: Pipe Maze ---

fun main() {
    val day = "10"

    fun part1(input: List<String>): Int {
        val a = input.toCharArray2()
        val (m, n) = a.size2()
        val (di, dj) = DIRS_RDLU()
        var (si, sj) = 0 to 0

        for (i in 0..<m) for(j in 0 ..<n) if (a[i][j] == 'S') { si = i; sj = j }

        return (0..3).maxOf {
            val queue = ArrayDeque<Q>()
            val visited = mutableSetOf<P>()

            fun enq(i: Int, j: Int, d: Int) {
                queue.add(Q(i + di[d], j+dj[d], d))
            }

            visited.add(P(si, sj))
            enq(si, sj, it)

            while (queue.isNotEmpty()) {
                val cur = queue.removeFirst()
                val (i, j, d) = cur

                if (visited.contains(P(i,j))) break

                if (i !in 0..<m || j !in 0..<n) continue

                visited.add(P(i, j))
                val p0 = listOf(-1, 1, -1, 3) // |
                val p1 = listOf(0, -1, 2, -1) // -
                val p2 = listOf(-1, 0, 3, -1) // L
                val p3 = listOf(3, 2, -1, -1) // J
                val p4 = listOf(1, -1, -1, 2) // 7
                val p5 = listOf(-1, -1, 1, 0) // F

                when (a[i][j]) {
                    '|' -> if (p0[d] != -1) enq(i, j, p0[d])
                    '-' -> if (p1[d] != -1) enq(i, j, p1[d])
                    'L' -> if (p2[d] != -1) enq(i, j, p2[d])
                    'J' -> if (p3[d] != -1) enq(i, j, p3[d])
                    '7' -> if (p4[d] != -1) enq(i, j, p4[d])
                    'F' -> if (p5[d] != -1) enq(i, j, p5[d])
                }
            }

            visited.size/2
        }
    }

    fun part2(input: List<String>): Int {
        val a = input.toCharArray2()
        val (m, n) = a.size2()
        val (di, dj) = DIRS_RDLU()
        var (si, sj) = 0 to 0

        for (i in 0..<m) for(j in 0 ..<n) if (a[i][j] == 'S') { si = i; sj = j }

        var loop = mutableSetOf<P>()
        var loopFound = false
        for (k in 0..3) {
            if (loopFound) break

            val queue = ArrayDeque<Q>()
            val visited = mutableSetOf<P>()

            fun enq(i: Int, j: Int, d: Int) {
                queue.add(Q(i + di[d], j+dj[d], d))
            }

            visited.add(P(si, sj))
            enq(si, sj, k)

            while (queue.isNotEmpty()) {
                val cur = queue.removeFirst()
                val (i, j, d) = cur

                if (visited.contains(P(i,j))) {
                    loopFound = true
                    loop = visited
                    break
                }

                if (i !in 0..<m || j !in 0..<n) continue

                visited.add(P(i, j))
                val p0 = listOf(-1, 1, -1, 3) // |
                val p1 = listOf(0, -1, 2, -1) // -
                val p2 = listOf(-1, 0, 3, -1) // L
                val p3 = listOf(3, 2, -1, -1) // J
                val p4 = listOf(1, -1, -1, 2) // 7
                val p5 = listOf(-1, -1, 1, 0) // F

                when (a[i][j]) {
                    '|' -> if (p0[d] != -1) enq(i, j, p0[d])
                    '-' -> if (p1[d] != -1) enq(i, j, p1[d])
                    'L' -> if (p2[d] != -1) enq(i, j, p2[d])
                    'J' -> if (p3[d] != -1) enq(i, j, p3[d])
                    '7' -> if (p4[d] != -1) enq(i, j, p4[d])
                    'F' -> if (p5[d] != -1) enq(i, j, p5[d])
                }
            }
        }
        val v = loop.toList()
        var (ci, cj) = v[0]
        var area = 0
        val ss = v.size

        // COPY FROM day18, shoelace & pick theorem
        for (k in 1..<v.size) {
            val (ni, nj) = v[k]
            area += (nj - cj) * (ni + ci)
            // or
//            area += ci * nj - ni * cj
            ci = ni
            cj = nj
        }
        return abs(area) / 2 + ss / 2 + 1 - v.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test_2")
    check(part2(testInput) == 4)

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2(input).println()
}
