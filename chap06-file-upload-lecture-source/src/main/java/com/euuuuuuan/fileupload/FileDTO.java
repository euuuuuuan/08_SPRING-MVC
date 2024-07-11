package com.euuuuuuan.fileupload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FileDTO {
    private String originalFileName;
    private String saveName;
    private String filePath;
    private String fileDescription;
}
