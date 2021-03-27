package com.andreimargaritoiu.elearning.datasource

import com.andreimargaritoiu.elearning.model.models.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>

    fun retrieveBank(accountNumber: String): Bank

    fun createBank(bank: Bank): Bank

    fun modifyBank(accountNumber: String, bank: Bank): Bank

    fun removeBank(accountNumber: String)

}