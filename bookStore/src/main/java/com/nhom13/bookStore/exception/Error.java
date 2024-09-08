package com.nhom13.bookStore.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum Error {
    NOT_FOUND(404, "Resource not found", HttpStatus.NOT_FOUND), //Resource not found
    BAD_REQUEST(400, "Bad request", HttpStatus.BAD_REQUEST), //Syntax error or malformed request
    UNAUTHORIZED(401, "Unauthorized", HttpStatus.UNAUTHORIZED), // unauthenticated user
    FORBIDDEN(403, "Forbidden", HttpStatus.FORBIDDEN), //The user does not have permission to access the resource
    CONFLICT(409, "Conflict", HttpStatus.CONFLICT), // Resource state conflicts. For example, it can happen when trying to create a duplicate record or update data that is being edited at the same time by someone else.
    //Server Error
    UNCATEGORIZED_EXCEPTION(9999, "Unclassified error", HttpStatus.INTERNAL_SERVER_ERROR),
    //Database Error
    DATABASE_ACCESS_ERROR(9998, "Database access error", HttpStatus.INTERNAL_SERVER_ERROR),
    DUPLICATE_KEY(9996, "Duplicate key found", HttpStatus.CONFLICT),
    EMPTY_RESULT(9995, "No result found", HttpStatus.NOT_FOUND),
    NON_UNIQUE_RESULT(9994, "Non-unique result found", HttpStatus.CONFLICT),
    // MYSQL exception
    // MySQL-related errors
    MYSQL_CONNECTION_FAILURE(3001, "MySQL connection failed", HttpStatus.INTERNAL_SERVER_ERROR),
    MYSQL_DUPLICATE_KEY_ERROR(3002, "MySQL duplicate key error", HttpStatus.CONFLICT),
    MYSQL_VALIDATION_ERROR(3003, "MySQL validation error", HttpStatus.BAD_REQUEST),
    MYSQL_WRITE_CONCERN_ERROR(3004, "MySQL write operation error", HttpStatus.INTERNAL_SERVER_ERROR),
    MYSQL_TIMEOUT_ERROR(3005, "MySQL operation timed out", HttpStatus.INTERNAL_SERVER_ERROR),
    MYSQL_QUERY_EXECUTION_ERROR(3006, "MySQL query execution error", HttpStatus.INTERNAL_SERVER_ERROR),
    MYSQL_UNKNOWN_ERROR(3007, "Unknown MySQL error", HttpStatus.INTERNAL_SERVER_ERROR),
    // Cart exception
    CART_NOT_FOUND(2001, "Cart not found", HttpStatus.NOT_FOUND),
    CART_ALREADY_EXISTS(2002, "Cart already exists", HttpStatus.CONFLICT),
    CART_UNABLE_TO_SAVE(2003, "Unable to save cart", HttpStatus.INTERNAL_SERVER_ERROR),
    CART_UNABLE_TO_UPDATE(2004, "Unable to update cart", HttpStatus.INTERNAL_SERVER_ERROR),
    CART_UNABLE_TO_DELETE(2005, "Unable to delete cart", HttpStatus.INTERNAL_SERVER_ERROR),
    // Cart details exception
    CART_DETAILS_NOT_FOUND(2101, "Cart details not found", HttpStatus.NOT_FOUND),
    CART_DETAILS_ALREADY_EXISTS(2102, "Cart details already exists", HttpStatus.CONFLICT),
    CART_DETAILS_UNABLE_TO_SAVE(2103, "Unable to save cart details", HttpStatus.INTERNAL_SERVER_ERROR),
    CART_DETAILS_UNABLE_TO_UPDATE(2104, "Unable to update cart details", HttpStatus.INTERNAL_SERVER_ERROR),
    CART_DETAILS_UNABLE_TO_DELETE(2105, "Unable to delete cart details", HttpStatus.INTERNAL_SERVER_ERROR),
    // Inventory exceptions
    INVENTORY_NOT_FOUND(2201, "Inventory not found", HttpStatus.NOT_FOUND),
    INVENTORY_ALREADY_EXISTS(2202, "Inventory already exists", HttpStatus.CONFLICT),
    INVENTORY_UNABLE_TO_SAVE(2203, "Unable to save inventory", HttpStatus.INTERNAL_SERVER_ERROR),
    INVENTORY_UNABLE_TO_UPDATE(2204, "Unable to update inventory", HttpStatus.INTERNAL_SERVER_ERROR),
    INVENTORY_UNABLE_TO_DELETE(2205, "Unable to delete inventory", HttpStatus.INTERNAL_SERVER_ERROR),
    // Inventory Details exception
    INVENTORY_DETAILS_NOT_FOUND(2301, "Inventory details not found", HttpStatus.NOT_FOUND),
    INVENTORY_DETAILS_ALREADY_EXISTS(2302, "Inventory details already exists", HttpStatus.CONFLICT),
    INVENTORY_DETAILS_UNABLE_TO_SAVE(2303, "Unable to save inventory details", HttpStatus.INTERNAL_SERVER_ERROR),
    INVENTORY_DETAILS_UNABLE_TO_UPDATE(2304, "Unable to update inventory details", HttpStatus.INTERNAL_SERVER_ERROR),
    INVENTORY_DETAILS_UNABLE_TO_DELETE(2305, "Unable to delete inventory details", HttpStatus.INTERNAL_SERVER_ERROR),
    // Inventory status
    INVENTORY_STATUS_NOT_FOUND(2401, "Inventory status not found", HttpStatus.NOT_FOUND),
    INVENTORY_STATUS_ALREADY_EXISTS(2402, "Inventory status already exists", HttpStatus.CONFLICT),
    INVENTORY_STATUS_UNABLE_TO_SAVE(2403, "Unable to save inventory status", HttpStatus.INTERNAL_SERVER_ERROR),
    INVENTORY_STATUS_UNABLE_TO_UPDATE(2404, "Unable to update inventory status", HttpStatus.INTERNAL_SERVER_ERROR),
    INVENTORY_STATUS_UNABLE_TO_DELETE(2405, "Unable to delete inventory status", HttpStatus.INTERNAL_SERVER_ERROR),
    // Order exception
    ORDER_NOT_FOUND(2501, "Order not found", HttpStatus.NOT_FOUND),
    ORDER_ALREADY_EXISTS(2502, "Order already exists", HttpStatus.CONFLICT),
    ORDER_UNABLE_TO_SAVE(2503, "Unable to save order", HttpStatus.INTERNAL_SERVER_ERROR),
    ORDER_UNABLE_TO_UPDATE(2504, "Unable to update order", HttpStatus.INTERNAL_SERVER_ERROR),
    ORDER_UNABLE_TO_DELETE(2505, "Unable to delete order", HttpStatus.INTERNAL_SERVER_ERROR),
    // Order details exception
    ORDER_DETAILS_NOT_FOUND(2601, "Order details not found", HttpStatus.NOT_FOUND),
    ORDER_DETAILS_ALREADY_EXISTS(2602, "Order details already exists", HttpStatus.CONFLICT),
    ORDER_DETAILS_UNABLE_TO_SAVE(2603, "Unable to save order details", HttpStatus.INTERNAL_SERVER_ERROR),
    ORDER_DETAILS_UNABLE_TO_UPDATE(2604, "Unable to update order details", HttpStatus.INTERNAL_SERVER_ERROR),
    ORDER_DETAILS_UNABLE_TO_DELETE(2605, "Unable to delete order details", HttpStatus.INTERNAL_SERVER_ERROR),
    // Order status
    ORDER_STATUS_NOT_FOUND(2701, "Order status not found", HttpStatus.NOT_FOUND),
    ORDER_STATUS_ALREADY_EXISTS(2702, "Order status already exists", HttpStatus.CONFLICT),
    ORDER_STATUS_UNABLE_TO_SAVE(2703, "Unable to save order status", HttpStatus.INTERNAL_SERVER_ERROR),
    ORDER_STATUS_UNABLE_TO_UPDATE(2704, "Unable to update order status", HttpStatus.INTERNAL_SERVER_ERROR),
    ORDER_STATUS_UNABLE_TO_DELETE(2705, "Unable to delete order status", HttpStatus.INTERNAL_SERVER_ERROR),
    // Payment Status exceptions
    PAYMENT_STATUS_NOT_FOUND(2801, "Payment status not found", HttpStatus.NOT_FOUND),
    PAYMENT_STATUS_ALREADY_EXISTS(2802, "Payment status already exists", HttpStatus.CONFLICT),
    PAYMENT_STATUS_UNABLE_TO_SAVE(2803, "Unable to save payment status", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYMENT_STATUS_UNABLE_TO_UPDATE(2804, "Unable to update payment status", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYMENT_STATUS_UNABLE_TO_DELETE(2805, "Unable to delete payment status", HttpStatus.INTERNAL_SERVER_ERROR),
    // Category exceptions
    CATEGORY_NOT_FOUND(2901, "Category not found", HttpStatus.NOT_FOUND),
    CATEGORY_ALREADY_EXISTS(2902, "Category already exists", HttpStatus.CONFLICT),
    CATEGORY_UNABLE_TO_SAVE(2903, "Unable to save category", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_UNABLE_TO_UPDATE(2904, "Unable to update category", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_UNABLE_TO_DELETE(2905, "Unable to delete category", HttpStatus.INTERNAL_SERVER_ERROR),
    // Products exception
    PRODUCT_NOT_FOUND(3001, "Product not found", HttpStatus.NOT_FOUND),
    PRODUCT_ALREADY_EXISTS(3002, "Product already exists", HttpStatus.CONFLICT),
    PRODUCT_UNABLE_TO_SAVE(3003, "Unable to save product", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_UNABLE_TO_UPDATE(3004, "Unable to update product", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_UNABLE_TO_DELETE(3005, "Unable to delete product", HttpStatus.INTERNAL_SERVER_ERROR),

    ;
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    /**
     * Constructor for ErrorCode.
     *
     * @param code       the error code
     * @param message    the error message
     * @param statusCode the corresponding HTTP status code
     */
    Error(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
