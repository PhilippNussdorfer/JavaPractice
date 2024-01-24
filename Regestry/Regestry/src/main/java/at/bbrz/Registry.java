package at.bbrz;

import java.util.ArrayList;
import java.util.List;

public abstract class Registry implements GetRegistry {
    private List<County> countyList = new ArrayList<>();

    public Registry() {
        init();
    }

    protected abstract void init();

    protected void add(County county) {
        countyList.add(county);
    }

    @Override
    public void getCountry() {
        for (County county : countyList) {
            System.out.println(county);
        }
    }
}