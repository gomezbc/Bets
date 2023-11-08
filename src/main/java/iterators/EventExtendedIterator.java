package iterators;

import domain.Event;

import java.util.List;
import java.util.NoSuchElementException;

public class EventExtendedIterator implements ExtendedIterator<Event> {

    private int cursor = -1;
    private final int size;
    private final List<Event> events;

    public EventExtendedIterator(List<Event> events) {
        this.events = events;
        this.size = events.size()-1;
    }

    @Override
    public boolean hasNext() {
        return cursor != size;
    }

    @Override
    public Event next() {
        int i = cursor;
        if (i >= size)
            throw new NoSuchElementException();
        cursor = i + 1;
        return events.get(cursor);
    }

    @Override
    public Event previous() {
        int i = cursor;
        if (i >= size && i<=0)
            throw new NoSuchElementException();
        cursor = i - 1;
        return events.get(cursor);
    }

    @Override
    public boolean hasPrevious() {
        return cursor != 0;
    }

    @Override
    public void goFirst() {
        cursor = -1;
    }

    @Override
    public void goLast() {
        cursor = size+1;
    }
}
