package com.emeka.transaction

import com.emeka.constants.FEE_TRANSFER_CODE
import com.emeka.enums.AccountType
import java.math.BigDecimal
import java.math.BigInteger

class UtilityBillPaymentTransaction : Transaction {
    override fun getRate(): BigDecimal {
        return BigDecimal.valueOf(0.002)
    }

    override fun calculateFee(amount: BigInteger): BigInteger {
        return getRate().multiply(amount.toBigDecimal()).toBigInteger()
    }

    override fun getDescription(): String {
        return "Standard fee rate of 0.2%"
    }

    override fun getTransferCode(): Int {
        return FEE_TRANSFER_CODE
    }

    override fun getDebitAccount(): AccountType {
        return AccountType.ALL_CUSTOMERS
    }

    override fun getCreditAccount(): AccountType {
        return AccountType.UTILITY_BILL_PAYMENT_FEE
    }
}