import kotlin.text.mapIndexed

fun main() {
    val mathHomework = readInputNoTrim("Day06")
        
    val numbers = mathHomework.dropLast(1)    
        .map { line ->
            Regex("\\d+").findAll(line)
                .mapIndexed { index, f -> index to f.value }
                .toList()
                .associate { it.first to it.second}
        }
    
    val operations = mathHomework.takeLast(1)
        .map { line ->
            Regex("[+*]").findAll(line)
                .mapIndexed { index, f -> index to f.value }
                .toList()
                .associate { it.first to it.second}
        }.first()
    
    
    val part1 = (0 until operations.size)
        .sumOf { index -> 
            val numbersForOperation = numbers.map { it[index]!!.toLong() }
            val operation = operations[index]
            if (operation == "+") numbersForOperation.sum() else 
                numbersForOperation.fold(1) { acc, i -> acc * i }
        }
    
    println("Part 1 solution is: $part1")
    
    
    val cephalopodsNumberColumns = mathHomework
        .map { line -> line.mapIndexed { column, letter -> column to letter }
                .associate { it.first to it.second }
        }

    val lineLength = cephalopodsNumberColumns.first().values.size
    
    val verticalNumbers = (0..lineLength)
        .map { column ->
            cephalopodsNumberColumns.mapNotNull { it[column]?.toString() }
                .joinToString("") 
        }
    
    var part2 = 0.toLong()
    val stack = ArrayDeque(verticalNumbers )
    var operation = ""
    var nums = emptyList<Long>().toMutableList()
    
    while (stack.isNotEmpty()) {
        val current = stack.removeFirst().filterNot {it.isWhitespace()}
        if (current.isEmpty()) {

            part2 += if (operation == "+") nums.sum() else nums.reduce { acc, i -> acc * i }
            nums = emptyList<Long>().toMutableList()
            continue
        }
        if ("+" in current || "*" in current) {
            operation = current.last().toString()
        }
        nums += Regex("\\d+").findAll(current)
            .joinToString { it.value }
            .toLong()
    }
    

    println("Part 2 solution is: $part2")

    assert(part1 == 6891729672676)
    assert(part2 == 9770311947567)
}

