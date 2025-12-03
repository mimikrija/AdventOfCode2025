import kotlin.math.max

fun main() {
    val batteries = readInput("Day03")


    val part1 = batteries.sumOf { maxJoltage(it) }

    val part2 = batteries.sumOf { battery -> bla("", battery).toLong() }

    println("Part 1 solution is: $part1")
    println("Part 2 solution is: $part2")
//
//    assert(part1 == 53420042388)
//    assert(part2 == 69553832684)
}

private fun maxJoltage(battery: String): Int {
    val size = battery.length
    return (1 until size
            ).maxOf{ it ->
        val left = battery.slice(0 until it)
            .map{p -> p.toString().toInt()}
            .max()
            .toString()
        val right = battery.slice(it until size)
            .map{p-> p.toString().toInt()}
            .max()
            .toString()
        (left + right).toInt()
    }
}


private fun bla(final: String = "", rest: String, currentMax: Long = 0): String {
    val size = rest.length
    val stop = 12 - final.length - 1
    if (final.length == 12) return final
    return (0 until size - stop).map { pos ->
        val left = rest[pos]
        bla(final + left, rest.slice(pos + 1 until size))
    }.maxOf { it }

}
