// @formatter:off
package graphql.nadel.tests.legacy.`new hydration`.`polymorphic hydrations`

import graphql.nadel.tests.next.ExpectedNadelResult
import graphql.nadel.tests.next.ExpectedServiceCall
import graphql.nadel.tests.next.TestSnapshot
import graphql.nadel.tests.next.listOfJsonStrings
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.listOf

private suspend fun main() {
    graphql.nadel.tests.next.update<`batch polymorphic hydration return top level null and error`>()
}

/**
 * This class is generated. Do NOT modify.
 *
 * Refer to [graphql.nadel.tests.next.UpdateTestSnapshots
 */
@Suppress("unused")
public class `batch polymorphic hydration return top level null and error snapshot` : TestSnapshot()
        {
    override val calls: List<ExpectedServiceCall> = listOf(
            ExpectedServiceCall(
                service = "foo",
                query = """
                | {
                |   foo {
                |     __typename
                |     __typename__batch_hydration__data: __typename
                |     batch_hydration__data__dataId: dataId
                |     batch_hydration__data__dataId: dataId
                |     id
                |   }
                | }
                """.trimMargin(),
                variables = "{}",
                result = """
                | {
                |   "data": {
                |     "foo": [
                |       {
                |         "__typename": "Foo",
                |         "id": "FOO-0",
                |         "batch_hydration__data__dataId": "PET-0",
                |         "__typename__batch_hydration__data": "Foo"
                |       },
                |       {
                |         "__typename": "Foo",
                |         "id": "FOO-1",
                |         "batch_hydration__data__dataId": "HUMAN-0",
                |         "__typename__batch_hydration__data": "Foo"
                |       },
                |       {
                |         "__typename": "Foo",
                |         "id": "FOO-2",
                |         "batch_hydration__data__dataId": "PET-1",
                |         "__typename__batch_hydration__data": "Foo"
                |       },
                |       {
                |         "__typename": "Foo",
                |         "id": "FOO-3",
                |         "batch_hydration__data__dataId": "HUMAN-1",
                |         "__typename__batch_hydration__data": "Foo"
                |       }
                |     ]
                |   }
                | }
                """.trimMargin(),
                delayedResults = listOfJsonStrings(
                ),
            ),
            ExpectedServiceCall(
                service = "people",
                query = """
                | {
                |   humanById(ids: ["HUMAN-0", "HUMAN-1"]) {
                |     __typename
                |     id
                |     batch_hydration__data__id: id
                |     name
                |   }
                | }
                """.trimMargin(),
                variables = "{}",
                result = """
                | {
                |   "data": {
                |     "humanById": [
                |       {
                |         "__typename": "Human",
                |         "id": "HUMAN-0",
                |         "name": "Fanny Longbottom",
                |         "batch_hydration__data__id": "HUMAN-0"
                |       },
                |       {
                |         "__typename": "Human",
                |         "id": "HUMAN-1",
                |         "name": "John Doe",
                |         "batch_hydration__data__id": "HUMAN-1"
                |       }
                |     ]
                |   }
                | }
                """.trimMargin(),
                delayedResults = listOfJsonStrings(
                ),
            ),
            ExpectedServiceCall(
                service = "pets",
                query = """
                | {
                |   petById(ids: ["PET-0", "PET-1"]) {
                |     __typename
                |     breed
                |     id
                |     batch_hydration__data__id: id
                |   }
                | }
                """.trimMargin(),
                variables = "{}",
                result = """
                | {
                |   "errors": [
                |     {
                |       "message": "something went wrong",
                |       "locations": [],
                |       "extensions": {
                |         "classification": "DataFetchingException"
                |       }
                |     }
                |   ],
                |   "data": {
                |     "petById": null
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
     *   "errors": [
     *     {
     *       "message": "something went wrong",
     *       "locations": [],
     *       "extensions": {
     *         "classification": "DataFetchingException"
     *       }
     *     }
     *   ],
     *   "data": {
     *     "foo": [
     *       {
     *         "__typename": "Foo",
     *         "id": "FOO-0",
     *         "data": null
     *       },
     *       {
     *         "__typename": "Foo",
     *         "id": "FOO-1",
     *         "data": {
     *           "__typename": "Human",
     *           "id": "HUMAN-0",
     *           "name": "Fanny Longbottom"
     *         }
     *       },
     *       {
     *         "__typename": "Foo",
     *         "id": "FOO-2",
     *         "data": null
     *       },
     *       {
     *         "__typename": "Foo",
     *         "id": "FOO-3",
     *         "data": {
     *           "__typename": "Human",
     *           "id": "HUMAN-1",
     *           "name": "John Doe"
     *         }
     *       }
     *     ]
     *   }
     * }
     * ```
     */
    override val result: ExpectedNadelResult = ExpectedNadelResult(
            result = """
            | {
            |   "errors": [
            |     {
            |       "message": "something went wrong",
            |       "locations": [],
            |       "extensions": {
            |         "classification": "DataFetchingException"
            |       }
            |     }
            |   ],
            |   "data": {
            |     "foo": [
            |       {
            |         "__typename": "Foo",
            |         "id": "FOO-0",
            |         "data": null
            |       },
            |       {
            |         "__typename": "Foo",
            |         "id": "FOO-1",
            |         "data": {
            |           "__typename": "Human",
            |           "id": "HUMAN-0",
            |           "name": "Fanny Longbottom"
            |         }
            |       },
            |       {
            |         "__typename": "Foo",
            |         "id": "FOO-2",
            |         "data": null
            |       },
            |       {
            |         "__typename": "Foo",
            |         "id": "FOO-3",
            |         "data": {
            |           "__typename": "Human",
            |           "id": "HUMAN-1",
            |           "name": "John Doe"
            |         }
            |       }
            |     ]
            |   }
            | }
            """.trimMargin(),
            delayedResults = listOfJsonStrings(
            ),
        )
}
