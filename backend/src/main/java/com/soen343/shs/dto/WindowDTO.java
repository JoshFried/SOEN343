package com.soen343.shs.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WindowDTO {
    private long id;
    private boolean blocked;
    private long roomId;
    private boolean open;
}