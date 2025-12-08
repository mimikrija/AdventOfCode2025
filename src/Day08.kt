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
    val part2 = connect(closestCoordinates, part2 = true)
    
    println("Part 1 solution is: $part1")
    println("Part 2 solution is: $part2") 

    assert(part1 == 42840)
    assert(part2 == 170629052)
}


private fun connect(
    closestCoordinates: List<List<Coordinate3D>>,
    part2: Boolean = false,
): Int {
    
    val connections = closestCoordinates
        .flatten()
        .toSet()
        .map{ mutableSetOf(it)}
        .toMutableList()
    
    closestCoordinates
        .forEach { (first, second) ->
            val firstSet = connections.first { it.contains(first) }
            val secondSet = connections.first { it.contains(second) }
            if (firstSet != secondSet) {
                connections.remove(secondSet)
                firstSet.addAll(secondSet)
                }
            // part 2 solution
            if (connections.size == 1 && part2) return first.x * second.x
        }
    // part 1 solution
    return connections
        .map{it.size}
        .sortedDescending()
        .take(3)
        .fold(1) { acc, i -> acc * i }
}

private fun Coordinate3D.euclideanDistanceSquared(other: Coordinate3D) =
    (x - other.x).toDouble().pow(2) +
            (y - other.y).toDouble().pow(2) + 
            (z - other.z).toDouble().pow(2)
