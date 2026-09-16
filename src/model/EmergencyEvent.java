package model;
import java.time.LocalDateTime;
public class EmergencyEvent {
    private final EventType eventType;
    private final LocalDateTime timestamp;
    private final int patientId;
    private final String description;
// this gives patient emergency description along with date and time
    public EmergencyEvent(
        EventType eventType,
        LocalDateTime timestamp,
        int patientId,
      String description) {
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.patientId = patientId;
        this.description = description;
         }public EventType getEventType() {
        return eventType;
    } public LocalDateTime getTimestamp() {
        return timestamp;
    }
  public int getPatientId() {
        return patientId;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
 return "[" + timestamp + "] "
                + eventType
                + " | Patient: " + patientId
                + " | " + description;
    }
}
