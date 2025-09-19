package com.emeka.transaction

import com.emeka.enums.AccountType
import java.math.BigDecimal
import java.math.BigInteger

class ATMWithdrawalTransaction: Transaction {
    override fun getRate(): BigDecimal? {
        return null
    }

    override fun calculateFee(amount: BigInteger): BigInteger {
        // Standard Rate
        return BigInteger.valueOf(10)
    }

    override fun getDescription(): String {
        return "Fixed fee of USD 10"
    }

    override fun getDebitAccount(): AccountType {
        return AccountType.ALL_CUSTOMERS
    }

    override fun getCreditAccount(): AccountType {
        return AccountType.ATM_WITHDRAWAL_FEE
    }
}