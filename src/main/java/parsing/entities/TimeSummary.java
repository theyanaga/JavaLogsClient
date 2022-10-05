package parsing.entities;

import java.time.Duration;
import java.time.ZonedDateTime;

public class TimeSummary {


    private final ZonedDateTime start;
    private final ZonedDateTime finish;
    private final String duration;

    public TimeSummary(ZonedDateTime start, ZonedDateTime finish) {
        this.start = start;
        this.finish = finish;
        Duration duration = Duration.between(start, finish);
        this.duration = String.format("%s d %s h %s m %s s", duration.toDaysPart(), duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getFinish() {
        return finish;
    }

    public String getDuration() {
        return duration;
    }
}
