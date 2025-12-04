fun main() {
    val rollMap = readInput("Day04")
        .flatMapIndexed { row, line ->
            line
                .mapIndexed { column, letter -> Coordinate(column, row) to letter.toString() }
        }
        .filter{ it.second == "@"}
        .map{ it.first }
        .toSet()
    
    val gridSize = rollMap.maxOf { it.x } + 1

    
    val part1 = accessibleRolls(rollMap, gridSize).size
    val part2 = rollMap.size - keepRemoving(rollMap, gridSize).size



    println("Part 1 solution is: $part1")
    println("Part 1 solution is: $part2")
    
    assert(part1 == 1493)
    assert(part1 == 9194)

}

private fun accessibleRolls(rollMap: Set<Coordinate>, gridSize: Int) = rollMap.filter { position ->
    val neighbours = neighbors(position, gridSize, includeDiagonals = true)
    neighbours.count { it in rollMap } < 4
}

private fun keepRemoving(
    rollMap: Set<Coordinate>,
    gridSize: Int
): Set<Coordinate> {
    val accessibleRolls = accessibleRolls(rollMap, gridSize = gridSize)
    if (accessibleRolls.isEmpty()) return rollMap
    return keepRemoving(rollMap - accessibleRolls, gridSize)
}
