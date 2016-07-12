package de.hhu.propra16.tddt.trainer;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

class BabySteps {
    private Timer timerDisplay;
    private Timer timer;
    private Duration duration;

    BabySteps(Duration duration) {
        this.duration = duration;
    }


    void timer(Trainer trainer) {
        Instant finishTime = Instant.now().plus(duration);

        timerDisplay = new Timer();
        timerDisplay.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                trainer.setTimeLeft(Duration.between(Instant.now(), finishTime));
            }
        }, 0, 1000);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                trainer.reset();
            }
        }, duration.toMillis());
    }

    void cancel() {
        if (!(timerDisplay == null)) timerDisplay.cancel();
        if (!(timer == null)) timer.cancel();
    }
}
