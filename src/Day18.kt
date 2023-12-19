import kotlin.math.abs

//--- Day 18: Lavaduct Lagoon ---

fun main() {
    val day = "18"

    fun part1(input: List<String>): Int {
        val dirs = listOf("R", "D", "L", "U")
        var (ci, cj) = 0 to 0
        var area = 0
        var ss = 0

        input.map {
            val (d, ns) = it.split(" ")
            val (di, dj) = DIRS_RDLU()

            val steps = ns.toInt()

            val ni = ci + di[dirs.indexOf(d)] * steps
            val nj = cj + dj[dirs.indexOf(d)] * steps

            area += (nj - cj) * (ni + ci)
            // or
//            area += ci * nj - ni * cj

            ss += steps
            ci = ni
            cj = nj
        }

        return abs(area) / 2 + ss / 2 + 1
    }

    fun part2(input: List<String>): Long {
        var (ci, cj) = 0 to 0
        var area = 0L
        var ss = 0L

        input.map {
            val (_, _, color) = it.split(" ")
            val hex = color.removeSurrounding("(#", ")")
            val ns = hex.dropLast(1).toInt(16)
            val d = hex.last().digitToInt()
            println("ns=$ns, d=$d")

            val (di, dj) = DIRS_RDLU()

            val ni = ci + di[d] * ns
            val nj = cj + dj[d] * ns

            area += (nj - cj) * (ni + ci).toLong()
            // or
//            area += ci * nj - ni * cj
            ss += ns
            ci = ni
            cj = nj
        }

        return abs(area) / 2 + ss / 2 + 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")

    check(part1(testInput) == 62)
    check(part2(testInput) == 952408144115)

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2(input).println()
}
