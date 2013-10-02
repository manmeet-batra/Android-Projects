package com.example.adwaz.constant;

public class Constants {

	public static final String ABOUTUS_FRAGMENT_TAG = "about_us";
	public static final String LOGIN_FRAGMENT_TAG = "login fragment";
	public static final String REGISTER_FRAGMENT_TAG = "register fragment";
	public static final String REGISTER_CUSTOMER_FRAGMENT_TAG = "register customer fragment";
	public static final String HOME_FRAGMENT_TAG = "home fragment";
	public static final String REGISTER_OPTION_FRAGMENT_TAG = "register option fragment";
	public static final String SEARCH_FRAGMENT_TAG = "Search fragment";
	public static final String PROFILE_FRAGMENT_TAG = "Profile fragment";
	public static final String BASE_URL = "http://184.168.55.132/adwaz_business_connections/services.php?";
	public static final String REGISTRATION_CATEGORIES_URL = BASE_URL
			+ "command=get_categories";
	public static final String REGISTRATION_SUB_CATEGORIES_URL = BASE_URL
			+ "command=get_subcategories";
	public static final String PROFILE_DETAIL_URL = BASE_URL
			+ "command=owner_details&id=";

	public static final int REGISTRATION_CATEGORIES_RESPONSEID = 1004;
	public static final int REGISTRATION_SUB_CATEGORIES_RESPONSEID = 1005;
	public static final int LOGIN_RESPONSEID = 1001;
	public static final int REGISTRATION_RESPONSEID = 1002;
	public static final int FORGOT_RESPONSEID = 1003;
	public static final int PROFILE_DATA_ID = 1006;

	/**
	 * Key-Value pair
	 */
	public static String EMAIL = "email";
	public static String PASSWORD = "password";
	public static String IS_CHECKED = "is_checked";

	/** Get customer details */
	public static final String GET_CUSTOMER_URL = BASE_URL
			+ "command=get_customers&id=";
	public static final int GET_CUSTOMER_RESPONSEID = 1006;

	/** Post user image */
	public static final String POST_USER_IMAGE = "http://adwaz.us/adwaz_business_connections/upload_logo.php";
	public static String KEY_SHARED_USERID = "userId";

	/**
	 * Customer Registration Constants
	 */
	public static String KEY_CUSTOMER_REGISTERED = "customer_registered";
}
