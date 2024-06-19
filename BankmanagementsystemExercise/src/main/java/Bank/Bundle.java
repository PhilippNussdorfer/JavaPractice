package Bank;

import Bank.commandInterpretor.Interpreter;
import Bank.person.Session;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Bundle {
    @Setter
    private boolean isRunning = true;
    private Interpreter interpreter = null;
    private final Session session;
    private final BankManagementSystem system;

    public Bundle(Session session, Bundle oldBundle) {
        this.session = session;
        this.system = oldBundle.getSystem();
    }

    public Bundle(Session session, BankManagementSystem system) {
        this.session = session;
        this.system = system;
    }

    public void setInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }
}
