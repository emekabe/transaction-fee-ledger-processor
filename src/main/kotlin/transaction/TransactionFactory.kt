package com.emeka.transaction

fun getTransaction(type: String): Transaction {
    if (type.equals("Mobile Top Up", ignoreCase = true)) {
        return MobileTopUpTransaction()
    }

    if (type.equals("Utility Bill Payment", ignoreCase = true)) {
        return UtilityBillPaymentTransaction()
    }

    if (type.equals("ATM Withdrawal", ignoreCase = true)) {
        return ATMWithdrawalTransaction()
    }

    throw IllegalArgumentException("Unsupported transaction type")
}