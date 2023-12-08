fun main() {
    val regex = """[a-zA-Z]""".toRegex()

    fun part1(input: List<String>): Int {
        return input.map{
            regex.replace(it, "")
        }.map {
            "${it[0]}${it[it.length - 1]}"
        }.sumOf { it.toInt() }
    }

    fun matchFunc(toReplace: String): String {
        if (!regex.containsMatchIn(toReplace)) {
            return toReplace
        }
        var r = ""
        var it = toReplace
        val digitRegex = """(one|two|three|four|five|six|seven|eight|nine)""".toRegex()
        val capReg = """[otfsen]""".toRegex()
        while (digitRegex.containsMatchIn(it)) {
            val digit = capReg.find(it)!!.value
            when (digit) {
                "o" -> {
                    if (it.contains("one")) {
                        it = it.replace("one", "")
                        r += "1"
                    } else {
                        it = it.replace(digit, "")
                    }
                }
                "t" -> {
                    if (it.contains("two")) {
                        it = it.replace("two", "")
                        r += "2"
                    } else if (it.contains("three")) {
                        it = it.replace("three", "3")
                        r += "3"
                    } else {
                        it = it.replace(digit, "")
                    }
                }
                "f" -> {
                    if (it.contains("four")) {
                        it = it.replace("four", "")
                        r += "4"
                    } else if (it.contains("five")) {
                        it = it.replace("five", "")
                        r += "5"
                    } else {
                        it = it.replace(digit, "")
                    }
                }
                "s" -> {
                    if (it.contains("six")) {
                        it = it.replace("six", "")
                        r += "6"
                    } else if (it.contains("seven")){
                        it = it.replace("seven", "")
                        r += "7"
                    } else {
                        it = it.replace(digit, "")
                    }
                }
                "e" -> {
                    if (it.contains("eight")) {
                        it = it.replace("eight", "")
                        r += "8"
                    } else {
                        it = it.replace(digit, "")
                    }
                }

                "n" -> {
                    if (it.contains("nine")) {
                        it = it.replace("nine", "")
                        r += "9"
                    } else {
                        it = it.replace(digit, "")
                    }
                }
            }
        }
        return r
    }

    fun part2(input: List<String>): Int {
        val f = {
            row: String ->
            Regex("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)")
                .split(row).joinToString("") {
                    matchFunc(it)
                }
        }
        return part1(input.map { f(it) })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val testInput2 = readInput("Day01_test_2")

    val r2 = part2(testInput2)
    check(r2 == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
