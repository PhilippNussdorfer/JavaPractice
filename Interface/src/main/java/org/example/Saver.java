package org.example;

import org.example.Interfaces.SaveAble;

import java.util.List;

public class Saver {
    public static boolean saveAll(List<SaveAble> saveList) {
        boolean result = false;
        for (SaveAble saveAble : saveList) {
            result = saveAble.save();
        }
        return result;
    }
}
