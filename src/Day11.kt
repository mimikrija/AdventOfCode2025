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
    
    val part2 = countPaths(connections, lastPoint = "svr", goal = "dac") * countPaths(connections, lastPoint = "dac", goal = "fft") * countPaths(connections, lastPoint = "fft", goal = "out") +
            countPaths(connections, lastPoint = "svr", goal = "fft") * countPaths(connections, lastPoint = "fft", goal = "dac") * countPaths(connections, lastPoint = "dac", goal = "out")

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
