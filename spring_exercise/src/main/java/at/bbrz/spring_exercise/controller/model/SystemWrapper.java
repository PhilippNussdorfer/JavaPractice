package at.bbrz.spring_exercise.controller.model;

import org.springframework.stereotype.Service;

@Service
public class SystemWrapper {
    public Long timeStampMillis() {
        return System.currentTimeMillis();
    }
}
