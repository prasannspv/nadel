// @formatter:off
package graphql.nadel.tests.legacy.polymorphism

import graphql.nadel.tests.next.ExpectedNadelResult
import graphql.nadel.tests.next.ExpectedServiceCall
import graphql.nadel.tests.next.TestSnapshot
import graphql.nadel.tests.next.listOfJsonStrings
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.listOf

private suspend fun main() {
    graphql.nadel.tests.next.update<`query with pass through interfaces and unions that DONT have typename in them work as expected`>()
}

/**
 * This class is generated. Do NOT modify.
 *
 * Refer to [graphql.nadel.tests.next.UpdateTestSnapshots
 */
@Suppress("unused")
public class
        `query with pass through interfaces and unions that DONT have typename in them work as expected snapshot`
        : TestSnapshot() {
    override val calls: List<ExpectedServiceCall> = listOf(
            ExpectedServiceCall(
                service = "PetService",
                query = """
                | query petQ {
                |   pets(isLoyal: true) {
                |     name
                |     ... on Cat {
                |       wearsBell
                |     }
                |     ... on Dog {
                |       wearsCollar
                |     }
                |   }
                | }
                """.trimMargin(),
                variables = "{}",
                result = """
                | {
                |   "data": {
                |     "pets": [
                |       {
                |         "name": "Sparky",
                |         "wearsCollar": true
                |       },
                |       {
                |         "name": "Whiskers",
                |         "wearsBell": false
                |       }
                |     ]
                |   }
                | }
                """.trimMargin(),
                delayedResults = listOfJsonStrings(
                ),
            ),
            ExpectedServiceCall(
                service = "PetService",
                query = """
                | query petQ {
                |   raining(isLoyal: true) {
                |     ... on Cat {
                |       wearsBell
                |     }
                |     ... on Dog {
                |       wearsCollar
                |     }
                |   }
                | }
                """.trimMargin(),
                variables = "{}",
                result = """
                | {
                |   "data": {
                |     "raining": {
                |       "wearsCollar": true
                |     }
                |   }
                | }
                """.trimMargin(),
                delayedResults = listOfJsonStrings(
                ),
            ),
        )

    /**
     * ```json
     * {
     *   "data": {
     *     "pets": [
     *       {
     *         "name": "Sparky",
     *         "wearsCollar": true
     *       },
     *       {
     *         "name": "Whiskers",
     *         "wearsBell": false
     *       }
     *     ],
     *     "raining": {
     *       "wearsCollar": true
     *     }
     *   }
     * }
     * ```
     */
    override val result: ExpectedNadelResult = ExpectedNadelResult(
            result = """
            | {
            |   "data": {
            |     "pets": [
            |       {
            |         "name": "Sparky",
            |         "wearsCollar": true
            |       },
            |       {
            |         "name": "Whiskers",
            |         "wearsBell": false
            |       }
            |     ],
            |     "raining": {
            |       "wearsCollar": true
            |     }
            |   }
            | }
            """.trimMargin(),
            delayedResults = listOfJsonStrings(
            ),
        )
}
