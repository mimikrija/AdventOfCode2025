fun main() {
    val rawInput = readInput("Day02")

    val idRanges =
        rawInput
            .first()
            .split(',')
            .map { id -> id.split('-') }

    val part1 = idRanges.sumOf { (first, last) ->
        (first.toLong()..last.toLong()).sumOf { number ->
            val str = number.toString()
            val size = str.length
            if (size < 2 || size % 2 == 1) return@sumOf 0
            if (str.chunked(size/2).toSet().size == 1) number else 0
        }
    }

    val part2 = idRanges.sumOf { (first, last) ->
        (first.toLong()..last.toLong()).sumOf { number ->
            val str = number.toString()
            val size = str.length
            if ((1..size/2)
                    .any { chunkSize ->
                        str.chunked(chunkSize).toSet().size == 1
                    }
                ) number else 0 
        } 
    }

    println("Part 1 solution is: $part1")
    println("Part 2 solution is: $part2")

    assert(part1 == 53420042388)
    assert(part2 == 69553832684)
}

