package com.jngld.utils.kryo;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.esotericsoftware.kryo.Kryo;

public class KryoPoolableFactory extends BasePooledObjectFactory<Kryo>{

	@Override
	public Kryo create() throws Exception {
		//System.out.println("kryo create !");
		return new Kryo();
	}

	@Override
	public PooledObject<Kryo> wrap(Kryo obj) {
		return new DefaultPooledObject<Kryo>(obj);
	}

}
