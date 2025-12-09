import kotlin.math.abs

fun main() {
    val coordinates = readInput("Day09")
        .map { it.split(",").toCoordinateLong() }

    val allAreas = coordinates
        .flatMapIndexed { i, first ->
            coordinates.slice(i + 1 until coordinates.size)
                .map { second -> first.areaBetween(second) }
        }

    val part1 = allAreas.max()
    println("Part 1 solution is: $part1")
    

    val rectangles = coordinates
        .flatMapIndexed { i, first ->
            coordinates.slice(i + 1 until coordinates.size)
                .map { second -> first to second }
        }
        .map { (first, second) ->
            getRectanglePoints(first, second)
        }

    val part2 = rectangles.filter { rectangle ->
        val pointsAlongRectangleLines = (rectangle + rectangle.first())
            .zipWithNext()
            .map { line -> coordinates.filter { coord -> line.intersects(coord) } }
        
        pointsAlongRectangleLines.all { points ->
            val s = points.size
            // this other condition does nothing :(
            s == 0 || (points.areConsecutive(coordinates) && s > 1)
        }
    }.maxOfOrNull { it[0].areaBetween(it[2]) }

    println("Part 2 solution is: $part2")

}

// 4664839112 too high

private fun List<CoordinateLong>.areConsecutive(coordinates: List<CoordinateLong>): Boolean =
    this
        .zipWithNext()
        .all { (first, second) -> abs(coordinates.indexOf(first)-coordinates.indexOf(second)) == 1}


private fun Pair<CoordinateLong, CoordinateLong>.intersects(other: CoordinateLong): Boolean {
   
    return  (first.y == other.y && second.y == other.y &&
            ((other.x in first.x + 1 until second.x) || (other.x in second.x + 1 until first.x))) ||
            (first.x == other.x && second.x == other.x && ((other.y in first.y + 1 until second.y)
                || (other.y in second.y + 1 until first.y)))
    
    }



private fun CoordinateLong.areaBetween(other: CoordinateLong) =
    (abs(x - other.x) + 1) * (abs(y - other.y) + 1)



private fun List<CoordinateLong>.shoeLace(): Long =
    (this + this.first()).zipWithNext()
        .sumOf { (first, second) -> (first.x * second.y) - (second.x * first.y)}.div(2)

private fun List<CoordinateLong>.isNegative(): Boolean =
    (this ).zipWithNext()
        .map { (first, second) -> (first.x * (second.y)) - (second.x * (first.y)) }
        .chunked(4)
        .any { it.sum() < 0 }

private fun Map<CoordinateLong, CoordinateLong>.getPointsBetween(
    rectangle: List<CoordinateLong>,
): List<CoordinateLong>{
    val first = rectangle[0]
    val third = rectangle[2]
    
    fun isWithinArea(coordinate: CoordinateLong): Boolean {
        
        val minx = minOf(first.x, third.x)
        val maxx = maxOf(first.x, third.x)
        val miny = minOf(first.y, third.y)
        val maxy = maxOf(first.y, third.y)

        return coordinate.x in minx..maxx && coordinate.y in miny..maxy
    }
    
    fun CoordinateLong.isBetween(first: CoordinateLong, second: CoordinateLong): Boolean {

//        if ((first.x == second.x) && (first.x == this.x))
//        {
//            return if (first.y > second.y) (second.y < this.y) && (this.y < first.y)
//            else (first.y < this.y) and (this.y < second.y)
//        }
//
//        if ((first.y == second.y) && (first.y == this.y))
//        {
//            return if (first.x > second.x) (second.x < this.x) && (this.x < first.x)
//            else (first.x < this.x) and (this.x < second.x)
//        }
//        
//        return false
        val a = CoordinateLong(first.x, second.y)
        val b = CoordinateLong(second.x, second.y)

        return a == this || b == this

    }

    val second = rectangle[1]
    val fourth = rectangle[3]
    

    var current = first
    val points = mutableListOf(current)
    while (true) {
        val nextPoint = this[current]!!
        //println("for $current and $nextPoint analysing,,,,")
        if (nextPoint == first) return points
        if (isWithinArea(nextPoint)) {
            
            val match = rectangle.firstOrNull { it.isBetween(current, nextPoint) }
            //println(CoordinateLong(9,3).isBetween(current, nextPoint))
            //println("for $current and $nextPoint match is $match")
            if (match != null && match != nextPoint && match != current) points.add(match)
            points.add(nextPoint)
        }
        
        current = nextPoint 
    }
}

private fun getRectanglePoints(
    first: CoordinateLong,
    third: CoordinateLong,
): List<CoordinateLong>{


    val second = CoordinateLong(third.x, first.y)
    val fourth = CoordinateLong(first.x, third.y)

    return sortClockwise(listOf(first, second, third, fourth), first)
}

private fun sortClockwise(
    points: List<CoordinateLong>,
    first: CoordinateLong,
): List<CoordinateLong>{
    val topLeft = points.sortedBy { it.x }.minBy { it.y }
    val topRight = points.sortedBy { it.x }.reversed().minBy { it.y }
    val bottomRight = points.sortedBy { it.x }.reversed().maxBy { it.y }
    val bottomLeft = points.sortedBy { it.x }.maxBy { it.y }
    val result = listOf(topLeft, topRight, bottomRight, bottomLeft)
    
    val pos = result.indexOf(first)
    return result.slice(pos until result.size) + result.slice(0 until pos)
}

private fun List<CoordinateLong>.prune(): List<CoordinateLong> {
    val result = this.foldIndexed(initial = listOf(this.first())){ i, acc, current ->
        val last = acc.last()
        if (i == this.size - 1) return acc + current
        val nextOne = this[i+1]
        if (last.x == nextOne.x || last.y == nextOne.y) acc else acc + current
    }
    
   
    
    
    return result
    
    
}