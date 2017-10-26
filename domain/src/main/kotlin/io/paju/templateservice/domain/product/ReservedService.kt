package io.paju.templateservice.domain.product

import io.paju.templateservice.shared.DateRange
import java.util.*

/**
 * ReservedService is ENTITY representing instance of service that has been reserved for particular SalesOrder
 */

class ReservedService(override val price: Price,
                      override val name: String,
                      override val description: String,
                      val reservationPeriod: ReservationPeriod,
                      val reservedServiceId: ReservedServiceId): Product {
}

data class ReservedServiceId(val value: UUID)
class ReservationPeriod(start: Date, end: Date) : DateRange(start, end)