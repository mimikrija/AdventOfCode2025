fun main() {
    val rawInput = readInput("Day05")
        
    val ranges = rawInput
        .filter{"-" in it}
        .map { it.split("-")
            .map { it.toLong() } }
        .map{it.first() .. it.last()}
    
    val numbers = rawInput
        .filter{"-" !in it}
        .filter{ it.length > 1}
        .map { it.toLong() }
    
    
    val part1 = numbers
        .count{ ranges.any { range -> it in range } }

    println("Part 1 solution is: $part1")
    
    
    val part2 = ranges.sortedBy { it.start }.toSet()
        .fold(emptySet<LongRange>()) { acc, checkRange ->

            if (acc.any { checkRange.fullyImmersedIn(it) }) acc
            else {
            val withinCheck = acc.filter { it.fullyImmersedIn(checkRange) }
            val pruned = acc - withinCheck
            val merged = pruned.map { range ->
                if (range.touches(checkRange)) 
                    range.merge(checkRange) else range 
            }.toSet()
            if (pruned == merged) { pruned + setOf(checkRange) } else merged}

        }.sumOf { it.endInclusive - it.start + 1 }
    
    
    println("Part 2 solution is: $part2")

    assert(part1 == 782)
    assert(part2 == 353863745078671)
}


private fun LongRange.fullyImmersedIn(other: LongRange) =
    start in other && endInclusive in other

private fun LongRange.touches(other: LongRange) =
    start in other || endInclusive in other

private fun LongRange.merge(other: LongRange) =
    if (start in other) other.start .. endInclusive else start .. other.endInclusive
