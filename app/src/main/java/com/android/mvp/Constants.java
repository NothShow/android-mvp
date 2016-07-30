package com.android.mvp;

public class Constants {

    public static final String LOG_TAG = "oa-todo";

    public static final class ServerConfig {
        public final static int HOST_TYPE_DEVELOP = 0;
        public final static int HOST_TYPE_STAGING = 1;
        public final static int HOST_TYPE_RELEASE = 2;

        public final static String HTTP_HOST_DEVELOP = "http://develop.xm.test.sankuai.com";
        public final static String HTTP_HOST_STAGING = "http://dxw.st.sankuai.com";
        public final static String HTTP_HOST_RELEASE = "http://dxw.sankuai.com";

        public final static String API_DONE_LIST = "/oa/todo/api/done_list.json";

        public final static String API_UNDONE_LIST = "/oa/todo/api/undone_list.json";

        public final static String API_CREATE = "/oa/todo/api/create.json";

        public final static String API_CREATE_BY_MSG = "/oa/todo/api/create_bymsg.json";

        public final static String API_DETAIL = "/oa/todo/api/detail.json";

        public final static String API_DELETE = "/oa/todo/api/del.json";

        public final static String API_MODIFY = "/oa/todo/api/modify.json";

        public final static String API_REMIND_ON = "/oa/todo/api/remind_on.json";

        public final static String API_REMIND_OFF = "/oa/todo/api/remind_off.json";

        public final static String API_DONE = "/oa/todo/api/done.json";

        public final static String API_UNDONE = "/oa/todo/api/undone.json";

    }

    public final static int PAGE_COUNT = 50;


    public final static class KEY {
        public static final String KEY_ID = "id";
    }
}
