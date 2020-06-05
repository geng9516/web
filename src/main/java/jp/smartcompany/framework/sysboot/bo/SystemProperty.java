package jp.smartcompany.framework.sysboot.bo;

import lombok.*;

import java.io.Serializable;


/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SystemProperty implements Serializable {

    private static final long serialVersionUID = -4292291291322476035L;

    private String customerId;
    private String propName;
    private Object propValue;

}
