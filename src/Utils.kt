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

fun print2DList(arr: List<List<String>>) {
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

typealias CharArray2 = Array<CharArray>

fun List<String>.toCharArray2() = Array(size) { get(it).toCharArray() }
