package com.emeka.enums

import com.emeka.constants.*

enum class AccountType(
    val id: Long,
    val ledger: Int,
    val code: Int,
    val userData64: Long,
    val classification: AccountClassification
) {
    MOBILE_TOP_UP_FEE(
        id = MOBILE_TOP_UP_FEE_ID,
        ledger = USD_FIAT_LEDGER,
        code = MOBILE_TOP_UP_FEE_CODE,
        userData64 = SYSTEM_USER_DATA,
        classification = AccountClassification.REVENUE
    ),
    UTILITY_BILL_PAYMENT_FEE(
        id = UTILITY_BILL_PAYMENT_FEE_ID,
        ledger = USD_FIAT_LEDGER,
        code = UTILITY_BILL_PAYMENT_FEE_CODE,
        userData64 = SYSTEM_USER_DATA,
        classification = AccountClassification.REVENUE
    ),
    ATM_WITHDRAWAL_FEE(
        id = ATM_WITHDRAWAL_FEE_ID,
        ledger = USD_FIAT_LEDGER,
        code = ATM_WITHDRAWAL_FEE_CODE,
        userData64 = SYSTEM_USER_DATA,
        classification = AccountClassification.REVENUE
    ),
    ALL_CUSTOMERS(
        id = ALL_CUSTOMERS_ID,
        ledger = USD_FIAT_LEDGER,
        code = ALL_CUSTOMERS_CODE,
        userData64 = SYSTEM_USER_DATA,
        classification = AccountClassification.LIABILITY
    );

}