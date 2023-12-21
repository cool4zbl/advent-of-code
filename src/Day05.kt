import kotlin.math.max
import kotlin.math.min

//--- Day 5: If You Give A Seed A Fertilizer ---

fun main() {
    val day = "05"

    fun getMatch(idx: Long, lst: List<String>) : Long {
        for (i in lst.indices) {
            val (d, s, r) = lst[i].split(" ").map { it.toLong() }
            if (idx in (s..<s+r)) {
                return d + idx-s
            }
        }
        return idx
    }

    fun part1(input: List<String>): Long {
        val (ss ) = groupListFromInput(input)[0]
        val seeds = ss.removePrefix("seeds: ").split(" ").map { it.toLong() }
        val mappings = groupListFromInput(input).drop(1).map { it.drop(1) }

        return seeds.minOf { s ->
            mappings.fold(s) { acc, strings -> getMatch(acc, strings) }
        }
    }

    fun part2(input: List<String>): Long {
        val ss = groupListFromInput(input)[0][0].removePrefix("seeds: ").split(" ").map { it.toLong() }
        val mappings = groupListFromInput(input).drop(1).map { it.drop(1) }

        var seeds = ss.chunked(2).map { listOf(it[0], it[1]+it[0]) }.toMutableList()

        mappings.forEach { maps ->
            val newRange = mutableListOf<List<Long>>()
            // find new ranges for each seeds' range of each mapping
            while (seeds.isNotEmpty()) {
                val (sL, sR) = seeds.removeAt(0)
                var mapped = false
                for (i in maps.indices) {
                    val s = maps[i]
                    val (des, src, r) = s.split(" ").map { it.toLong() }
                    val oL = max(sL, src)
                    val oR = min(sR, src + r-1)
                    // if there's overlapping between seed range and map range, translate
                    if (oL < oR) { // has overlap: sL < src+R-1 && sR > src
                        newRange.add(listOf(oL + des - src, oR + des - src))
                        if (sL < oL) seeds.add(listOf(sL, oL-1))
                        if (sR > oR) seeds.add(listOf(oR+1, sR))
                        // if found, find range for next seed
                        mapped = true
                        break
//                    } else {
//                        println("no overlap, oL=$oL > oR=$oR, s=$sL,$sR, m=$src,${src+R-1}")
                    }
                }
                // if no overlapping, means no need to translate, keep the same range
                if (!mapped) newRange.add(listOf(sL, sR))
            }
            newRange.sortBy { it[0] }
            seeds = newRange
        }

        return seeds.minOf { it[0] }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2(input).println()
}
