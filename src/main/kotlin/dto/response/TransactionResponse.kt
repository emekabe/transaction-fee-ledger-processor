package com.emeka.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.math.BigInteger

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TransactionResponse(
    @param:JsonProperty("transaction_id")
    val transactionId: String,
    @param:JsonProperty("amount")
    val amount: BigInteger,
    @param:JsonProperty("asset")
    val asset: String,
    @param:JsonProperty("type")
    val type: String,
    @param:JsonProperty("fee")
    val fee: BigInteger,
    @param:JsonProperty("rate")
    val rate: BigDecimal?,
    @param:JsonProperty("description")
    val description: String
)
