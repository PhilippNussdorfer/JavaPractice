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
    @Getter
    private final BankManagementSystem system;

    public Bundle(Session session, Bundle oldBundle) {
        this.session = session;
        this.system = oldBundle.getSystem();
    }

    public Bundle(Session session, BankManagementSystem system) {
        this.session = session;
        this.system = system;
    }

    public void addInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }
}
