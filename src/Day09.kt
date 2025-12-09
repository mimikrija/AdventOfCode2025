import kotlin.math.abs
import kotlin.math.pow

fun main() {
    val coordinates = readInput("Day09")
        .map { it.split("," ).toCoordinateLong()}

    val coordinateCombinations = coordinates
        .flatMapIndexed { i, first ->
            coordinates.slice(i + 1 until coordinates.size)
                .map{second -> first.areaBetween(second)}
        }
    
    val part1 = coordinateCombinations.max()
    println("Part 1 solution is: $part1")
    
    val connections =(coordinates + coordinates.first())
        .zipWithNext()
        .associate { (first, second) -> first to second }
    

   // println(connections)
    
    val part2 = coordinates
        .flatMapIndexed { i, first ->
            coordinates.slice(i + 1 until coordinates.size)
                .mapNotNull { second -> 
                    val areaVulgaris = first.areaBetween(second)
                    val pointsBetween = coordinates.getPointsBetween(first, second)
                    if (pointsBetween.isNegative()) null else first to second to areaVulgaris
                }
        }.maxBy {it.second}
    
    println(part2)
    
    // 2937908610 not
//    
//    val part1 = connect(closestCoordinates.take(1000))
//    val part2 = connect(closestCoordinates, part2 = true)
//    
//    println("Part 1 solution is: $part1")
//    println("Part 2 solution is: $part2") 
//
//    assert(part1 == 42840)
//    assert(part2 == 170629052)
}



private fun CoordinateLong.areaBetween(other: CoordinateLong) =
    abs(x - other.x + 1) * abs(y - other.y + 1)

private fun List<CoordinateLong>.isNegative(): Boolean =
    (this + this.first()).zipWithNext()
        .map { (first, second) -> (first.x * second.y) - (second.x * first.y) }
        .chunked(4)
        .any { it.sum() < 0 }

private fun List<CoordinateLong>.getPointsBetween(
    first: CoordinateLong,
    second: CoordinateLong,
): List<CoordinateLong>{
    val indexFirst = indexOf(first)
    val indexSecond = indexOf(second)
    if (indexFirst < indexSecond) return this.slice(indexFirst  .. indexSecond)
    else return this.slice(indexSecond .. indexFirst) 
    
}