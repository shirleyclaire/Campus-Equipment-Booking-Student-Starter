This document tracks feature improvements, state management fixes, and deployment procedures for the Campus Equipment Booking system.
## State Consistency Rules
1. Equipment availability must update immediately upon booking request.
2. Concurrent booking requests must be handled safely to prevent double-booking.
3. System logs must capture state transition errors for auditing.
