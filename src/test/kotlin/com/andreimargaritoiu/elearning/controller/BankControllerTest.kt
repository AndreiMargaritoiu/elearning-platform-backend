package com.andreimargaritoiu.elearning.controller

import com.andreimargaritoiu.elearning.model.models.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
        val mockMvc: MockMvc,
        val objectMapper: ObjectMapper
) {

    val baseUrl = "/api/banks"

//    moved to constructor
//    @Autowired
//    lateinit var mockMvc: MockMvc // mock http request and delegates to controller
//
//    @Autowired
//    lateinit var objectMapper: ObjectMapper // serializing objects to json

    @Nested
    @DisplayName("getBanks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {

        @Test
        fun `should return all banks` () {
            // when & then
            mockMvc.get(baseUrl)
                    .andDo { print() }
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$[0].accountNumber") { value("aa") }
                    }
        }
    }

    @Nested
    @DisplayName("getBank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {

        @Test
        fun `should return the bank with the given id` () {
            // given
            val accountNumber = "aa"

            // when & then
            mockMvc.get("$baseUrl/$accountNumber")
                    .andDo { print() }
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.trust") { value("0.1") }
                        jsonPath("$.transactionFee") { value("12") }
                    }
        }

        @Test
        fun `should return NOT FOUND if the account nr does not exist` () {
            // given
            val accountNumber = "dd"

            // when & then
            mockMvc.get("$baseUrl/$accountNumber")
                    .andDo { print() }
                    .andExpect {
                        status { isNotFound() }
                    }
        }
    }

    @Nested
    @DisplayName("addBank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddBank {

        @Test
        fun `should add a new bank` () {
            // given
            val newBank = Bank("nb", 41.2, 22)

            // when & then
            mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank) // serialize as a json representation
            }
                    .andDo { print() }
                    .andExpect {
                        status { isCreated() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.accountNumber") { value("nb") }
                        jsonPath("$.trust") { value("41.2") }
                        jsonPath("$.transactionFee") { value("22") }
                    }

            mockMvc.get("$baseUrl/${newBank.accountNumber}")
                    .andExpect { content { json(objectMapper.writeValueAsString(newBank)) } }

        }

        @Test
        fun `should return BAD REQUEST when trying to add an existing bank` () {
            // given
            val invalidBank = Bank("aa", 41.2, 22)

            // when & then
            mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank) // serialize as a json representation
            }
                    .andDo { print() }
                    .andExpect {
                        status { isBadRequest() }
                    }
        }
    }

    @Nested
    @DisplayName("updateBank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateBank {

        @Test
        fun `should update an existing bank` () {
            // given
            val updatedBank = Bank("aa", 10.5, 22)

            // when & then
            mockMvc.patch("$baseUrl/${updatedBank.accountNumber}") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank) // serialize as a json representation
            }
                    .andDo { print() }
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.accountNumber") { value(updatedBank.accountNumber) }
                        jsonPath("$.trust") { value(updatedBank.trust) }
                        jsonPath("$.transactionFee") { value(updatedBank.transactionFee) }
                    }

            mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
                    .andExpect { content { json(objectMapper.writeValueAsString(updatedBank)) } }
        }

        @Test
        fun `should return NOT FOUND when trying to update a non existing bank` () {
            // given
            val invalidBank = Bank("dd", 41.2, 22)

            // when & then
            mockMvc.patch("$baseUrl/${invalidBank.accountNumber}") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank) // serialize as a json representation
            }
                    .andDo { print() }
                    .andExpect {
                        status { isNotFound() }
                    }
        }
    }



    @Nested
    @DisplayName("deleteBank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteBank {

        @Test
        fun `should delete an existing bank` () {
            // given
            val accountNumber = "aa"

            // when & then
            mockMvc.delete("$baseUrl/$accountNumber")
                    .andDo { print() }
                    .andExpect {
                        status { isNoContent() }
                    }

            mockMvc.get("$baseUrl/$accountNumber")
                    .andDo { print() }
                    .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return NOT FOUND when trying to delete a non existing bank` () {
            // given
            val invalidAccountNumber = "dd"

            // when & then
            mockMvc.delete("$baseUrl/$invalidAccountNumber")
                    .andDo { print() }
                    .andExpect {
                        status { isNotFound() }
                    }
        }
    }

}