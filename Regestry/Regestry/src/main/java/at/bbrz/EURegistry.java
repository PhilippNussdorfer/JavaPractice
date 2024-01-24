package at.bbrz;

public class EURegistry extends Registry {

    @Override
    protected void init() {
        add(new County("ISO 3166-2:AT", "Austria"));
        add(new County("ISO 3166-2:BY", "Belarus"));
        add(new County("ISO 3166-2:FR", "France"));
        add(new County("ISO 3166-2:DE", "Germany"));
        add(new County("ISO 3166-2:GR", "Greece"));
    }
}
