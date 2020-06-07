package jp.smartcompany.framework.sysboot.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SystemPropertyDTO implements Serializable {

    private static final long serialVersionUID = -4292291291322476035L;

    private String customerId;
    private String propName;
    private Object propValue;

}
