import kotlin.collections.sumOf

fun main() {
    val connections = readInput("Day11")
        .associate {
            val leftRight = it.split(": ")
            val source = leftRight.first()
            val con = leftRight.last().split(" ")
            source to con
        }

    val part1 = countPaths(connections)
    println("Part 1 solution is: $part1")
    
    val part2 = countPathsWithMustVisits(connections, lastPoint = "svr", goal = "out")
    println("Part 2 solution is: $part2")
    
    assert(part1 == 615L)
    assert(part2 == 303012373210128)

}


private fun countPaths(
    connections: Map<String, List<String>>,
    goal: String = "out",
    lastPoint: String = "you",
    memo: MutableMap<String, Long> = mutableMapOf()
): Long
{
    if (memo.containsKey(lastPoint)) return memo[lastPoint]!!
    if (lastPoint == goal) return 1
    if (goal != "out" && lastPoint == "out") return 0
    return connections[lastPoint]!!.sumOf { next ->
        val solution = countPaths(connections, goal, next, memo)
        memo[next] = solution
        solution
    }
}


private fun countPathsWithMustVisits(
    connections: Map<String, List<String>>,
    lastPoint: String = "you",
    goal: String = "out",
    memo: MutableMap<Pair<String, Set<String>>, Long> = mutableMapOf(),
    seen: Set<String> = emptySet(),
    mustVisit: Set<String> = setOf("fft", "dac"),
): Long {
    if (memo.containsKey(lastPoint to seen)) return memo[lastPoint to seen]!!
    if (lastPoint == goal) return if (seen == mustVisit || mustVisit.isEmpty()) 1 else 0
    return connections[lastPoint]!!.sumOf { next ->
        val seenUpdate = if (next in mustVisit) seen + next else seen
        val solution = countPathsWithMustVisits(
            connections = connections,
            lastPoint = next,
            memo = memo,
            seen = seenUpdate,
        )
        memo[next to seenUpdate] = solution
        solution
    }
}
