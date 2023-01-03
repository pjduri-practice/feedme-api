package FeedMe.Auth.Authorization.models.dto;

/**
 * A basic response DTO (data-transfer-object) relaying a single message.
 */
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}