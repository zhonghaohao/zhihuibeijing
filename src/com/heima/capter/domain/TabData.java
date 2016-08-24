package com.heima.capter.domain;

import java.util.ArrayList;

/**
 * ҳǩ����ҳ����
 * 
 * @author Kevin
 * 
 */
public class TabData {

	public int retcode;

	public TabDetail data;

	public class TabDetail {
		public String title;
		public String more;
		public ArrayList<TabNewsData> news;
		public ArrayList<TopNewsData> topnews;

		@Override
		public String toString() {
			return "TabDetail [title=" + title + ", news=" + news
					+ ", topnews=" + topnews + "]";
		}
	}

	/**
	 * �����б����
	 * 
	 * @author Kevin
	 * 
	 */
	public class TabNewsData {
		public String id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;

		@Override
		public String toString() {
			return "TabNewsData [title=" + title + "]";
		}
	}

	/**
	 * ͷ������
	 * 
	 * @author Kevin
	 * 
	 */
	public class TopNewsData {
		public String id;
		public String topimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;

		@Override
		public String toString() {
			return "TopNewsData [title=" + title + "]";
		}
	}

	@Override
	public String toString() {
		return "TabData [data=" + data + "]";
	}

}
