fun main() {
    val day = "15"

    fun hash(s: String): Int {
        return s.toCharArray().fold(0) { acc, c ->
            (acc + c.code) * 17 % 256
        }
    }

    fun part1(input: List<String>): Int {
        return input.map {
            row -> row.split(",").map { hash(it) }.sumOf { it }
        }.sumOf { it }
    }

    fun part2(input: List<String>): Int {
        /*
        {
            0: { rn: 1, cm: 2 },
            1: { qp: 3 }
        }
         */
        val m = mutableMapOf<Int, MutableMap<String, Int>>()

        input[0].split(",").map {
            if (it.contains("=")) {
                val (label, v) = it.split("=")
                val l = hash(label)
                if (m.containsKey(l)) {
                    m.getValue(l)[label] = v.toInt()
                } else {
                    m[l] = mutableMapOf(label to v.toInt())
                }
            } else {
                val (label, _) = it.split("-")
                val l = hash(label)
                m[l]?.remove(label)
            }
        }

        return m.entries.sumOf {
            box -> box.value.entries.withIndex().sumOf {
                (i, labels) -> (box.key + 1) * (i + 1) * (labels.value)
        }}
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
    check(part1(testInput) == 1320)

    val input = readInput("input/Day${day}")
    part1(input).println()

    check(part2(testInput) == 145)
    part2(input).println()
}
