import java.util.LinkedList
import java.util.Queue

fun main() {
    val day = "21"

    fun part1(input: List<String>): Int {
        val a = input.toCharArray2()
        val (m, n) = a.size2()
        val (di, dj) = DIRS_RDLU()
        val (si, sj) = m/2 to n/2
        a[si][sj] = '.'

        var count = 0
        val queue: Queue<MutableSet<Pair<Int, Int>>> = LinkedList()

        // [[(si, sj)], [(si+1, sj), ... ], []]
        queue.add(mutableSetOf(si to sj))

        while (count < 64) {
            val curLevel = queue.remove()
            val nextLevel = mutableSetOf<Pair<Int, Int>>()
            curLevel.map {  cur ->
                val (i, j) = cur

                (0..3).map {
                    val (xi, xj) = di[it] + i to dj[it] + j
                    if (xi in 0..<m && xj in 0..<n && a[xi][xj] == '.') {
                        nextLevel.add(xi to xj)
                    }
                }
            }
            queue.add(nextLevel)
            count++
        }

        val r = queue.remove()
        r.map {
            a[it.first][it.second] = 'O'
        }
        print2DCharArray(a)
        return r.size
    }

    // TODO:
    fun part2(input: List<String>, steps: Int): Int {
        val a = input.toCharArray2()
        val (m, n) = a.size2()
        val (di, dj) = DIRS_RDLU()
        val (si, sj) = m/2 to n/2
        a[si][sj] = '.'

        var count = 0
        val queue: Queue<MutableSet<Pair<Int, Int>>> = LinkedList()

        // [[(si, sj)], [(si+1, sj), ... ], []]
        queue.add(mutableSetOf(si to sj))

        while (count < steps) {
            val curLevel = queue.remove()
            val nextLevel = mutableSetOf<Pair<Int, Int>>()
            curLevel.map {  cur ->
                val (i, j) = cur

                (0..3).map {
                    val (xi, xj) = di[it] + i to dj[it] + j
                    if (xi in 0..<m && xj in 0..<n && a[xi][xj] == '.') {
                        nextLevel.add(xi to xj)
                    }
                }
            }
            queue.add(nextLevel)
            count++
        }

        val r = queue.remove()
        r.map {
            a[it.first][it.second] = 'O'
        }
        print2DCharArray(a)
        return r.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
//    check(part1(testInput) == 16)
    check(part2(testInput, 6) == 16)
    check(part2(testInput, 10) == 50)

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2(input, 26501365).println()
}
