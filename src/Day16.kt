import java.util.*

fun main() {
    val day = "16"

    fun part1(input: List<String>): Int {
        val arr = input.toCharArray2()
        val arr2 = input.toCharArray2()
        var (m, n) = arr.size2()


        val a2 = Array(m) { Array(n) {0} }
        // Left, right, up, down
        val dir = listOf(
            mutableListOf(0, -1), mutableListOf(0, 1),
            mutableListOf(-1, 0), mutableListOf(1, 0),
        )

        val beamPos: Queue<MutableList<MutableList<Int>>> = LinkedList()
        beamPos.add(mutableListOf(mutableListOf(0, 0), dir[1]))

        fun ls(d: MutableList<Int>, dir: List<MutableList<Int>>): MutableList<Int> {
            val idx = dir.indexOf(d)
            return dir[dir.size-1 - idx]
        }
        fun rs(d: MutableList<Int>, dir: List<MutableList<Int>>): MutableList<Int> {
            val idx = dir.indexOf(d)
            return dir[(idx + 2) % dir.size]
        }
        val DIR = charArrayOf('<', '>', '^', 'v')

//        m=8
//        n=5

        // TODO: figure out how to end loop
        while (beamPos.isNotEmpty()) {
            var cur = beamPos.remove()
            var (p, d) = cur
            var (i, j) = p
//            println("i=$i, j=$j, dir=$d")
            while (i in 0..<m && j in 0..<n) {
                // TODO:
                if (a2[i][j] == 0) a2[i][j] += 1

                when (arr[i][j]) {
                    '.' -> {
                        if (DIR.contains(arr2[i][j])) {
                            arr2[i][j] = '2'
                        } else {
                            arr2[i][j] = DIR[dir.indexOf(d)]
                        }
                        i += d[0]
                        j += d[1]
                    }
                    '/' -> {
                        d = ls(d, dir)
                        i += d[0]
                        j += d[1]
                    }
                    '\\' -> {
                        d = rs(d, dir)
                        i += d[0]
                        j += d[1]
                    }
                    '|' -> {
                        if (d[0] == 0) {
                            val d0 = ls(d, dir)
                            val d1 = rs(d, dir)
                            d = d0
                            beamPos.add(mutableListOf(mutableListOf(i, j), d1))
                        }
                        i += d[0]
                        j += d[1]
                    }
                    '-' -> {
                        if (d[1] == 0) {
                            val d0 = ls(d, dir)
                            val d1 = rs(d, dir)
                            d = d0
                            beamPos.add(mutableListOf(mutableListOf(i, j), d1))
                        }
                        i += d[0]
                        j += d[1]
                    }
                }
            }
        }

        print2DList(a2.toList())
        print2DCharArray(arr2)

        return a2.sumOf { row ->
            row.count { it == 1 }
        }

    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
    check(part1(testInput) == 46)

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2(input).println()
}
