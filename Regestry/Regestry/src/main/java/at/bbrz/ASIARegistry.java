package at.bbrz;

public class ASIARegistry extends Registry {

    @Override
    protected void init() {
        add(new County("China", "ISO 3166-2:CN"));
        add(new County("Chile", "ISO 3166-2:CL"));
        add(new County("Hong Kong", "ISO 3166-2:HK"));
        add(new County("Indonesia", "ISO 3166-2:ID"));
        add(new County("Japan", "ISO 3166-2:JP"));
    }
}
