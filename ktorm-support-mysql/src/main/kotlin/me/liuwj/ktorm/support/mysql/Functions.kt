package me.liuwj.ktorm.support.mysql

import me.liuwj.ktorm.expression.ArgumentExpression
import me.liuwj.ktorm.expression.FunctionExpression
import me.liuwj.ktorm.schema.*

fun <T : Any> Column<List<T>>.jsonContains(item: T, itemSqlType: SqlType<T>): FunctionExpression<Boolean> {
    val listSqlType = this.sqlType

    // json_contains(column, json_array(item))
    return FunctionExpression(
        functionName = "json_contains",
        arguments = kotlin.collections.listOf(
            asExpression(),
            FunctionExpression(
                functionName = "json_array",
                arguments = kotlin.collections.listOf(ArgumentExpression(item, itemSqlType)),
                sqlType = listSqlType
            )
        ),
        sqlType = BooleanSqlType
    )
}

infix fun Column<List<Int>>.jsonContains(item: Int): FunctionExpression<Boolean> {
    return this.jsonContains(item, IntSqlType)
}

infix fun Column<List<Long>>.jsonContains(item: Long): FunctionExpression<Boolean> {
    return this.jsonContains(item, LongSqlType)
}

infix fun Column<List<Double>>.jsonContains(item: Double): FunctionExpression<Boolean> {
    return this.jsonContains(item, DoubleSqlType)
}

infix fun Column<List<Float>>.jsonContains(item: Float): FunctionExpression<Boolean> {
    return this.jsonContains(item, FloatSqlType)
}

infix fun Column<List<String>>.jsonContains(item: String): FunctionExpression<Boolean> {
    return this.jsonContains(item, VarcharSqlType)
}


fun <T : Any> Column<*>.jsonExtract(path: String, sqlType: SqlType<T>): FunctionExpression<T> {
    // json_extract(column, path)
    return FunctionExpression(
        functionName = "json_extract",
        arguments = kotlin.collections.listOf(asExpression(), ArgumentExpression(path, VarcharSqlType)),
        sqlType = sqlType
    )
}

fun rand(): FunctionExpression<Double> {
    return FunctionExpression(functionName = "rand", arguments = emptyList(), sqlType = DoubleSqlType)
}

fun <T : Comparable<T>> greatest(vararg columns: ColumnDeclaring<T>): FunctionExpression<T> {
    // greatest(left, right)
    return FunctionExpression(
        functionName = "greatest",
        arguments = columns.map { it.asExpression() },
        sqlType = columns[0].sqlType
    )
}

fun <T : Comparable<T>> greatest(left: ColumnDeclaring<T>, right: T): FunctionExpression<T> {
    return greatest(left, left.wrapArgument(right))
}

fun <T : Comparable<T>> greatest(left: T, right: ColumnDeclaring<T>): FunctionExpression<T> {
    return greatest(right.wrapArgument(left), right)
}

fun <T : Comparable<T>> least(vararg columns: ColumnDeclaring<T>): FunctionExpression<T> {
    // least(left, right)
    return FunctionExpression(
        functionName = "least",
        arguments = columns.map { it.asExpression() },
        sqlType = columns[0].sqlType
    )
}

fun <T : Comparable<T>> least(left: ColumnDeclaring<T>, right: T): FunctionExpression<T> {
    return least(left, left.wrapArgument(right))
}

fun <T : Comparable<T>> least(left: T, right: ColumnDeclaring<T>): FunctionExpression<T> {
    return least(right.wrapArgument(left), right)
}