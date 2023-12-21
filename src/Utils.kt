import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

typealias IntArray2 = Array<IntArray>
typealias CharArray2 = Array<CharArray>

/**
 * (Row, Column) coordinate in 2D grid.
 */
data class P2(val i: Int, val j: Int)

fun List<String>.toIntArray2() = Array(size) {
    it -> get(it).split("").filter { it.isNotBlank() }.map { it.toInt() }.toIntArray()
}

fun IntArray2.size2(): P2 {
    val n = size
    val m = get(0).size
    for (i in 1..<n) require(get(i).size == m) { "Row $i has size ${get(i)}, but expected $m"}
    return P2(n, m)
}

fun List<String>.toCharArray2() = Array(size) { get(it).toCharArray() }

fun CharArray2.size2(): P2 {
    val n = size
    val m = get(0).size
    for (i in 1..<n) require(get(i).size == m) { "Row $i has size ${get(i)}, but expected $m"}
    return P2(n, m)
}

// right, down, left, up
fun DIRS_RDLU(): Pair<IntArray, IntArray> = Pair(
    intArrayOf(0, 1, 0, -1),
    intArrayOf(1, 0, -1, 0),
)

fun transpose(arr: CharArray2): CharArray2 {
    if (arr.isEmpty() || arr.all { it.isEmpty() }) return arrayOf(charArrayOf())
    return arr[0].indices.map {
            col -> arr.map { row -> row[col] }.joinToString("")
    }.toCharArray2()
}

fun print2DCharArray(arr: CharArray2) {
    arr.map { c -> println(c) }
}

fun print2DList(arr: Array<IntArray>) {
    arr.forEach { row ->
        println(row.joinToString(", "))
    }
}

fun greater(a: String, b: String): Boolean {
    // compare two positive numbers
    if (a.length > b.length) return true
    if (a.length < b.length) return false

    val aList = a.split("").filter { it != "" }.map { it.toInt() }
    val bList = b.split("").filter { it!= "" }.map { it.toInt() }
    var carry = 0

    val s = ArrayList<Int>()

    for (i in aList.size-1 downTo 0) {
        val diff = aList[i] + carry - bList[i]
        carry = if (diff < 0) {
            s.add(10 + diff)
            -1
        } else {
            s.add(diff)
            0
        }
    }
    return s.count { it != 0 } > 0 && carry >= 0
}


fun groupListFromInput(input: List<String>): List<List<String>> {
    val groups = mutableListOf<List<String>>()
    val currentGroup = mutableListOf<String>()

    input.forEach { line ->
        if (line.isBlank()) { // Check for an empty line
            if (currentGroup.isNotEmpty()) {
                groups.add(currentGroup.toList())
                currentGroup.clear()
            }
        } else {
            currentGroup.add(line)
        }
    }

    // Add the last group if it's not empty
    if (currentGroup.isNotEmpty()) {
        groups.add(currentGroup.toList())
    }

    return groups
}
