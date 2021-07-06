package extensions.javax.persistence.TypedQuery;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Extension
public class TypedQueryExtensions {
    public static <X> Optional<X> getSingleOrDefault(@This TypedQuery<X> item) {
        var results = item.getResultList();

        if (results == null) return Optional.empty();
        if (results.isEmpty()) return Optional.empty();

        if (results.size() == 1) return Optional.of(results.get(0));
        throw new NonUniqueResultException();
    }

    public static <X> X getSingle(@This TypedQuery<X> item) {
        var results = item.getResultList();

        if (results == null) throw new NoResultException();
        if (results.isEmpty()) throw new NoResultException();


        if (results.size() == 1) return results.get(0);
        throw new NonUniqueResultException();
    }

    public static <X> Optional<X> getFirstOrDefault(@This TypedQuery<X> item) {
        var results = item.getResultList();

        if (results == null) return Optional.empty();
        if (results.isEmpty()) return Optional.empty();

        return Optional.of(results.get(0));
    }

    public static <X> X getFirst(@This TypedQuery<X> item) {
        var results = item.getResultList();

        if (results == null) throw new NoResultException();
        if (results.isEmpty()) throw new NoResultException();

        return results.get(0);
    }

    public static <X> Optional<X> getElementAtOrDefault(@This TypedQuery<X> item, int index) {
        if(index < 0) throw new IndexOutOfBoundsException();

        var results = item.getResultList();

        if (results == null) return Optional.empty();
        if (results.isEmpty()) return Optional.empty();

        if (results.size() > index) return Optional.of(results.get(index));
        return Optional.empty();
    }

    public static <X> X getElementAt(@This TypedQuery<X> item, int index) {
        if(index < 0) throw new IndexOutOfBoundsException();

        var results = item.getResultList();

        if (results == null) throw new NoResultException();
        if (results.isEmpty()) throw new NoResultException();

        if (results.size() > index) return results.get(index);
        throw new IndexOutOfBoundsException();
    }
}