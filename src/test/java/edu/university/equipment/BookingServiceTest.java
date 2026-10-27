package edu.university.equipment;
import static org.junit.jupiter.api.Assertions.*; import java.util.NoSuchElementException; import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
class BookingServiceTest {
 private BookingService service; @BeforeEach void setup(){service=new BookingService();}
 @Test void successfulBookingReducesInventory(){service.create("STU1001","LAPTOP01",2);assertEquals(8,service.getEquipment("LAPTOP01").getAvailableQuantity());}
 @Test void zeroQuantityRejected(){assertThrows(IllegalArgumentException.class,()->service.create("STU1001","LAPTOP01",0));assertEquals(10,service.getEquipment("LAPTOP01").getAvailableQuantity());}
 @Test void blankBorrowerRejected(){assertThrows(IllegalArgumentException.class,()->service.create(" ","LAPTOP01",1));}
 @Test void unknownEquipmentRejected(){assertThrows(NoSuchElementException.class,()->service.create("STU1001","UNKNOWN",1));}
 @Test void ordinaryOverbookingRejected(){assertThrows(IllegalStateException.class,()->service.create("STU1001","PROJECTOR01",6));assertEquals(4,service.getEquipment("PROJECTOR01").getAvailableQuantity());}
 @Test void cancellationRestoresInventoryOnce(){Booking b=service.create("STU1001","TABLET01",3);service.cancel(b.getId());assertEquals(8,service.getEquipment("TABLET01").getAvailableQuantity());assertThrows(IllegalStateException.class,()->service.cancel(b.getId()));}
 @Test
void bookingExactAvailableInventorySucceeds() {
    // PROJECTOR01 initial capacity is 4
    Booking b = service.create("STU1001", "PROJECTOR01", 4);
    assertEquals(0, service.getEquipment("PROJECTOR01").getAvailableQuantity());
    assertNotNull(b);
}

@Test
void bookingOneMoreThanAvailableIsRejected() {
    int initialAvailable = service.getEquipment("PROJECTOR01").getAvailableQuantity(); // 4
    
    // Attempt to book 5 (available + 1)
    assertThrows(IllegalStateException.class, () -> service.create("STU1001", "PROJECTOR01", initialAvailable + 1));
    
    // 1. Verify inventory is NOT changed
    assertEquals(initialAvailable, service.getEquipment("PROJECTOR01").getAvailableQuantity());
}
@Test
void doubleCancellationDoesNotRestoreInventoryTwice() {
    // 1. Create a booking (e.g., 2 laptops)
    Booking b = service.create("STU1001", "LAPTOP01", 2);
    int initialAvailableAfterBooking = service.getEquipment("LAPTOP01").getAvailableQuantity(); // 8

    // 2. First cancellation: should succeed, update status, and restore inventory by 2
    Booking cancelledBooking = service.cancel(b.getId());
    assertEquals("CANCELLED", cancelledBooking.getStatus());
    assertEquals(initialAvailableAfterBooking + 2, service.getEquipment("LAPTOP01").getAvailableQuantity());

    // 3. Second cancellation attempt: must throw IllegalStateException
    assertThrows(IllegalStateException.class, () -> service.cancel(b.getId()));

    // 4. Verify status remains CANCELLED and inventory is unchanged
    assertEquals("CANCELLED", service.getBooking(b.getId()).getStatus());
    assertEquals(initialAvailableAfterBooking + 2, service.getEquipment("LAPTOP01").getAvailableQuantity());
}
}
