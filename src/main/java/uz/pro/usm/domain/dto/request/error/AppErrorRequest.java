package uz.pro.usm.domain.dto.request.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AppErrorRequest {
    private String errorPath;
    private String errorMessage;
    private Integer errorCode;
    private LocalDateTime timeStamp;
}
