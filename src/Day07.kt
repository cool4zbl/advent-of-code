import kotlin.math.pow

//--- Day 7: Camel Cards ---

fun main() {
    val day = "07"

//  6  Five of a kind, where all five cards have the same label: AAAAA
//  5  Four of a kind, where four cards have the same label and one card has a different label: AA8AA
//  4  Full house, where three cards have the same label, and the remaining two cards share a different label: 23332
//  3  Three of a kind, where three cards have the same label, and the remaining two cards are each different from any other card in the hand: TTT98
//  2  Two pair, where two cards share one label, two other cards share a second label, and the remaining card has a third label: 23432
//  1  One pair, where two cards share one label, and the other three cards have a different label from the pair and each other: A23A4
//  0  High card, where all cards' labels are distinct: 23456
    fun getLevel(gg: Map<Int, List<List<String>>>): Int {
        return when (gg.keys.max()) {
            5 -> 6
            4 -> 5
            3 -> {
                if (gg.containsKey(2)) 4
                else 3
            }
            2 -> {
                if (gg.getValue(2).size == 2) 2
                else 1
            }
            else -> 0
        }
    }

    fun groupBySize(s: String): Map<Int, List<List<String>>>{
        return s.split("").filter { it.isNotBlank() }.groupBy { it }.values.groupBy { it.size }
    }

    fun compareByStrength(ra: CharArray): Comparator<String> {
        val base = ra.size.toDouble()
        val mp = doubleArrayOf(1.0, base, base.pow(2), base.pow(3), base.pow(4))
        return compareBy<String> {
            it.foldIndexed(0.0) { idx, acc, c -> acc +ra.indexOf(c) * (mp[mp.size-1-idx]) }
        }.thenBy { it }
    }


    fun part1(input: List<String>): Int {
        // { [string]: [bid, rank] }
        val m = mutableMapOf<String, MutableList<Int>>()
        val a = Array(7) { mutableListOf<String>() }

        input.map { ss ->
            val (s, bid) = ss.split(" ")

            m[s] = mutableListOf(bid.toInt())

            val gg = groupBySize(s)

            val l = when (gg.keys.max()) {
                5 -> 6
                4 -> 5
                3 -> {
                    if (gg.containsKey(2)) 4
                    else 3
                }
                2 -> {
                    if (gg.getValue(2).size == 2) 2
                    else 1
                }
                else -> 0
            }

            a[l].add(s)
        }

        var rank = 0
        val ra = charArrayOf( 'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2' ).reversedArray()
        a.map { strings -> strings.sortWith(compareByStrength(ra)) }

        a.filter { it.isNotEmpty() }.map {
            strings ->  strings.map {
                rank++
                m[it]?.add(rank)
            }
        }

        return m.values.fold(0) { acc, ints -> acc + ints[0] * ints[1] }
    }

    fun part2(input: List<String>): Int {
        // { [string]: [bid, rank] }
        val m = mutableMapOf<String, MutableList<Int>>()
        val a = Array(7) { mutableListOf<String>() }

        input.map { ss ->
            val (s, bid) = ss.split(" ")

            m[s] = mutableListOf(bid.toInt())

            val g = s.split("").filter { it.isNotBlank() }.groupBy { it }
            val gg = groupBySize(s)

            var l = getLevel(gg)

            if (g.containsKey("J") && g.getValue("J").size != 5) {
                l = g.keys.filter{ it != "J" }.map {
                    s.replace("J", it)
                }.maxOf { getLevel(groupBySize(it)) }
            }

            a[l].add(s)
        }

        var rank = 0
        val ra = charArrayOf( 'A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J' ).reversedArray()

        a.map { strings -> strings.sortWith(compareByStrength(ra)) }

        a.filter { it.isNotEmpty() }
            .map { strings ->
                strings.map {
                    rank++
                    m[it]?.add(rank)
                }
            }

        return m.values.fold(0) { acc, ints -> acc + ints[0] * ints[1] }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2(input).println()
}
