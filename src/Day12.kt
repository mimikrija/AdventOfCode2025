import kotlin.math.abs

fun main() {
    val regions = readInput("Day12").filter { "x" in it }

    val part1 = regions.count{
        val numbers = Regex("\\d+").findAll(it).map{it.value.toInt()}.toList()
        val x = numbers[0]
        val y = numbers[1]
        val count = numbers.drop(2).sum()
        val yCapacity = y.div(3)
        val xCapacity = x.div(3)
        
        count <= yCapacity * xCapacity
    }

    println("Part 1 solution is: $part1")
    assert(part1 == 410)
}
