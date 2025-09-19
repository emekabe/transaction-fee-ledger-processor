package com.emeka.transaction

import com.emeka.enums.AccountType
import java.math.BigDecimal
import java.math.BigInteger

interface Transaction {

    fun getRate(): BigDecimal?

    fun calculateFee(amount: BigInteger): BigInteger

    fun getDescription(): String

    fun getDebitAccount(): AccountType

    fun getCreditAccount(): AccountType

}