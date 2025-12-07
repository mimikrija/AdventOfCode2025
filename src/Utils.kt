import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.absoluteValue

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

fun readInputNoTrim(name: String) = Path("src/$name.txt").readText().lines()


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

data class Coordinate(val x: Int, val y: Int) {
    fun manhattanDistanceTo(other: Coordinate) = (x - other.x).absoluteValue + (y - other.y).absoluteValue

    operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
}
fun neighbors(
    coordinate: Coordinate,
    gridSize: Int,
    includeDiagonals: Boolean = false,
    isInfinite: Boolean = false
): List<Coordinate> {
    val main = listOf(coordinate.copy(y = coordinate.y + 1), coordinate.copy(y = coordinate.y - 1),
        coordinate.copy(x = coordinate.x + 1), coordinate.copy(x = coordinate.x - 1))

    val diagonals = listOf(
        coordinate.copy(x = coordinate.x + 1, y = coordinate.y + 1),
        coordinate.copy(x = coordinate.x - 1, y = coordinate.y - 1),
        coordinate.copy(x = coordinate.x + 1, y = coordinate.y - 1),
        coordinate.copy(x = coordinate.x - 1, y = coordinate.y + 1)
    )

    return (if (includeDiagonals) main + diagonals else main)
        .filter{ if(isInfinite) true else (it.x in 0 until gridSize && it.y in 0 until gridSize)}
}



