// package name ends with "extensions." + extended class name
package extensions.java.lang.Iterable;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Extension
public class IterableExtensions {
    public static <T> Set<T> toSet(@This Iterable<T> items) {
        var result = new HashSet<T>();
        items.forEach(result::add);
        return result;
    }

    public static <T> List<T> toList(@This Iterable<T> items) {
        var result = new ArrayList<T>();
        items.forEach(result::add);
        return result;
    }
}