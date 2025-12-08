import kotlin.math.pow

fun main() {
    val coordinates = readInput("Day08")
        .map { it.split("," ).toCoordinate3D()}

    
    val closestCoordinates = coordinates
        .flatMapIndexed { i, first -> 
            coordinates.slice(i + 1 until coordinates.size).map { second ->
                    listOf(first, second).sortedBy { it.x } }}
            .sortedBy { it.first().euclideanDistanceSquared(it.last()) }

    
    val part1 = connect(closestCoordinates.take(1000))
    

   println("Part 1 solution is: $part1")
    
    val part2 = connect(closestCoordinates, part2 = true)
    
    println("Part 2 solution is: $part2") 

    assert(part1 == 42840)
    assert(part2 == 170629052)
}


private fun connect(
    coordinatePairs: List<List<Coordinate3D>>,
    part2: Boolean = false,
): Int {
    val allCoordinates = coordinatePairs.flatMap { it }.toSet()
        .map{setOf(it).toMutableSet() }.toMutableList()
    
    coordinatePairs
        .forEach { (first, second) -> 
            val firstSet = allCoordinates.first { it.contains(first) }
            val secondSet = allCoordinates.first { it.contains(second) }
            if (firstSet != secondSet) {
                allCoordinates.remove(firstSet)
                allCoordinates.remove(secondSet)
                firstSet.addAll(secondSet)
                allCoordinates.add(firstSet)}
            val setSizes = allCoordinates.map{it.size}.toSet()
            if (setSizes.size == 1 && part2) return first.x * second.x
        }

    return allCoordinates.map{it.size}.sortedDescending().take(3).fold(1) { acc, i -> acc * i }

}

private fun Coordinate3D.euclideanDistanceSquared(other: Coordinate3D) =
    (x - other.x).toDouble().pow(2) + (y - other.y).toDouble().pow(2) + (z - other.z).toDouble().pow(2)