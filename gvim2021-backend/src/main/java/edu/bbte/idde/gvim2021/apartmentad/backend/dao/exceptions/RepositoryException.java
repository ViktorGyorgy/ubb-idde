package edu.bbte.idde.gvim2021.apartmentad.backend.dao.exceptions;

public class RepositoryException extends RuntimeException {
    public RepositoryException() {
        super();
    }

    public RepositoryException(String message) {
        super(message);
    }
}
