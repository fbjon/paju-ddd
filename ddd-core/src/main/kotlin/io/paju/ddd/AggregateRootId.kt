package io.paju.ddd

import java.io.Serializable
import java.util.UUID

class AggregateRootId constructor(val id: UUID) : Serializable {

    override fun toString(): String {
        return id.toString()
    }

    fun toUUID(): UUID {
        return id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AggregateRootId

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        fun random() = AggregateRootId(UUID.randomUUID())
    }
}