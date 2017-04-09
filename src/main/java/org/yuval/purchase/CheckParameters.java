package org.yuval.purchase;

import javax.ws.rs.BeanParam;
import javax.ws.rs.core.Response;

/**
 * Created by Yuval on 08-Apr-17.
 */
public interface CheckParameters {
    Response checkParameters(@BeanParam PurchaseFilterBean filterBean);
    Response checkParametersUnmarkedSeat(@BeanParam PurchaseFilterBean filterBean);

}
