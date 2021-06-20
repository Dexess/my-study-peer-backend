package com.mystudypeer.mystudypeer.pojo;


import com.mystudypeer.mystudypeer.domains.RequestId;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "Request")
public class Request {
    @EmbeddedId
    private RequestId requestId;
    @Column(name = "status")
    private String status;
    @Column(name = "requestDate")
    private Date requestDate;

}
