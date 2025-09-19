package com.emeka.service

import com.emeka.dto.request.TransactionRequest
import com.emeka.dto.response.TransactionResponse
import com.emeka.transaction.Transaction
import com.emeka.transaction.getTransaction

class FeeService (
    val accountingService: AccountingService
){

    fun chargeFee(request: TransactionRequest): TransactionResponse{
        val transaction: Transaction = getTransaction(request.type)

        val fee = transaction.calculateFee(request.amount)

        accountingService.transfer(transaction, fee)

        return TransactionResponse(
            transactionId  = request.transactionId,
            amount = request.amount,
            asset = request.asset,
            type = request.type,
            fee = fee,
            rate = transaction.getRate(),
            description = transaction.getDescription(),
        )
    }

}