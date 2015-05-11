package com.tomkasp.api;

import java.util.UUID;

/**
 * 
 * @author Adib Saikali
 */
public class ApiErrorCodes {
	//@formatter:off
	
	public final static UUID UANBLE_TO_PARSE_REQUEST    = UUID.fromString("a05500d4-f689-4cd7-a814-df83c0effb5c");
	public final static UUID BIND_EXCEPTION             = UUID.fromString("9314486f-27b2-455b-a352-5c8fa69fdbe9");
	public final static UUID TYPE_MISMATCH_EXCEPTION    = UUID.fromString("ef1afabc-1545-458e-a380-cbe552ea7077");
	public final static UUID INVALID_REQUEST_BODY       = UUID.fromString("062952b8-257f-4fb5-9baf-2d2932375f9d");
	
	public final static UUID UNHANDLED_SERVER_EXCEPTION = UUID.fromString("434f49d8-575f-4449-ad49-7f3daf45194f");
	public final static UUID BAD_SERVER_URL             = UUID.fromString("253f6647-f9d4-4ae0-929a-a5ea1607c908");
	public final static UUID SESSION_EXPIRED            = UUID.fromString("cdbfa4c6-9ae6-46da-aef7-fa167f689afe");
	public final static UUID API_LOGIN_FAILURE          = UUID.fromString("ea069af4-ff3e-4c26-86b5-7b3165a81afc");
	//@formatter:on

	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString());
	}
}
