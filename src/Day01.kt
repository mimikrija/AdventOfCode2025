import kotlin.math.abs

fun main() {
    val rawInput = readInput("Day01")

    val instructions =
        rawInput
            .map { text ->
                text
                    .slice(0 until 1) to text.slice(1 until text.length).toInt()
            }

    val zeroes = instructions
        .fold(initial = 50 to 0) { (position, count), (direction, distance) ->
            val increment = when (direction) { "L" -> -distance "R" -> distance else -> 0 }
            (increment + position).mod(100) to if (position == 0) count + 1 else count
        }

    val zeroPasses = instructions
        .fold(initial = 50 to 0) { (position, count), (direction, distance) ->
            val increment = when (direction) { "L" -> -distance "R" -> distance else -> 0 }
            val previous = position
            val next = (increment + position).mod(100)

            val fullLoops = abs(increment.div(100))
            val throughZero = when (direction) {
                "L" -> if (previous != 0 && previous <= next) 1 else 0
                "R" -> if (next != 0 && previous >= next) 1 else 0
                else -> 0
            }

            next to count + fullLoops + throughZero
        }

    val part1 = zeroes.second
    val part2 = zeroPasses.second + part1

    println("Part 1 solution is: $part1")
    println("Part 2 solution is: $part2")

    assert(part1 == 1168)
    assert(part2 == 7199)
}
