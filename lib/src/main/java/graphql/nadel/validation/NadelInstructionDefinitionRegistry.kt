package graphql.nadel.validation

import graphql.nadel.definition.NadelInstructionDefinition
import graphql.nadel.definition.NadelSchemaMemberCoordinates
import graphql.nadel.definition.coordinates
import graphql.nadel.definition.hydration.NadelDefaultHydrationDefinition
import graphql.nadel.definition.hydration.NadelHydrationDefinition
import graphql.nadel.definition.partition.NadelPartitionDefinition
import graphql.nadel.definition.renamed.NadelRenamedDefinition
import graphql.nadel.engine.util.emptyOrSingle
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLFieldsContainer
import graphql.schema.GraphQLNamedType

data class NadelInstructionDefinitionRegistry(
    private val definitions: Map<NadelSchemaMemberCoordinates, List<NadelInstructionDefinition>>,
) {
    fun isHydrated(
        container: GraphQLFieldsContainer,
        field: GraphQLFieldDefinition,
    ): Boolean {
        return getHydrationDefinitions(container, field).any()
    }

    fun isHydrated(
        container: NadelServiceSchemaElement.FieldsContainer,
        field: GraphQLFieldDefinition,
    ): Boolean {
        return getHydrationDefinitionSequence(coords(container, field)).any()
    }

    fun getHydrationDefinitions(
        container: NadelServiceSchemaElement.FieldsContainer,
        field: GraphQLFieldDefinition,
    ): Sequence<NadelHydrationDefinition> {
        return getHydrationDefinitionSequence(coords(container, field))
    }

    fun getHydrationDefinitions(
        container: GraphQLFieldsContainer,
        field: GraphQLFieldDefinition,
    ): Sequence<NadelHydrationDefinition> {
        return getHydrationDefinitionSequence(container.coordinates().field(field.name))
    }

    fun getHydrationDefinitionSequence(
        coords: NadelSchemaMemberCoordinates.Field,
    ): Sequence<NadelHydrationDefinition> {
        val definitions = definitions[coords]
            ?: return emptySequence()

        return definitions.asSequence()
            .filterIsInstance<NadelHydrationDefinition>()
    }

    fun isRenamed(
        container: NadelServiceSchemaElement.Type,
    ): Boolean {
        return getRenamedOrNull(container.overall) != null
    }

    fun isRenamed(
        container: GraphQLFieldsContainer,
        field: GraphQLFieldDefinition,
    ): Boolean {
        return getRenamedOrNull(container, field) != null
    }

    fun isRenamed(
        container: NadelServiceSchemaElement.FieldsContainer,
        field: GraphQLFieldDefinition,
    ): Boolean {
        return getRenamedOrNull(coords(container, field)) != null
    }

    fun getRenamedOrNull(
        container: GraphQLFieldsContainer,
        field: GraphQLFieldDefinition,
    ): NadelRenamedDefinition.Field? {
        val coords = container.coordinates().field(field.name)
        return getRenamedOrNull(coords)
    }

    fun getRenamedOrNull(
        container: NadelServiceSchemaElement.FieldsContainer,
        field: GraphQLFieldDefinition,
    ): NadelRenamedDefinition.Field? {
        return getRenamedOrNull(coords(container, field))
    }

    fun getRenamedOrNull(coords: NadelSchemaMemberCoordinates.Field): NadelRenamedDefinition.Field? {
        val definitions = definitions[coords]
            ?: return null

        return definitions.asSequence()
            .filterIsInstance<NadelRenamedDefinition.Field>()
            .firstOrNull()
    }

    fun getRenamedOrNull(
        container: GraphQLNamedType,
    ): NadelRenamedDefinition.Type? {
        return getRenamedOrNull(container.coordinates())
    }

    fun getRenamedOrNull(coords: NadelSchemaMemberCoordinates.Type): NadelRenamedDefinition.Type? {
        val definitions = definitions[coords]
            ?: return null

        return definitions.asSequence()
            .filterIsInstance<NadelRenamedDefinition.Type>()
            .firstOrNull()
    }

    fun getUnderlyingTypeName(type: GraphQLNamedType): String {
        return getRenamedOrNull(type)?.from ?: type.name
    }

    fun isPartitioned(
        container: GraphQLFieldsContainer,
        field: GraphQLFieldDefinition,
    ): Boolean {
        return getPartitionedOrNull(container, field) != null
    }

    fun isPartitioned(
        container: NadelServiceSchemaElement.FieldsContainer,
        field: GraphQLFieldDefinition,
    ): Boolean {
        return getPartitionedOrNull(coords(container, field)) != null
    }

    fun getPartitionedOrNull(
        container: NadelServiceSchemaElement.FieldsContainer,
        field: GraphQLFieldDefinition,
    ): NadelPartitionDefinition? {
        return getPartitionedOrNull(coords(container, field))
    }

    fun getPartitionedOrNull(
        container: GraphQLFieldsContainer,
        field: GraphQLFieldDefinition,
    ): NadelPartitionDefinition? {
        val coords = container.coordinates().field(field.name)
        return getPartitionedOrNull(coords)
    }

    fun getPartitionedOrNull(
        coords: NadelSchemaMemberCoordinates.Field,
    ): NadelPartitionDefinition? {
        val definitions = definitions[coords]
            ?: return null

        return definitions.asSequence()
            .filterIsInstance<NadelPartitionDefinition>()
            .firstOrNull()
    }

    fun hasDefaultHydration(
        type: NadelServiceSchemaElement.Type,
    ): Boolean {
        return getDefaultHydrationSequence(type.overall.coordinates()).any()
    }

    fun getDefaultHydrationOrNull(
        type: NadelServiceSchemaElement.Type,
    ): NadelDefaultHydrationDefinition? {
        return getDefaultHydrationSequence(type.overall.coordinates()).emptyOrSingle()
    }

    private fun getDefaultHydrationSequence(
        coords: NadelSchemaMemberCoordinates.Type,
    ): Sequence<NadelDefaultHydrationDefinition> {
        val definitions = definitions[coords]
            ?: return emptySequence()

        return definitions
            .asSequence()
            .filterIsInstance<NadelDefaultHydrationDefinition>()
    }

    private fun coords(
        container: NadelServiceSchemaElement.FieldsContainer,
        field: GraphQLFieldDefinition,
    ): NadelSchemaMemberCoordinates.Field {
        val containerCoords = when (container) {
            is NadelServiceSchemaElement.Interface -> NadelSchemaMemberCoordinates.Interface(container.overall.name)
            is NadelServiceSchemaElement.Object -> NadelSchemaMemberCoordinates.Object(container.overall.name)
        }

        return containerCoords.field(field.name)
    }
}
