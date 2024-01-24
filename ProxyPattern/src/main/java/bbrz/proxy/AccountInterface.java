package bbrz.proxy;

public interface AccountInterface {

    public void deposit(double currency);
    public double draw(double currency);
    public double balance();
}
