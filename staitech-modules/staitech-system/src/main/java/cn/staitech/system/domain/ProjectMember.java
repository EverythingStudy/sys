package cn.staitech.system.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * tb_project_member
 *
 * @author
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectMember implements Serializable {
    private Long userId;

    private Long projectId;

    private Long roleId;

    private static final long serialVersionUID = 1L;
}