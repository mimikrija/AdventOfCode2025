import kotlin.sequences.toList



fun main() {
    val rawInput = readInput("Day10")
    
    val lights = rawInput
        .map { 
            val pattern = Regex("\\[((.+))\\]").findAll(it).map{it.groupValues[1]}.toList().first() 
            val numbers = Regex("((\\d+,?)+)").findAll(it)
                .map{it.groupValues.first().split(" ")}
                .toList()
                .flatMap { it.map { it.split(",").map{it.toInt()}} }
            
            val buttons = numbers
                .dropLast(1)
                .map { button ->
                    pattern.mapIndexed { index, _ -> index in button }
                }
            val joltage = numbers.takeLast(1).flatten()
            
            Light(pattern, buttons, joltage)
            
        }

    val part1 = lights.sumOf { light ->
        val solution = light.pattern.map { it == '#' }
        val buttons = light.buttons
        val permutationsOfSingleButtonPresses = createPermutations(buttons.size)

        permutationsOfSingleButtonPresses
            .filter { permutation ->
                val perButtonSolutions = permutation.zip(buttons).map { (permutation, button) ->
                    button.map { it.and(permutation) }
                }
                val finalSolutionPerPermutation = perButtonSolutions.reduce { acc, buttonSolution ->
                    acc.zip(buttonSolution).map { (a, b) -> a xor b }
                }
                finalSolutionPerPermutation == solution
            }.minOf { permutation -> permutation.count {it} }

    }
    

    println("Part 1 solution is: $part1")
    
    assert(part1 == 524)
}


data class Light(val pattern: String, val buttons: List<List<Boolean>>, val joltage: List<Int>)

private fun createPermutations(
    n: Int, 
    from: List<Boolean> = listOf(false, true)
): List<List<Boolean>> {
    if (n == 1) return from.map{ listOf(it)}
    else return createPermutations(n - 1, from)
        .flatMap { list -> from.map { list + it }}
}
