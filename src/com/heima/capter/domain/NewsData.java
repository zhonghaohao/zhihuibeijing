package com.heima.capter.domain;

import java.util.List;


public class NewsData {
	public String retcode;
	/**
	 * ����(���ţ���ͼ��ר���)��һ������
	 */
	public List<NewsMenuData> data;

	/**
	 * ���˵��ļ���ѡ��(���ţ���ͼ��ר��)
	 */
	public class NewsMenuData {
		public String id;
		public String title;
		public int type;
		public String url;
		public List<NewsTabData> children;
		@Override
		public String toString() {
			return "NewsMenuData [title=" + title + ", children=" + children
					+ "]";
		}
		
	}
	/**
	 * ������ĵ��������� 
	 *
	 */
	public class NewsTabData{
		public String id;
		public String title;
		public int type;
		/**
		 * ÿһ����������ӦҪ�������ݵ�url
		 */
		public String url;
		@Override
		public String toString() {
			return "NewsTabData [title=" + title + "]";
		}
		
	}

	@Override
	public String toString() {
		return "NewsData [data=" + data + "]";
	}
	
}
