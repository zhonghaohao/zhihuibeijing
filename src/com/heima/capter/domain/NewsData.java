package com.heima.capter.domain;

import java.util.List;


public class NewsData {
	public String retcode;
	/**
	 * 包含(新闻，组图，专题等)的一个数组
	 */
	public List<NewsMenuData> data;

	/**
	 * 左侧菜单的几个选项(新闻，组图，专题)
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
	 * 主界面的导航栏数据 
	 *
	 */
	public class NewsTabData{
		public String id;
		public String title;
		public int type;
		/**
		 * 每一个导航栏对应要请求数据的url
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
