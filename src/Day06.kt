import kotlin.text.mapIndexed

fun main() {
    val mathHomework = readInput("Day06")
        
    val numbers = mathHomework.dropLast(1)    
        .map { line ->
            Regex("\\d+|[^\\d ]").findAll(line)
                .mapIndexed { index, f -> index to f.value }
                .toList()
                .associate { it.first to it.second}
        }
    
    val operations = mathHomework.takeLast(1)
        .map { line ->
            Regex("[^\\d ]").findAll(line)
                .mapIndexed { index, f -> index to f.value }
                .toList()
                .associate { it.first to it.second}
        }.first()
    
    val operationCount = operations.size
    
    
    val part1 = (0 until operationCount)
        .sumOf { index -> 
            val numbersForOperation = numbers.map { it[index]!!.toLong() }
            val operation = operations[index]
            if (operation == "+") numbersForOperation.sum() else 
                numbersForOperation.fold(1) { acc, i -> acc * i }
        }
    
    println("Part 1 solution is: $part1")
    
    
    val cephalopodsNumberColumns = mathHomework
        .map { line ->
            line.map{it}
                .mapIndexed { column, letter -> column to letter }
                .associate { it.first to it.second }
        }

    val lineLength = cephalopodsNumberColumns.flatMap{it.keys}.max()
    
    val verticalNumbers = (0..lineLength)
        .map { column ->
            cephalopodsNumberColumns.mapNotNull { it[column] }
                .joinToString("") { it.toString() }
        }
    
    val operationLocations = verticalNumbers
        .mapIndexedNotNull { index, chars -> if (chars.contains("+") or chars.contains("*")) index else null }
    
    val splitPositions = operationLocations.zipWithNext().map{it.first to it.second}  + listOf(
        operationLocations.last() to lineLength+1)
    

    val part2 = splitPositions.map { (start, end) ->
        verticalNumbers.subList(start, end)
    }.sumOf {
        val operation = it.first().last().toString()
        val numbers = it.flatMap { n -> Regex("\\d+").findAll(n).map { f -> f.value.toLong() } }
        if (operation == "+") numbers.sum() else numbers.fold(1.toLong()) { acc, i -> acc * i }
    }
    

    println("Part 2 solution is: $part2")

    assert(part1 == 6891729672676)
    assert(part2 == 9770311947567)
}

