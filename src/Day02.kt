import java.util.Collections
import kotlin.math.abs
import kotlin.math.max

fun main() {
    val rawInput = readInput("Day02")

    val idRanges =
        rawInput
            .first()
            .split(',')
            .map { id -> id.split('-')}
    
    
    val part1 = idRanges.sumOf { (first, last) ->
        (first.toLong()..last.toLong()).sumOf { number ->
            val str = number.toString()
            val size = str.length
            if (size < 2 || size%2 == 1) return@sumOf 0
            val checks = 
                str.slice(0 until size.div(2)) to str.slice(size.div(2) until size)
            
            if (checks.first == checks.second) number else 0
        }

    }

    println("Part 1 solution is: $part1")

    assert(part1 == 53420042388)


}
    