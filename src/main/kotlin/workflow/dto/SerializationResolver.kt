package com.emeka.workflow.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.math.BigDecimal
import java.math.BigInteger

object BigIntegerSerializer : KSerializer<BigInteger> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("BigInteger", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: BigInteger) {
        encoder.encodeInt(value.toInt())
    }

    override fun deserialize(decoder: Decoder): BigInteger {
        return BigInteger(decoder.decodeInt().toString())
    }
}

object BigDecimalSerializer : KSerializer<BigDecimal> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("BigDecimal", PrimitiveKind.DOUBLE)

    override fun serialize(encoder: Encoder, value: BigDecimal) {
        encoder.encodeDouble(value.toDouble())
    }

    override fun deserialize(decoder: Decoder): BigDecimal {
        return BigDecimal(decoder.decodeDouble())
    }
}