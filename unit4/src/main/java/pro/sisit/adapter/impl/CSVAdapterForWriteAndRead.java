package pro.sisit.adapter.impl;

import pro.sisit.adapter.IOAdapter;

public class CSVAdapterForWriteAndRead<T extends CSVAdapterForWriteAndRead> implements IOAdapter<T> {

    private IOAdapter<T> adapter;

    public CSVAdapterForWriteAndRead(IOAdapter<T> adapter) {
        this.adapter = adapter;
    }


    @Override
    public T read(int index) {
        return adapter.read(index);
    }

    @Override
    public int append(T entity) {
        return adapter.append(entity);
    }
}
