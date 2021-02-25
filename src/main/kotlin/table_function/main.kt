package table_function

import kotlin.math.abs

class TableFunction {
    private var table = mutableListOf<Couple>()

    val size: Int get() = table.size

    private class Couple(
        val x: Double,
        var y: Double
    )

    fun add(x: Double, y: Double): Boolean {
        if (table.size == 0) {
            table.add(Couple(x, y))
        } else {
            for (i in 0 until table.size) {
                if (x == table[i].x) {
                    table[i].y = y
                    return false
                }
            }
            table.add(Couple(x, y))
        }
        return true
    }

    fun remove(x: Double): Boolean {
        for (i in 0 until table.size) {
            if (x == table[i].x) {
                table.removeAt(i)
                return true
            }
        }
        return false
    }

    fun getPairs(): Collection<Pair<Double, Double>> {
        val result = mutableListOf<Pair<Double, Double>>()
        for (i in 0 until table.size) {
            result.add(Pair(table[i].x, table[i].y))
        }
        return result
    }

    fun findPair(x: Double): Pair<Double, Double>? {
        if (table.size == 0) {
            throw IllegalStateException()
        } else {
            var minDifValue: Double = Double.MAX_VALUE
            var result: Pair<Double, Double>? = null
            for (i in 0 until table.size) {
                if (abs(x - table[i].x) < minDifValue) {
                    minDifValue = abs(x - table[i].x)
                    result = Pair(table[i].x, table[i].y)
                }
            }
            return result
        }
    }

    fun getValue(x: Double): Double {
        when (table.size) {
            0 -> throw IllegalStateException()
            1 -> return table[0].y
            else -> {
                var quantityNumLess = 0
                var quantityNumMore = 0
                var firstMinIndex = Integer.MAX_VALUE
                var secondMinIndex = Integer.MAX_VALUE
                var firstMaxIndex = Integer.MIN_VALUE
                var secondMaxIndex = Integer.MIN_VALUE
                var minFirstValue = Double.MIN_VALUE
                var maxFirstValue = Double.MAX_VALUE
                var minSecondValue = Double.MIN_VALUE
                var maxSecondValue = Double.MAX_VALUE
                for (i in 0 until table.size) {
                    if (x == table[i].x) return table[i].y
                    if (x > table[i].x) {
                        quantityNumLess++
                        if (table[i].x > minFirstValue) {
                            minSecondValue = minFirstValue
                            minFirstValue = table[i].x
                            secondMinIndex = firstMinIndex
                            firstMinIndex = i
                        } else if (table[i].x > minSecondValue) {
                            minSecondValue = table[i].x
                            secondMinIndex = i
                        }
                    }
                    if (x < table[i].x) {
                        quantityNumMore++
                        if (table[i].x < maxFirstValue) {
                            maxSecondValue = maxFirstValue
                            maxFirstValue = table[i].x
                            secondMaxIndex = firstMaxIndex
                            firstMaxIndex = i
                        } else if (table[i].x < maxSecondValue) {
                            maxSecondValue = table[i].x
                            secondMaxIndex = i
                        }
                    }
                }
                return when {
                    quantityNumLess > 0 && quantityNumMore > 0 -> {
                        (x - minFirstValue) * (table[firstMaxIndex].y - table[firstMinIndex].y) / (maxFirstValue - minFirstValue) + table[firstMinIndex].y
                    }
                    quantityNumLess > 1 -> {
                        (x - minSecondValue) * (table[firstMinIndex].y - table[secondMinIndex].y) / (minFirstValue - minSecondValue) + table[secondMinIndex].y
                    }
                    else -> {
                        (x - maxFirstValue) * (table[secondMaxIndex].y - table[firstMaxIndex].y) / (maxSecondValue - maxFirstValue) + table[firstMaxIndex].y
                    }
                }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TableFunction) return false
        val firstTable = table
        val secondTable = other.table
        var countOfEquals = 0
        if (firstTable.size != secondTable.size) return false
        for (i in 0 until firstTable.size) {
            for (j in 0 until firstTable.size) {
                if (firstTable[i].x == secondTable[j].x && firstTable[i].y == secondTable[j].y) {
                    countOfEquals++
                }
            }
        }
        return countOfEquals == firstTable.size
    }

    override fun hashCode(): Int {
        var result = table.hashCode()
        result = 31 * result + size
        return result
    }
}