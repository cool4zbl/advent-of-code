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

    // No answer
    fun part2(input: List<String>): Int {
        val ins = input[0]
        val lst = input.slice(2 until input.size)
        val m = genMap(lst)
        var curList = m.keys.filter { it[it.length-1] == 'A' }

        var steps = 0
        while (!curList.all { it[it.length-1] == 'Z' }) {
            val dir = ins[steps % ins.length]
            curList = curList.map { cur ->
                val curNode = if (dir == 'L') m[cur]?.get(0).toString() else m[cur]?.get(1).toString()
                curNode
            }
            steps += 1
        }
        return steps
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day08_test")
//    check(part1(testInput) == 2)
    check(part2(testInput) == 6)

    val input = readInput("input/Day08")
    part1(input).println()
    part2(input).println()
}
