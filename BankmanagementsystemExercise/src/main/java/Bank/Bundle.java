package Bank;

import Bank.commandInterpretor.Interpreter;
import Bank.person.Session;
import lombok.Getter;

@Getter
public class Bundle {
    public boolean isRunning = true;
    private Interpreter interpreter = null;
    @Getter
    private final Session session;

    public Bundle(Session session) {
        this.session = session;
    }

    public void addInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }
}
