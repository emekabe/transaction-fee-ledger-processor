package com.emeka.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigInteger

data class TransactionRequest(

    @param:JsonProperty("transaction_id")
    val transactionId: String,

    @param:JsonProperty("amount")
    val amount: BigInteger,

    @param:JsonProperty("asset")
    val asset: String,

    @param:JsonProperty("asset_type")
    val assetType: String,

    @param:JsonProperty("type")
    val type: String,

    @param:JsonProperty("state")
    val state: String,

    @param:JsonProperty("created_at")
    val createdAt: String
)
