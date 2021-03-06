package io.paju.salesorder.domain

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.paju.salesorder.domain.SalesOrderTestData.customerId
import io.paju.salesorder.domain.SalesOrderTestData.makeSalesOrder
import io.paju.salesorder.domain.SalesOrderTestData.product1
import io.paju.salesorder.domain.SalesOrderTestData.product2
import io.paju.salesorder.domain.SalesOrderTestData.product3
import io.paju.salesorder.service.DummyPaymentService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class SalesOrderTest {

    private val paymentServiceMock = mock<DummyPaymentService>()

    @Test
    fun addProduct() {
        val so: SalesOrder = makeSalesOrder(product1)
        assertEquals(1, so.products().size)
        assertEquals(product1, so.products().first())
        assertEquals(1, so.products(PaymentStatus.OPEN).size)
    }

    @Test
    fun removeProduct() {
        val so: SalesOrder = makeSalesOrder(product1, product2, product3)
        so.removeProduct(product2)

        assertEquals(2, so.products().size)
        assertNotNull(so.products().find { it == product1 })
        assertNotNull(so.products().find { it == product3 })
    }

    @Test
    fun deliverProduct() {
        val so: SalesOrder = makeSalesOrder(product1, product2, product3)
        so.deliverProduct(product1)

        assertEquals(3, so.products().size)
        assertEquals(1, so.products(DeliveryStatus.DELIVERED).size)
        assertEquals(2, so.products(DeliveryStatus.NOT_DELIVERED).size)
    }

    @Test
    fun invoiceDeliveredProductsAndServices() {
        val so: SalesOrder = makeSalesOrder(product1, product2, product3)
        so.deliverProduct(product1)

        so.invoiceDeliveredProducts(paymentServiceMock)
        verify(paymentServiceMock, times(1))
            .handleProductPayment(product1, customerId, PaymentMethod.INVOICE)
    }

    @Test
    fun payDeliveredProduct() {
        val so: SalesOrder = makeSalesOrder(product1, product2, product3)
        so.deliverProduct(product1)

        so.payDeliveredProduct(paymentServiceMock, product1, PaymentMethod.CASH)
        verify(paymentServiceMock, times(1))
            .handleProductPayment(product1, customerId, PaymentMethod.CASH)
    }

}