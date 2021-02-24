package ru.ilnyrdiplom.bestedu.facade.exceptions;

public class FileUploadException extends BaseException {


    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadException(String message) {
        super(message);
    }
}
