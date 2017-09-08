package io.pantheist.kinds.misc;

import java.util.ArrayList;
import java.util.List;

public class Arrays
{
	public static <T> List<? extends T> toList(final T[] items)
	{
		final List<T> list = new ArrayList<>();
		for (final T item : items)
		{
			list.add(item);
		}
		return list;
	}

	public static <T> List<? extends T> reverse(final T[] items)
	{
		final List<T> list = new ArrayList<>();
		for (int i = items.length - 1; i >= 0; i--)
		{
			list.add(items[i]);
		}
		return list;
	}
}
