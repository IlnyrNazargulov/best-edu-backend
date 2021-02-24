package ru.ilnyrdiplom.bestedu.facade.exceptions;

public class FileSizeExceededException extends BaseException {
    public FileSizeExceededException() {
        super("File size exceeds the maximum limit.");
    }
}
