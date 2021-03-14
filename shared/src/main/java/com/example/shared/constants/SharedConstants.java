package com.example.shared.constants;

public interface SharedConstants {

   String API = "api";
   String PAGE = "page";
   String PAGE_SIZE = "pageSize";
   String SORT_TYPE = "sortType";
   String SORT_BY = "sortBy";
   String DEFAULT_PAGE_SIZE = "10";
   String DEFAULT_PAGE_NUMBER = "0";
   String SORT_TYPE_ASC = "ASC";
   String SORT_TYPE_DESC = "DESC";
   String DEFAULT_SORT_TYPE = SORT_TYPE_ASC;
   String UPLOAD_DATE = "uploadDate";
   String DEFAULT_SORT_BY = UPLOAD_DATE;
   String USER_ID = "userId";
   String QUERY = "query";
   String FIELDS = "fields";
   String FEED_TYPE = "feedType";
   String TOKEN = "token";

   String KEY_AUTHORIZATION_TOKEN_HEADER_PREFIX = "authorization.token.header.prefix";
   String KEY_AUTHORIZATION_TOKEN_HEADER_NAME = "authorization.token.header.name";
   String KEY_TOKEN_SECRET = "token.secret";
   String KEY_TOKEN_EXPIRATION_TIME = "token.expiration_time";
   String KEY_API_REGISTRATION_URL_PATH = "api.registration.url.path";
   String KEY_API_LOGIN_URL_PATH = "api.login.url.path";
   String KEY_API_USERS_ACTUATOR_URL_PATH = "api.users.actuator.url.path";
   String KEY_API_ZUUL_ACTUATOR_URL_PATH = "api.zuul.actuator.url.path";
}
