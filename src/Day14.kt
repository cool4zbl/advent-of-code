fun main() {
    val day = "14"

    // TODO: use transpose?
    fun west(a: Array<CharArray>): Array<CharArray> {
        val arr = a.clone()
        val (m, n) = arr.size2()
        for (i in 0..<m) for (j in 0..<n) {
            if (arr[i][j] == 'O') {
                var k = j
                while (k > 0 && arr[i][k-1] == '.') k--
                arr[i][j] = '.'
                arr[i][k] = 'O'
            }
        }
        return arr
    }
    fun east(a: Array<CharArray>): Array<CharArray> {
        val arr = a.clone()
        val (m, n) = arr.size2()

        for (i in 0..<m) for (j in 0..<n) {
            if (arr[i][j] == '.') {
                var k = j
                while (k >0 && arr[i][k-1] == 'O') k--

                arr[i][j] = 'O'
                arr[i][k] = '.'
            }
        }
        return arr
    }
    fun north(a: Array<CharArray>): Array<CharArray> {
        val arr = a.clone()
        val (m, n) = arr.size2()
        for (j in 0..<n) for (i in 0..<m) {
            if (arr[i][j] == 'O') {
                var k = i
                while (k > 0 && arr[k - 1][j] == '.') k--
                arr[i][j] = '.'
                arr[k][j] = 'O'
            }
        }
        return arr
    }
    fun south(a: Array<CharArray>): Array<CharArray> {
        var arr = a.clone()
        arr = transpose(arr)
        arr = east(arr)
        arr = transpose(arr)

        return arr
    }
    fun getSum(arr: Array<CharArray>): Int {
        val (m, n) = arr.size2()
        var sum = 0
        for (i in 0..<m) for (j in 0..<n) if (arr[i][j] == 'O') {
            sum += m - i
        }
        return sum
    }

    fun part1(input: List<String>): Int {
        var arr = input.toCharArray2()
        arr = north(arr)
        return getSum(arr)
    }

    fun round(a: Array<CharArray>): Array<CharArray> {
        var arr = a.clone()
        arr = north(arr)
        arr = west(arr)
        arr = south(arr)
        arr = east(arr)
        return arr
    }

    // start, cycle length, map
    fun findCycle(a: Array<CharArray>): Triple<Int, Int, MutableMap<Int, Pair<Int, Int>>> {
        val m = mutableMapOf<Int, Pair<Int, Int>>()
        var i = 1
        var arr = a
        while (true) {
            arr = round(arr)
            val sum = getSum(arr)
            val key = arr.contentDeepHashCode()
            if (m.containsKey(key)) {
                val (st) = m.getValue(key)
                return Triple(st,i - st, m)
            }
            m[key] = i to sum
            i++
        }
    }

    // TODO: find cycle once?
    fun part2(input: List<String>): Int {
        val arr = input.toCharArray2()
        val (start, len, mp) = findCycle(arr)
//        println("find cycle, s=$start, len=$len, mp=$mp")

        val t = 1_000_000_000
        val idx = ((t - start) % len) + start
        return mp.values.find { it.first == idx }!!.second
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day${day}_test")
    check(part1(testInput) == 136)
    check(part2(testInput) == 64)

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2(input).println()
}
