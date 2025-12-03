fun main() {
    val batteries = readInput("Day03")
    

    val part1 = batteries.sumOf { battery -> highestNumber(battery, digits = 2).toLong() }
    val part2 = batteries.sumOf { battery -> highestNumber(battery, digits = 12).toLong() }

    println("Part 1 solution is: $part1")
    println("Part 2 solution is: $part2")

    assert(part1 == 16854.toLong())
    assert(part2 == 167526011932478)
}


private fun highestNumber(rest: String, solution: String="", digits: Int): String{
    val solutionSize = solution.length
    if (solutionSize == digits) return solution
    val minimumRestSize = digits - solutionSize - 1
    val left = rest.slice(0 until rest.length - minimumRestSize)
    val highestInLeft = left.max()
    val firstMaxPos = left.indexOfFirst { it == highestInLeft }
    return highestNumber(
        rest = rest.slice(firstMaxPos + 1 until rest.length),
        solution = solution + highestInLeft.toString(),
        digits = digits
    ) 
    
}
