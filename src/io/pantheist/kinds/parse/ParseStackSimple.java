package io.pantheist.kinds.parse;

import java.util.ArrayList;
import java.util.List;

public final class ParseStackSimple<S> implements IParseStack<S>
{
	List<S> list;

	public ParseStackSimple()
	{
		this.list = new ArrayList<>();
	}

	@Override
	public void push(final S obj)
	{
		list.add(obj);
	}

	@Override
	public S pop() throws ParserException
	{
		if (list.isEmpty())
		{
			throw new ParserException("Stack is empty");
		}
		return list.remove(list.size() - 1);
	}

	@Override
	public boolean isEmpty()
	{
		return list.isEmpty();
	}

}
