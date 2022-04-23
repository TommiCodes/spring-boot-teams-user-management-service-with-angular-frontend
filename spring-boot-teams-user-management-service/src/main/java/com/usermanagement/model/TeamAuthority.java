package com.usermanagement.model;

import com.usermanagement.model.enums.Privileges;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TeamAuthority {
    private long teamId;
    private List<Privileges> privileges;
}
