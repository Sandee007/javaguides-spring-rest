package com.example.javaguidesblog.util;

public class ApiConfig {
    public static final String BASE = "api/";
    private static final String _create = "create";

    public static final class Posts {
        public static final String INDEX = "posts";
        public static final class IndexConfig {
            public static final String DEFAULT_PAGE_NUMBER = "0";
            public static final String DEFAULT_PAGE_SIZE = "10";
            public static final String DEFAULT_SORT_BY = "id";
            public static final String DEFAULT_SORT_DIRECTION = "asc";
        }
    }

    public static final class Comments{
        public  static final String INDEX = Posts.INDEX + "/{postId}/comments";
    }
}
