package org.example.Interfaces;

import java.io.BufferedReader;

public interface Persistance<T, K> {
    void save(T object);
    T load(K key, BufferedReader reader);
}
