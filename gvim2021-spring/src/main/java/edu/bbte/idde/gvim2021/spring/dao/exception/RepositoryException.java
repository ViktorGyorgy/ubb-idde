package edu.bbte.idde.gvim2021.spring.dao.exception;

public class RepositoryException extends RuntimeException {
    public RepositoryException() {
        super();
    }

    public RepositoryException(String message) {
        super(message);
    }
}
