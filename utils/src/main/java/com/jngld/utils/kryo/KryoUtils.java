package com.jngld.utils.kryo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.jngld.utils.entity.IpInfo;

/**
 * kryo序列化工具类
 * @author wangzz-a
 * @version $Id: KryoUtils.java, v 0.1 2015年12月28日 下午4:43:40 wangzz-a Exp $
 */
public class KryoUtils {

	/**
	 * 对象序列化为字节数组
	 * @author wangzz-a
	 * @param obj
	 * @return byte[]
	 * @throws Exception 
	 * @date 2015年12月24日 下午2:34:37
	 */
	public static byte[] serialize(Object obj) throws Exception {
		
		//在对象池中借用kryo对象
		Kryo kryo = KryoPoolUtil.getKryo();
		
		Output output = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			output = new Output(baos);
			kryo.writeClassAndObject(output, obj);
			output.flush();
			byte[] s = output.getBuffer();
			return s;
		} finally {
			if (output != null)
				output.close();
			//归还kryo对象
			KryoPoolUtil.returnKryo(kryo);
		}
	}

	/**
	 * 数组反序列化为Object对象
	 * @author wangzz-a
	 * @param bits
	 * @return Object
	 * @throws Exception 
	 * @date 2015年12月24日 下午2:35:15
	 */
	public static Object deserialize(byte[] bits) throws Exception {
		//在对象池中借用kryo对象
		Kryo kryo = KryoPoolUtil.getKryo();
		
		if (bits == null || bits.length == 0)
			return null;
		
		Input ois = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bits);
			ois = new Input(bais);
			return kryo.readClassAndObject(ois);
		} finally {
			if (ois != null)
				ois.close();
			//归还kryo对象
			KryoPoolUtil.returnKryo(kryo);
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		IpInfo ipInfo = new IpInfo();
		ipInfo.setCountry("中国");
		ipInfo.setProvince("山东省");
		ipInfo.setCity("济南");
		ipInfo.setDistrict("111110");
		ipInfo.setCarrier("小联通");
		
		byte[] bits = serialize(ipInfo);
		System.out.print("[");
		for (byte b : bits) {
			System.out.print(Byte.toString(b) + ", ");
		}
		System.out.print("] \n");
		IpInfo ipInfo2 = (IpInfo) deserialize(bits);
		System.out.println(ipInfo2.getCarrier());
		
	}

}
