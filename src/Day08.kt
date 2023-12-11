fun main() {
    fun genMap(lst: List<String>): Map<String, List<String>> {
        val m = mutableMapOf<String, List<String>>()
        lst.map { s ->
            val (key, v) = s.split("=")
            m[key.trim()] = v.trim()
                .removeSurrounding("(", ")")
                .split(",").map { it.trim() }
        }
        return m
    }

    fun part1(input: List<String>): Int {
        val end = "ZZZ"
        val ins = input[0]
        val lst = input.slice(2 until input.size)
        val m = genMap(lst)

        var cur = "AAA"
        var steps = 0
        while (cur != end) {
            val dir = ins[steps % ins.length]
            cur = if (dir == 'L') m[cur]?.get(0).toString() else m[cur]?.get(1).toString()
            steps += 1
        }
        return steps
    }

    fun gcm(a: Long, b: Long): Long {
        return if (b.toInt() == 0) a else (gcm(b, a % b))
    }

    fun lcm(a: Long, b: Long): Long {
        return a / (gcm(a, b)) * b
    }

    fun lcm(vararg nums: Long): Long {
        return nums.reduce { acc, i -> lcm(acc, i) }
    }

    fun part2(input: List<String>): Long {
        val ins = input[0]
        val lst = input.slice(2 until input.size)
        val m = genMap(lst)

        val curList = m.keys.filter { it[it.length-1] == 'A' }
        val steps = MutableList(curList.size) { 0 }
        curList.forEachIndexed { index, s ->
            var cur = s
            while (cur[cur.length-1] != 'Z') {
                val dir = ins[steps[index] % ins.length]
                cur = if (dir == 'L') m[cur]?.get(0).toString() else m[cur]?.get(1).toString()
                steps[index] += 1
            }
        }

        val longLst = steps.map { s -> s.toLong() }.toLongArray()
        // TODO: how to calculate Long LCM?
        return lcm(*longLst)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day08_test")
//    check(part1(testInput) == 2)
    check(part2(testInput) == 6.toLong())

    val input = readInput("input/Day08")
    part1(input).println()
    part2(input).println()
}
