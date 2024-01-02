import java.util.LinkedList
import java.util.Queue
import kotlin.math.pow

fun main() {
    val day = "21"

    val (di, dj) = DIRS_RDLU()

    fun part1(input: List<String>): Int {
        val a = input.toCharArray2()
        val (m, n) = a.size2()
        val (si, sj) = m/2 to n/2
        a[si][sj] = '.'

        var count = 0
        val queue: Queue<MutableSet<P>> = LinkedList()

        // [[(si, sj)], [(si+1, sj), ... ], []]
        queue.add(mutableSetOf(P(si, sj)))

        while (count < 64) {
            val curLevel = queue.remove()
            val nextLevel = mutableSetOf<P>()
            curLevel.forEach { cur ->
                val (i, j) = cur

                (0..3).forEach {
                    val (xi, xj) = di[it] + i to dj[it] + j
                    if (xi in 0..<m && xj in 0..<n && a[xi][xj] == '.') {
                        nextLevel.add(P(xi, xj))
                    }
                }
            }
            queue.add(nextLevel)
            count++
        }

        val r = queue.remove()
        r.map { a[it.i][it.j] = 'O' }
        return r.size
    }

    fun part2(input: List<String>, steps: Int): Long {
        val a = input.toCharArray2()
        val (m, n) = a.size2()
        val (si, sj) = m/2 to n/2
        a[si][sj] = '.'

        var count = 0
        val queue = ArrayDeque<MutableSet<P>>()

        queue.add(mutableSetOf(P(si, sj)))

        while (count < steps) {
            val curLevel = queue.removeFirst()
            val nextLevel = mutableSetOf<P>()

            curLevel.map { cur ->
                val (i, j) = cur
                (0..3).map { d ->
                    val (xi, xj) = di[d] + i to dj[d] + j
                    var (vi, vj) = xi to xj
                    if (xi < 0) {
                        //val result = ((dividend % divisor) + divisor) % divisor
                        vi = ((xi % m) + m) % m
                    }
                    if (xi >= m) {
                        vi = xi % m
                    }
                    if (xj < 0) {
                        vj = ((xj % n) + n) % n
                    }
                    if (xj >= n) {
                        vj = xj % n
                    }
                    if (a[vi][vj] == '.') {
                        nextLevel.add(P(xi, xj))
                    }
                }
            }
            queue.add(nextLevel)
            count++
        }

        val r = queue.removeFirst()
        return r.size.toLong()
    }


    fun part2_geo(input: List<String>, steps: Int): Long {
        val a = input.toCharArray2()
        val (m, n) = a.size2()
        val (si, sj) = m/2 to n/2
        a[si][sj] = '.'

        fun fill(si: Int, sj: Int, steps: Int): Long {
            val res = mutableSetOf<P>()
            val visited = mutableSetOf<P>()
            visited.add(P(si, sj))

            val queue = ArrayDeque<Q>()
            queue.add(Q(si, sj, steps))

            while (queue.isNotEmpty()) {
                val (i, j, s) = queue.removeFirst()

                if (s % 2 == 0) res.add(P(i, j))
                if (s == 0) continue

                for (k in 0..3) {
                    val (xi, xj) = di[k] + i to dj[k] + j

                    if (xi !in 0..<m || xj !in 0..<n || a[xi][xj] == '#' || visited.contains(P(xi, xj))) {
                        continue
                    }
                    visited.add(P(xi, xj))
                    queue.add(Q(xi, xj, s - 1))
                }
            }
            return res.size.toLong()
        }
        val w = steps / m - 1
        val odd = (w / 2 * 2 + 1).toDouble().pow(2).toLong()
        val even = ((w + 1) / 2 * 2).toDouble().pow(2).toLong()

        val ops = fill(si, sj, m * 2 + 1)
        val eps = fill(si, sj, m * 2)

        check(m == n)

        val ct = fill(m -1, sj, m - 1)
        val cr = fill(si, 0, m - 1)
        val cb = fill(0, sj, m - 1)
        val cl = fill(si, m-1, m - 1)

        val small_tr = fill(m-1, 0, m/2-1)
        val small_tl = fill(m-1, m-1, m/2-1)
        val small_br = fill(0, 0, m/2-1)
        val small_bl = fill(0, m-1, m/2-1)

        val large_tr = fill(m-1, 0, m * 3 /2-1)
        val large_tl = fill(m-1, m-1, m * 3 /2-1)
        val large_br = fill(0, 0, m*3/2-1)
        val large_bl = fill(0, m-1, m*3/2-1)

        return odd * ops + even * eps +
                ct + cr + cb + cl +
                (w+1) * (small_tr + small_tl + small_br + small_bl) +
                w * (large_tr + large_tl + large_br + large_bl)

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
    check(part2_geo(testInput, 6) == 16L)
    check(part2_geo(testInput, 10) == 50L)

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2_geo(input, 26_501_365).println()
}
