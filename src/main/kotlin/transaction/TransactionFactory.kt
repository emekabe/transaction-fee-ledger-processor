package com.emeka.transaction

import com.emeka.constants.*

fun getTransaction(type: String): Transaction {
    if (type.equals(MOBILE_TOP_UP, ignoreCase = true)) {
        return MobileTopUpTransaction()
    }

    if (type.equals(UTILITY_BILL_PAYMENT, ignoreCase = true)) {
        return UtilityBillPaymentTransaction()
    }

    if (type.equals(ATM_WITHDRAWAL, ignoreCase = true)) {
        return ATMWithdrawalTransaction()
    }

    throw IllegalArgumentException("Unsupported transaction type")
}