package com.inventory_managerment.baseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {
    @Column
    @Basic(optional=false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;
    @Column
    @Basic(optional=false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedAt;
}

