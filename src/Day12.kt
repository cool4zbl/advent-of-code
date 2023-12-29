import kotlin.collections.HashMap

//--- Day 12: Hot Springs ---

fun main() {
    val day = "12"

    // generate `???` with `#` & `.`
    fun combination(ans: MutableList<String>, s: String) {
        if (!s.contains('?')) {
            ans.add(s)
            return
        }
        for (i in listOf("#", ".")) {
            combination(ans, s.replaceFirst("?", i))
        }
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { ss ->
            val (str, ns0) = ss.split(" ")
            val ns = ns0.split(",").map { it.toInt() }.toIntArray()
            val ans = mutableListOf<String>()
            combination(ans, str)

            ans.filter { it ->
                val s1 = it.split(".")
                    .filter { it.isNotBlank() }
                    .map { it.length }.toIntArray()
                s1.contentEquals(ns)
            }.size.toLong()
        }
    }
    fun cnt(s: String, ns: IntArray, i: Int, j: Int, u: Int, m: HashMap<Triple<Int, Int, Int>, Long>): Long {
        if (i >= s.length) return if (j >= ns.size || j == ns.size - 1 && u == ns[j]) 1 else 0

        val key = Triple(i, j, u)
        m[key]?.let { return it }
//                if (dp[i][j][u] >= 0) return dp[i][j][u]

        val c = s[i]

        var res = 0L
        if (c == '.' || c == '?') {
            if (u > 0 && ns[j] == u) {
                res += cnt(s, ns, i + 1, j + 1, 0, m)
            } else if (u == 0) {
                res += cnt(s, ns, i + 1, j, 0, m)
            }
        }
        if (c == '#' || c == '?') {
            if (j < ns.size && u < ns[j]) {
                res += cnt(s, ns, i + 1, j, u + 1, m)
            }
        }
//                dp[i][j][u] = res
        m[key] = res

        return res
    }

    fun part1r(input: List<String>): Long {

        return input.sumOf { ss ->
            val (str, ns0) = ss.split(" ")
            val ns = ns0.split(",").map { it.toInt() }.toIntArray()

//            val dp = Array(str.length) {
//                Array(ns.size + 1) { j -> LongArray(ns.getOrElse(j) { 0 } + 1) { -1 } }
//            }
            val m = HashMap<Triple<Int, Int, Int>, Long>()
            val a = cnt(str, ns, 0, 0, 0, m)
            m.println()
            a
        }
    }

    fun part2(input: List<String>): Long {

        return input.sumOf { row ->
            val (str, ns0) = row.split(" ")
            val ns = ns0.split(",").map { it.toInt() }
            val ss = Array(5) { str }.joinToString("?")
            val g = buildList { repeat(5) { addAll(ns)} }.toIntArray()

            val m = HashMap<Triple<Int, Int, Int>, Long>()
            cnt(ss, g, 0, 0, 0, m)
        }

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
    val r1 = part1r(testInput)
    check(r1 == 21L)

    val r = part2(testInput)
    check(r == 525152L)

    val input = readInput("input/Day${day}")
    part1r(input).println()
    part2(input).println()
}
