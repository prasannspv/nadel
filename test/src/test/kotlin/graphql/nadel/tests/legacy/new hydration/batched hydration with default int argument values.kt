package graphql.nadel.tests.legacy.`new hydration`

import graphql.nadel.tests.legacy.NadelLegacyIntegrationTest

class `batched hydration with default int argument values` : NadelLegacyIntegrationTest(
    query = """
        query {
          issues {
            id
            authors {
              id
            }
          }
        }
    """.trimIndent(),
    variables = emptyMap(),
    services = listOf(
        Service(
            name = "UserService",
            overallSchema = """
                type Query {
                  usersByIds(id: [ID], test: Int): [User]
                }
                type User {
                  id: ID
                }
            """.trimIndent(),
            underlyingSchema = """
                type Query {
                  usersByIds(id: [ID], test: Int): [User]
                }
                type User {
                  id: ID
                }
            """.trimIndent(),
            runtimeWiring = { wiring ->
                wiring.type("Query") { type ->
                    val usersByIds = listOf(
                        UserService_User(id = "USER-1"),
                        UserService_User(id = "USER-2"),
                        UserService_User(id = "USER-3"),
                        UserService_User(id = "USER-4"),
                        UserService_User(id = "USER-5"),
                    ).associateBy { it.id }

                    type.dataFetcher("usersByIds") { env ->
                        if (env.getArgument<Any?>("test") == 42) {
                            env.getArgument<List<String>>("id")?.map(usersByIds::get)
                        } else {
                            null
                        }
                    }
                }
            },
        ),
        Service(
            name = "Issues",
            overallSchema = """
                type Query {
                  issues: [Issue]
                }
                type Issue {
                  id: ID
                  authors(test: Int = 42): [User] @hydrated(
                    service: "UserService"
                    field: "usersByIds"
                    arguments: [
                      {name: "id" value: "${'$'}source.authorIds"}
                      {name: "test" value: "${'$'}argument.test"}
                    ]
                    identifiedBy: "id"
                    batchSize: 3
                  )
                }
            """.trimIndent(),
            underlyingSchema = """
                type Issue {
                  authorIds: [ID]
                  id: ID
                }
                type Query {
                  issues: [Issue]
                }
            """.trimIndent(),
            runtimeWiring = { wiring ->
                wiring.type("Query") { type ->
                    type.dataFetcher("issues") { env ->
                        listOf(
                            Issues_Issue(authorIds = listOf("USER-1", "USER-2"), id = "ISSUE-1"),
                            Issues_Issue(authorIds = listOf("USER-3"), id = "ISSUE-2"),
                            Issues_Issue(
                                authorIds = listOf("USER-2", "USER-4", "USER-5"),
                                id = "ISSUE-3",
                            ),
                        )
                    }
                }
            },
        ),
    ),
) {
    private data class UserService_User(
        val id: String? = null,
    )

    private data class Issues_Issue(
        val authorIds: List<String?>? = null,
        val id: String? = null,
    )
}
