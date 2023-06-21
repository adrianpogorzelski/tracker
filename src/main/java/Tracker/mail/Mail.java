package Tracker.mail;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Mail {

    private String recipient;

    private String subject;

    private String content;

    private MultipartFile attachment;
}
