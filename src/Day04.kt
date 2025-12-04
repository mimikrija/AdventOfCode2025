fun main() {
    val rollMap = readInput("Day04")
        .flatMapIndexed { row, line ->
            line
                .mapIndexed { column, letter -> Coordinate(column, row) to letter.toString() }
        }.associate { (position, letter) -> position to letter }
    
    val gridSize = rollMap.keys.maxOf { it.x } + 1


    
    val part1 = rollMap.filter { (position, value) ->
        if (value != "@") return@filter false
        val neighbours = neighbors(position, gridSize, includeDiagonals = true)
        neighbours.count { rollMap[it] == "@" } < 4
    }.size



    println("Part 1 solution is: $part1")


    assert(part1 == 1493)

}



    

