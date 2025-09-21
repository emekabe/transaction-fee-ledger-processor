package com.emeka.workflow.dto.response

import com.emeka.workflow.dto.BigDecimalSerializer
import com.emeka.workflow.dto.BigIntegerSerializer
import com.fasterxml.jackson.annotation.JsonInclude
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.math.BigInteger

@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class TransactionResponse(

    val transaction_id: String,

    @Contextual
    @Serializable(with = BigIntegerSerializer::class)
    val amount: BigInteger,

    val asset: String,

    val type: String,

    @Serializable(with = BigIntegerSerializer::class)
    val fee: BigInteger,

    @Serializable(with = BigDecimalSerializer::class)
    val rate: BigDecimal?,

    val description: String
)
