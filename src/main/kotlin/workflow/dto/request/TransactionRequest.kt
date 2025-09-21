package com.emeka.workflow.dto.request

import com.emeka.workflow.dto.BigIntegerSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigInteger

@Serializable
data class TransactionRequest(

    val transaction_id: String,

    @Contextual
    @Serializable(with = BigIntegerSerializer::class)
    val amount: BigInteger,

    val asset: String,

    val asset_type: String,

    val type: String,

    val state: String,

    val created_at: String
)
