import java.util.Collections
import kotlin.math.abs
import kotlin.math.max

fun main() {
    val rawInput = readInput("Day01")

    val instructions =
        rawInput
            .map { text ->
                text
                    .slice(0 until 1) to text.slice(1 until text.length).toInt()
            }

    
    var zeroes = 0
    var zeroPasses = 0
    var position = 50
    
    instructions
        .forEach { (direction, distance) -> 
            val increment = when (direction) {"L" -> -distance "R" -> distance else -> 0}
            val previous = position
            position = (increment + position).mod(100)
            if (position == 0) zeroes++
            val extraLoops = abs(increment.div(100))
            val throughZero = when(direction) {
                "L" -> if (previous != 0 && previous <= position) 1 else 0
                "R" -> if (position != 0 && previous >= position) 1 else 0
                else -> 0
            }
            zeroPasses += throughZero + extraLoops
    }
    

    
    val part1 = zeroes
    val part2 = zeroPasses + zeroes
    
    
    println("Part 1 solution is: $part1")
    println("Part 2 solution is: $part2")

    assert(part1 == 1168)
    assert(part2 == 7199)

}
    