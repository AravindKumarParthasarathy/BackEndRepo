package com.msciq.storage.model.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * <p>
 * Generic success response
 * </p>
 *
 * @author Sivaranjani DS
 *
 * @param <T>
 */

public class SuccessResponse<T> extends ResponseEntity<Object> {

	public SuccessResponse(String message, Object entity, List<T> entityList,
						   HttpStatus httpStatus) {
		super(new SuccessResponseMessage<T>(Boolean.TRUE, message, entity, entityList,
				Integer.valueOf(httpStatus.value())), httpStatus);
	}

}
