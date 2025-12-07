fun main() {
    val tachyonManifold = readInput("Day07")
        .flatMapIndexed { row, line ->
            line
                .mapIndexed { column, letter -> Coordinate(column, row) to letter}
        }
        .associate { it }
    
    val initialBeam = tachyonManifold.filter { it.value == 'S' }.keys.first()


    
    val maxY = tachyonManifold.maxOf { it.key.y }
    
    val part1 = splitTheBeam(initialBeam, tachyonManifold, maxY)
    val part2 = recursiveTimelines(initialBeam, tachyonManifold, maxY)
    
    println("Part 1 solution is: $part1")
    println("Part 2 solution is: $part2")

    assert(part1 == 1537)
    assert(part2 == 18818811755665)
}


private fun splitTheBeam(
    inputBeam: Coordinate,
    tachyonManifold: Map<Coordinate, Char>,
    maxY: Int,
): Int{
    var beams = setOf(inputBeam).toSet()
    val down = Coordinate(0, 1)
    val left = Coordinate(-1, 0)
    val right = Coordinate(1, 0)
    var countSplits = 0
    while (!beams.any{it.y == maxY}) {
        val nextPositions = beams
            .map { it + down }
            .flatMap { if (tachyonManifold[it] == '^')
            {   
                countSplits += 1
                listOf(it + left, it + right) } else listOf(it) }
        beams = nextPositions.toSet()
    }
    return countSplits
}

private fun recursiveTimelines(
    position: Coordinate,
    tachyonManifold: Map<Coordinate, Char>,
    maxY: Int,
    memo: MutableMap<Coordinate, Long> = emptyMap<Coordinate, Long>().toMutableMap()
): Long {
    val left = Coordinate(-1, 0)
    val right = Coordinate(1, 0)
    if (memo.containsKey(position)) return memo[position]!!
    val nextSplit = findNextSplitBelow(position, tachyonManifold)
    if (nextSplit == null) return 1L
    val solutionLeft = recursiveTimelines(nextSplit + left, tachyonManifold, maxY, memo)
    memo[nextSplit+left] = solutionLeft
    val solutionRight = recursiveTimelines(nextSplit + right, tachyonManifold, maxY, memo)
    memo[nextSplit+right] = solutionRight
    return solutionLeft + solutionRight
}

private fun findNextSplitBelow(
    position: Coordinate,
    tachyonManifold: Map<Coordinate, Char>,
): Coordinate? = tachyonManifold
    .filter { it.key.y > position.y && it.key.x == position.x }
    .filter { it.value == '^' }
    .keys.minByOrNull { it.y }
