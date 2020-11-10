package com.soen343.shs.dto;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SecuritySystemDTO {
    private long id;
    private long houseId;
    private boolean auto;
    private AwayModeDTO awayMode;
}
