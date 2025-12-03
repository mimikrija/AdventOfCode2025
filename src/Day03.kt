fun main() {
    val batteries = readInput("Day03")


    val part1 = batteries.sumOf { 
        val x = maxJoltage(it)
    x}
    
  println("Part 1 solution is: $part1")
//    println("Part 2 solution is: $part2")
//
//    assert(part1 == 53420042388)
//    assert(part2 == 69553832684)
}

private fun maxJoltage(battery: String): Int {
    val size = battery.length
    return (1 until size
            ).maxOf{ it ->
        val left = battery.slice(0 until it)
            .map{p -> p.toString().toInt()}
            .max()
            .toString()
        val right = battery.slice(it until size)
            .map{p-> p.toString().toInt()}
            .max()
            .toString()
        (left + right).toInt()
    }
}
