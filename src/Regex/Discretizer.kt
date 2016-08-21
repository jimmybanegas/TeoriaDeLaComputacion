package src.Regex

import java.io.Serializable
import java.util.*

/**
 * Created by Jimmy Banegas on 20-Aug-16.
 */

open class Discretizer : Serializable{

    companion object{

        /**
         * Given a regular expression, this will return the subexpressions that,
         * when or-ed together, result in the expression.

         * @param expression
         * *            the regular expression
         * *
         * @return an array of the subexpressions
         */
        fun or(expression: String): Array<String> {
            val se = ArrayList<String>() // Subexpressions.
            var start = 0
            var level = 0
            for (i in 0..expression.length - 1) {
                if (expression[i] == '(')
                    level++
                if (expression[i] == ')')
                    level--
                if (expression[i] != '+')
                    continue
                if (level != 0)
                    continue
                // First level or!
                se.add(delambda(expression.substring(start, i)))
                start = i + 1
            }
            se.add(delambda(expression.substring(start)))
            return se.toArray(arrayOfNulls<String>(0))
        }


        /**
         * Given a regular expression, this will return the subexpressions that,
         * when concatenated together, will result in the expression.

         * @param expression
         * *            the regular expression
         * *
         * @return an array of the subexpressions
         */
        fun cat(expression: String): Array<String> {
            val se = ArrayList<String>() // Subexpressions.
            var start = 0
            var level = 0
            for (i in 0..expression.length - 1) {
                val c = expression[i]
                if (c == ')') {
                    level--
                    continue
                }
                if (c == '(')
                    level++
                if (!(c == '(' && level == 1) && level != 0)
                    continue
                if (c == '+') {
                    // Hum. That shouldn't be...
                    throw IllegalArgumentException(
                            "+ encountered in cat discretization!")
                }
                if (c == '*')
                    continue
                // Not an operator, and on the first level!
                if (i == 0)
                    continue
                se.add(delambda(expression.substring(start, i)))
                start = i
            }
            se.add(delambda(expression.substring(start)))
            return se.toArray(arrayOfNulls<String>(0))
        }

        /**
         * Given a string, returns the string, or the empty string if the string is
         * the lambda string.

         * @param string
         * *            the string to possibly replace
         * *
         * @return the string, or the empty string if the string is the lambda
         * *         string
         */
        fun delambda(string: String): String {
             return ""
        }

    }

}
