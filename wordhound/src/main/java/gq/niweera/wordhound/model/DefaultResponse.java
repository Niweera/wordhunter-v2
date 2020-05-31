package gq.niweera.wordhound.model;


public class DefaultResponse {
    private String error;
    private String endpoint;
    private String note;

    public DefaultResponse(String error, String endpoint, String note) {
        this.error = error;
        this.endpoint = endpoint;
        this.note = note;
    }

    public DefaultResponse() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
