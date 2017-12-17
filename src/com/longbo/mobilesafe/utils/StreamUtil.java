package com.longbo.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {

	/**��ת�����ַ���
	 * @param is ������
	 * @return ��ת���ɵ��ַ��� ����null�����쳣
	 */
	public static String streamToString(InputStream is) {
		//1.�ڶ�ȡ�Ĺ����У�����ȡ�����ݴ洢�ڻ����У�һ���Ե�ת�����ַ���
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		//2.�����Ĳ���������û��Ϊֹ
		byte[] buffer = new byte[1024];
		int temp = -1;
		try {
			while((temp = is.read(buffer))!= -1){
				bos.write(buffer, 0, temp);
			}
			//���ض�ȡ������
			return bos.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				is.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
